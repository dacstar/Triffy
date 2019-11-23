from .models import Profile, CheckList
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

