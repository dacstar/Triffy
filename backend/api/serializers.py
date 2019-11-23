from .models import Profile, CheckList, Calendar, Post
from rest_framework import serializers


class ProfileSerializer(serializers.ModelSerializer):
    id = serializers.ReadOnlyField()
    username = serializers.SerializerMethodField('get_username')

    class Meta:
        model = Profile
        fields = ('id', 'username', 'gender', 'age')

    def get_username(self, obj):
        return obj.user.username


class CheckListSerializer(serializers.ModelSerializer):

    class Meta:
        model = CheckList
        fields = ('id', 'user', 'content', 'checked')


class CalendarSerializer(serializers.ModelSerializer):
    class Meta:
        model = Calendar
        fields = ('id', 'user_id', 'category', 'time_now',
                  'content', 'money', 'card', 'spent', 'currency')


class PostSerializer(serializers.ModelSerializer):
    like_count = serializers.SerializerMethodField('get_like_count')

    class Meta:
        model = Post
        fields = ('id', 'writer', 'city', 'title', 'photo', 'content',
                  'created_at', 'updated_at', 'agreement', 'like_users', 'like_count')

    def get_like_count(self, obj):
        print(obj.like_users.count())
        return obj.like_users
