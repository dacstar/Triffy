from django.db import models
from django.contrib.auth.models import User


class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    gender = models.CharField(max_length=10, default='M')
    age = models.IntegerField(default=25)


#  wrapper for create user Profile
def create_profile(**kwargs):
    user = User.objects.create_user(
        username=kwargs['username'],
        password=kwargs['password'],
        is_active=True,
    )
    profile = Profile.objects.create(
        user=user,
        gender=kwargs['gender'],
        age=kwargs['age'],
        ssn=kwargs['ssn']
    )
    return profile


class Balance(models.Model):
    id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    acounts = models.CharField(max_length=20, blank=True)
    goal_amount = models.IntegerField(blank=True)
    now_amount = models.IntegerField(blank=True)


class City(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=20)
    country = models.CharField(max_length=10)


class Posts(models.Model):
    id = models.AutoField(primary_key=True)
    city_id = models.ForeignKey(City, on_delete=models.CASCADE)


class Likes(models.Model):
    id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    post_id = models.ForeignKey(Posts, on_delete=models.CASCADE)


class Program(models.Model):
    id = models.AutoField(primary_key=True)
    city_id = models.ForeignKey(City, on_delete=models.CASCADE)
    title = models.TextField()
    episode = models.CharField(max_length=10)


class Calendar(models.Model):
    id = models.AutoField(primary_key=True)
    category = models.CharField(max_length=10)
    time_now = models.DateField(blank=True)
    content = models.CharField(max_length=10)
    money = models.IntegerField(blank=True)
    card = models.BooleanField()
    spent = models.BooleanField()
