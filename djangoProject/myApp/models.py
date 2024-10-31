from django.contrib.auth.models import AbstractUser
from django.db import models


class User(AbstractUser):
    bio = models.CharField(blank=True, max_length=299)

    def __str__(self):
        return self.username


class Topic(models.Model):
    author = models.ForeignKey(User, on_delete=models.SET_NULL, verbose_name="Автор темы", null=True)
    title = models.CharField(max_length=50, verbose_name="Тема")
    description = models.TextField(blank=True, verbose_name="Описание")
    created_at = models.DateTimeField(auto_now_add=True, verbose_name="Дата создания")

    def __str__(self):
        return self.title


class Message(models.Model):
    author = models.ForeignKey(User, on_delete=models.SET_NULL, verbose_name="Автор сообщения", null=True)
    topic = models.ForeignKey(Topic, on_delete=models.CASCADE, verbose_name="Тема")
    content = models.TextField(verbose_name="Контент коммента")
    created_at = models.DateTimeField(auto_now_add=True, verbose_name="Дата создания")

    def __str__(self):
        return self.content[:50]


class ReplyMessage(models.Model):
    author = models.ForeignKey(User, on_delete=models.SET_NULL, verbose_name="Автор ответа", null=True)
    message = models.ForeignKey(Message, on_delete=models.CASCADE, verbose_name="Сообщение")
    content = models.TextField(verbose_name="Контент ответа на коммент")
    created_at = models.DateTimeField(auto_now_add=True, verbose_name="Дата создания")

    def __str__(self):
        return self.content[:50]
