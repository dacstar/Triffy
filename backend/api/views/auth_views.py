from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from api.models import create_profile, Profile, Balance
from api.serializers import ProfileSerializer
from django.contrib.auth.models import User
from datetime import datetime, timedelta
from pytz import timezone


@api_view(['POST', 'GET'])
def signup(request):
    if request.method == 'POST':
        # front에서 회원가입 할 때에 'http://10.3.17.61:8080/v1/account/list'에서 받은 계좌번호를 'http://10.3.17.61:8080/v1/account/deposit/detail'에 요청하여 계좌정보 profiles에 추가하여 받음
        username = request.POST['username']
        password = request.POST['password']
        age = request.POST['age']
        gender = request.POST['gender']
        ssn = request.POST['ssn']
        # Balnce model 저장
        profile = create_profile(username=username, password=password, age=age, gender=gender, ssn=ssn)
        account = request.POST['account']
        if account == "230221966424":
            name = '신한 S힐링 여행적금'
            now_amount = 1600000
            start_date = "20190927"
            end_date = "20200427"
            goal_amount = 2000000
            interest = 1.85
        else:
            name = request.POST.get('name', None)
            now_amount = request.POST.get('now_amount', None)
            start_date = request.POST.get('start', None)
            end_date = request.POST.get('end', None)
            goal_amount = request.POST.get('goal_amount', None)
            interest = request.POST.get('interest', None)
        balance = Balance.objects.create(user_id=profile.user, account=account, name=name, now_amount=now_amount, goal_amount=goal_amount, start_date=start_date, end_date=end_date)

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
