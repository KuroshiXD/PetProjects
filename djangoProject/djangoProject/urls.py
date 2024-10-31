"""
URL configuration for djangoProject project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/5.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from myApp.views import *

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', topics, name='topics'),
    path('login/', login, name='login'),
    path('register/', register, name='register'),
    path('topics/<int:topic_id>/comments/', add_comment, name='add_comment'),
    path('topics/profile/<str:username>/deleted_article/<int:topic_id>', delete_topic, name='delete_topic'),
    path('topics/<int:topic_id>/rep_comment/<int:comment_id>/', comment_reply, name='comment_reply'),
    path('topics/profile/<str:username>', profile, name='profile'),
    path('topics/<int:topic_id>/', topic_content, name='topic_content'),
    path('topics/create_topic/', create_topic, name='create_topic'),
    path('logout/', logout, name='logout'),
]
