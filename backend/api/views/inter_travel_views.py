from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from datetime import datetime, timedelta
from django.contrib.auth.models import User
from api.models import Profile, Balance, CheckList, Calendar
from pytz import timezone
from api.serializers import CalendarSerializer

# 사용자의 저장된 calendar 데이터 리턴하기
@api_view(['POST'])
def get_calendar(request):
    if request.method == 'POST':
        user = request.user
        calendars = Calendar.objects.filter(user_id=user)
        result = {}
        for item in calendars:
            serializer = CalendarSerializer(item)
            tmp = serializer.data
            if serializer.data['time_now'] in result:
                result[tmp['time_now']].append({'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']})
            else:
                result[tmp['time_now']] = [{'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']}]
        return Response(data=result, status=status.HTTP_200_OK)

# category 별로 분류하여 합산, 소비자 리포트 생성 및 리턴
@api_view(['POST'])
def by_category(request):
    if request.method == 'POST':
        user = request.user
        calendars = Calendar.objects.filter(user_id=user).filter(spent=True)
        result = {}
        for item in calendars:
            serializer = CalendarSerializer(item)
            tmp = serializer.data
            if tmp['category'] in result:
                result[tmp['category']] += tmp['money']
            else:
                result[tmp['category']] = tmp['money']

        # category를 합산 금액에 따라 딕셔너리를 sorting
        res = sorted(result.items(), key=(lambda x: x[1]), reverse=True)
        return Response(data=res, status=status.HTTP_200_OK)

# card 소비/현금 소비 나뉘어 보여주기
@api_view(['POST'])
def by_card(request):
    if request.method == 'POST':
        user = request.user
        calendars = Calendar.objects.filter(user_id=user).filter(spent=True)
        result = {'card': 0, 'cash': 0}
        for item in calendars:
            serializer = CalendarSerializer(item)
            tmp = serializer.data
            if tmp['card'] == True:
                result['card'] += tmp['money']
            else:
                result['cash'] += tmp['money']
        res = sorted(result.items(), key=(lambda x: x[1]), reverse=True)
        return Response(data=res, status=status.HTTP_200_OK)


# calendar item 생성하기(spent가 true면 이미 소비한 내역, false면 소비계획)
@api_view(['POST'])
def add_item(request):
    if request.method == 'POST':
        user = request.user
        category = request.POST.get('category', None)
        time_now = str(datetime.now())[:10]
        content = request.POST['content']
        money = int(request.POST['money'])
        card = request.POST.get('card', False)
        spent = request.POST.get('spent', True)
        currency = request.POST.get('currency', 'won')
        calendar = Calendar.objects.create(user_id=user, category=category, time_now=time_now, content=content, money=money, card=card, spent=spent, currency=currency)
        calendars = list(Calendar.objects.filter(user_id=user))
        result = []
        for item in calendars:
            serializer = CalendarSerializer(item)
            result.append(serializer.data)
        return Response(data=result, status=status.HTTP_200_OK)


