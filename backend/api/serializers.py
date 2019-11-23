from .models import Profile, CheckList, Calendar, Airplane, Hotel
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


class AirplaneSerializer(serializers.ModelSerializer):
    class Meta:
        model = Airplane
        fields = ('name', 'price', 'outdeparture', 'outarrival', 'indeparture', 'inarrival')


class HotelSerializer(serializers.ModelSerializer):
    class Meta:
        model = Hotel
        fields = ('name', 'price', 'arrival', 'departure')