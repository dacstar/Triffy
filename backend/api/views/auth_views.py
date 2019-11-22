from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from api.models import create_profile, Profile
from api.serializers import ProfileSerializer
from django.contrib.auth.models import User
from datetime import datetime, timedelta
from pytz import timezone


@api_view(['POST', 'GET'])
def signup_many(request):
    if request.method == 'POST':
        profiles = request.data.get('profiles', None)
        for profile in profiles:
            username = profile.get('username', None)
            password = profile.get('password', None)
            age = profile.get('age', None)
            gender = profile.get('gender', None)
            ssn = profile.get('ssn', None)

            create_profile(username=username, password=password, age=age, gender=gender, ssn=ssn)

        return Response(status=status.HTTP_201_CREATED)


@api_view(['GET', 'POST', 'PUT', 'DELETE'])
def users(request):
    if request.method == 'GET':
        id = request.GET.get('id',None)
        '''해당 id를 갖는 profile의 pk값을 가져온다 '''
        if id:
            user = User.objects.get(username=id)
            if user:
                profile = Profile.objects.get(user=user)

        serializer = ProfileSerializer(profile)
        return Response(data=serializer.data, status=status.HTTP_200_OK)

    if request.method == 'POST':
        username = request.data.get('username', None)
        password = request.data.get('password', None)
        age = request.data.get('age', None)
        gender = request.data.get('gender', None)

        if username and password:
            create_profile(username=username, password=password, age=age, gender=gender)

        return Response(status=status.HTTP_201_CREATED)

    if request.method == 'PUT':
        id = request.GET.get('id', None)
        gender = request.GET.get('gender',None)
        age = request.GET.get('age', None)

        if id:
            user = User.objects.get(username=id)
            if user and gender and age:
                Profile.objects.filter(user=user).update(gender=gender, age=age)
        return Response(status=status.HTTP_201_CREATED)

    if request.method == 'DELETE':
        id = request.GET.get('id',None)
        if id:
            user = User.objects.get(pk=id)
            if user:
                user.delete()

        return Response(status=status.HTTP_201_CREATED)
