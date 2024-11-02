from django.contrib.auth import authenticate, login as auth_login, logout as auth_logout
from django.contrib.auth.decorators import login_required
from django.http import HttpResponse
from django.shortcuts import render, redirect
from .models import User, Topic, Message, ReplyMessage
import datetime
import re


def logout(request):
    auth_logout(request)
    return redirect('topics')


def topics(request):
    last_topics = Topic.objects.order_by('-created_at')[:20]
    return render(request, 'topics.html', context={'last_topics': last_topics, 'username': request.user})


def login(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        password = request.POST.get('password')

        user = authenticate(request, username=username, password=password)

        if user is not None:
            auth_login(request, user)
            return redirect('topics')
        else:
            return HttpResponse("Wrong username or password")

    return render(request, 'registration/login.html')


def register(request):
    if request.method == 'POST':
        username = request.POST.get('username')
        email = request.POST.get('email')
        password1 = request.POST.get('password1')
        password2 = request.POST.get('password2')

        if len(password1) < 8:
            return render(request, 'registration/registration.html',
                          {"error": "Password's length must be at least 8 characters"})

        if password1 != password2:
            return render(request, 'registration/registration.html',
                          {'error': 'Passwords do not match'})

        if User.objects.filter(username=username).exists():
            return render(request, 'registration/registration.html',
                          {'error': 'Username already taken'})

        if User.objects.filter(email=email).exists():
            return render(request, 'registration/registration.html',
                          {"error": "Email already taken"})

        user_created = User.objects.create_user(username=username, email=email, password=password1)
        user_created.save()
        return redirect('login')

    return render(request, 'registration/registration.html')


@login_required
def create_topic(request):
    if request.method == 'POST':
        author = request.user
        topic = request.POST.get('topic')
        description = request.POST.get('description')
        date = datetime.datetime.now()

        errors = {}

        if not topic:
            errors['topic'] = 'Topic is required'
        if len(topic) < 3 and not topic.startswith(' '):
            errors['topic'] = 'Topic must have at least 3 characters or have no spaces at the beginning'

        if errors:
            return render(request, 'create_topic.html', context={'errors': errors})

        topic = Topic.objects.create(author=author, title=topic, description=description)
        topic.save()
        return redirect('topics')

    return render(request, 'create_topic.html', context={'author': request.user,
                                                         'date': datetime.datetime.now()})


def topic_content(request, topic_id):
    topic = Topic.objects.get(id=topic_id)
    comments = Message.objects.filter(topic=topic)

    data = {
        'author': topic.author.username,
        'title': topic.title,
        'description': topic.description,
        'date': topic.created_at,
        'comments': comments,
        'topic_id': topic_id,
        'comment_date': datetime.datetime.now(),
    }

    return render(request, 'topic_content.html', context=data)


@login_required
def add_comment(request, topic_id):
    if request.method == 'POST':

        author = request.user
        comment = request.POST.get('comment')
        errors = {}

        if comment.startswith(' '):
            errors['comment'] = 'Comment must have no spaces at the beginning'

        if errors:
            return render(request, 'topic_content.html',
                          context={
                              'errors': errors,
                              'topic_id': topic_id,
                              'author': User.objects.get(id=Topic.objects.get(id=topic_id).author_id).username,
                              'title': Topic.objects.get(id=topic_id).title,
                              'description': Topic.objects.get(id=topic_id).description,
                              'date': Topic.objects.get(id=topic_id).created_at,
                              'comments': Message.objects.filter(topic_id=topic_id),
                          })

        message = Message.objects.create(content=comment, author_id=author.id, topic_id=topic_id)
        message.save()
        return redirect('topic_content', topic_id=topic_id)

    return render(request, 'topic_content.html')


def comment_reply(request, topic_id, comment_id):
    if request.method == "POST":

        user = Message.objects.get(id=comment_id).author_id
        comment = request.POST.get('reply')
        username = request.user.id

        if comment:
            rep_msg = ReplyMessage.objects.create(content=comment, message_id=comment_id, author_id=username)
            rep_msg.save()

    return redirect('topic_content', topic_id=topic_id)


@login_required
def profile(request, username):
    user = User.objects.get(username=username)
    data = {
        'username': username,
        'email': user.email,
        'date_of_registration': user.date_joined,
        'articles': Topic.objects.filter(author_id=user.id).order_by('-created_at'),
        'comments': Message.objects.filter(author_id=user.id).order_by('-created_at'),

    }
    return render(request, 'profile.html', context=data)


def delete_topic(request, username, topic_id):
    Topic.objects.get(id=topic_id).delete()
    return redirect('profile', username=username)


def edit_topic(request, topic_id):
    topic = Topic.objects.get(id=topic_id)
    if request.method == 'POST':
        edited_topic = request.POST.get('edit_title')
        edited_content = request.POST.get('edit_content')

        errors = {}

        if re.match(r'^\s+', edited_topic) or re.match(r'^\s+', edited_content):
            errors['error'] = "Place shouldn't be starts with a space"

        if errors:
            return render(request, 'edit_topic.html', context=errors)

        topic.title = edited_topic
        topic.description = edited_content
        topic.save()

        return redirect('profile', username=User.objects.get(id=topic.author_id).username)
    return render(request, 'edit_topic.html', context={
        "username": User.objects.get(id=topic.author_id).username,
        "topic_title": Topic.objects.get(id=topic_id).title,
        "topic_content": Topic.objects.get(id=topic_id).description,
        "topic_id": topic_id,
        "current_content": Topic.objects.get(id=topic_id).description,
        "current_topic": Topic.objects.get(id=topic_id).title,
    })
