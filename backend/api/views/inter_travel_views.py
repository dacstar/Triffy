from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from datetime import datetime, timedelta
from django.contrib.auth.models import User
from api.models import Profile, Balance, CheckList, Calendar
from pytz import timezone

# 사용자의 저장된 calendar 데이터 리턴하기
@api_view(['GET'])
def get_calendar(request):
    if request.method == 'GET':
        user_id = request.GET['user_id']
        calendars = list(Calendar.objects.filter(user_id=User.objects.get(pk=user_id)))
        data = {}
        for item in calendars:
            if item.time_now in data:
                data.time_now.append({'category': item.category, 'content': item.content, 'money': item.money, 'card': item.card, 'spent': item.spent})

            else:
                data[time_now] = [{'category': item.category, 'content': item.content, 'money': item.money, 'card': item.card, 'spent': item.spent}]
        return Response(data=data, status=HTTP_200_OK)
    
# category 별로 분류하여 합산, 소비자 리포트 생성 및 리턴
@api_view(['GET'])
def by_category(requst):
    user_id = request.GET['user_id']
    data = {}
    calendars = list(Calendar.objects.filter(user_id=User.objects.get(user_id)).filter(spent=True))
    for item in calendars:
        if item.category in data:
            data.category += item.money
        else:
            data[category] = item.money
    
    # category를 합산 금액에 따라 딕셔너리를 sorting
    res = sorted(data.items(), key=(lambda x: x[1]), reverse=True)
    return Response(data=res, status=HTTP_200_OK)

