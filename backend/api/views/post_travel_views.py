import requests
import json
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from datetime import datetime, timedelta
from django.contrib.auth.models import User
from api.models import Profile, CheckList, Calendar, Post, City
from pytz import timezone
from api.serializers import CalendarSerializer, PostSerializer


@api_view((['POST']))
def write_post(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            writer = request.user
            city = City.objects.get(name=request.POST['city'])
            title = request.POST['title']
            content = request.POST.get('content', '')
            photo = request.POST.get('photo', '')
            agreement = request.POST.get('agreement', False)
            post = Post.objects.create(writer=writer, city=city, title=title, photo=photo, content=content, agreement=agreement)
            return Response(status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def post_list(request):
    if request.method == 'POST':
        posts = Post.objects.all().order_by('-like_users')
        result = []
        for item in posts:
            serializer = PostSerializer(item)
            result.append(serializer.data)
        return Response(data=result, status=status.HTTP_200_OK)

@api_view(['POST'])
def like(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            user = request.user
            post_id = request.POST['post_id']
            post = Post.objects.get(pk=post_id)
            if post.like_users.filter(pk=user.id).exists():
                post.like_users.remove(user)
            else:
                post.like_users.add(user)
            post = Post.objects.get(pk=post_id)
            serializer = PostSerializer(post)
            return Response(data=serializer.data, status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

