from django.db import models
from django.contrib.auth.models import User

class Profile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    gender = models.CharField(max_length=10, default='M')
    age = models.IntegerField(default=25)
    ssn = models.CharField(max_length=100, default='')


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

    checkList = CheckList.objects.create(
        user=user,
        title='항공권 예약'
    )

    checkList = CheckList.objects.create(
        user=user,
        title='숙박 예약'
    )
    return profile


class Balance(models.Model):
    id = models.AutoField(primary_key=True)
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    account = models.CharField(max_length=20, blank=True)
    name = models.CharField(max_length=100, default='')
    interest = models.FloatField(default=0.0)
    goal_amount = models.IntegerField(blank=True)
    now_amount = models.IntegerField(blank=True)
    start_date = models.CharField(max_length=30, default='')
    end_date = models.CharField(max_length=30, default='')

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
    user_id = models.ForeignKey(User, on_delete=models.CASCADE)
    category = models.CharField(max_length=10)
    time_now = models.DateField(blank=True)
    content = models.CharField(max_length=10)
    money = models.IntegerField(blank=True)
    card = models.BooleanField()
    spent = models.BooleanField()

class CheckList(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    content = models.CharField(max_length=100, default='')
    checked = models.BooleanField(default=False)
