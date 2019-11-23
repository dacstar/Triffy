import requests
import json
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
        if request.user.is_authenticated:
            user = request.user
            # 신용카드 해외 사용 내역 조회
            url = "http://10.3.17.61:8081/v1/usecreditcard/searchuseforoverseas"
            params = {
                "dataHeader":{
                },
                "dataBody":{
                    "nxtQyKey":"",
                    "fromdate":"20190506",
                    "todate":"20190806",
                    "bctag":"0",
                    "fmlOCrdC":"0"
                }
            }
            response = requests.post(url, data=json.dumps(params)).json()
            if response["dataBody"]["grp001"]:
                group = response["dataBody"]["grp001"]
                for item in group:
                    content = item["ryCd"]
                    if content == "택시":
                        category = "교통비"
                    money = float(item["aprvamt"].replace(',', ''))
                    time_now = item["aprvtime"][:4] + '-' + item["aprvtime"][4:6] + '-' + item["aprvtime"][6:8]
                    spent = True
                    currency = item["currency"]
                    card = True
                    if not Calendar.objects.filter(user_id=user, time_now=time_now, content=content, money=money):
                        result = Calendar.objects.create(user_id=user, category=category, time_now=time_now, content=content, money=money, card=card, spent=spent, currency=currency)

            # 체크카드 해외 사용내역 조회
            url = "http://10.3.17.61:8081/v1/usedebitcard/searchuseforoverseas"
            params = {
                "dataHeader":{
                },
                "dataBody":{
                    "nxtQyKey":"",
                    "fromdate":"20190507",
                    "todate":"20190830",
                    "bctag":"0"
                }
            }
            response = requests.post(url, data=json.dumps(params)).json()
            if response["dataBody"]["grp001"]:
                group = response["dataBody"]["grp001"]
                for item in group:
                    content = item["ryCd"]
                    if content == "주차장":
                        category = "기타"
                    currency = item["loaTel"]
                    time_now = item["apvDt"][:4] + '-' + item["apvDt"][4:6] + '-' + item["apvDt"][6:8]
                    money = float(item["apva"])
                    card = True
                    spent = True
                    if not Calendar.objects.filter(user_id=user, time_now=time_now, content=content, money=money):
                        result = Calendar.objects.create(user_id=user, category=category, time_now=time_now, content=content, money=money, card=card, spent=spent, currency=currency)
            calendars = Calendar.objects.filter(user_id=user)
            result = {}
            for item in calendars:
                serializer = CalendarSerializer(item)
                tmp = serializer.data
                if serializer.data['time_now'] in result:
                    result[tmp['time_now']].append({'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']})
                else:
                    result[tmp['time_now']] = [{'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']}]
            return Response(data=result, status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

# category 별로 분류하여 합산, 소비자 리포트 생성 및 리턴
@api_view(['POST'])
def by_category(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
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
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

# card 소비/현금 소비 나뉘어 보여주기
@api_view(['POST'])
def by_card(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
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
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

# @api_view(['POST'])
# def buy_card(request):
#     if request.method == 'POST':
#         user = request.user
#         # 신용카드 해외 사용 내역 조회
#         url = 'http://10.3.17.61:8081/v1/usecreditcard/searchuseforoverseas'
#         params = {
#             "dataHeader":{
#             },
#             "dataBody":{
#                 "nxtQyKey":"",
#                 "fromdate":"20190506",
#                 "todate":"20190806",
#         "bctag":"0",
#         "fmlOCrdC":"0"
#             }
#         }
#         response = requests.post(url, data=json.dumps(params)).json()
#         if response['dataBody']['grp001']:
#             group = response['dataBody']['grp001']
#             for item in group:
#                 content = item['ryCd']
#                 if content == '택시':
#                     category = '교통비'
#                 money = int(item['aprvamt'].replace(',', ''))
#                 time_now = item['aprvtime'][:8]
#                 spend = True
#                 currency = item['currency']
#                 card = True
#                 if not Calendar.objects.filter(user_id=user, time_now=timenow, content=content, money=money):
#                     result = Calendar.objects.create(user_id=user, category=category, time_now=time_now, content=content, money=money, card=card, spent=spent, currency=currency)

#         # 체크카드 해외 사용내역 조회
#         url = 'http://10.3.17.61:8081/v1/usedebitcard/searchuseforoverseas'
#         params = {
#             "dataHeader":{
#             },
#             "dataBody":{
#                 "nxtQyKey":"",
#         "fromdate":"20190507",
#         "todate":"20190830",
#         "bctag":"0"
#             }
#         }
#         response = requests.post(url, data=json.dumps(params)).json()
#         if response['dataBody']['grp001']:
#             group = response['dataBody']['grp001']
#             for item in group:
#                 content = item['ryCd']
#                 if content == '주차장':
#                     category = '기타'
#                 currency = item['loaTel']
#                 time_now = item['apvDt'][:8]
#                 money = float(item['apva'])
#                 card = True
#                 spent = True
#                 if not Calendar.objects.filter(user_id=user, time_now=timenow, content=content, money=money):
#                     result = Calendar.objects.create(user_id=user, category=category, time_now=time_now, content=content, money=money, card=card, spent=spent, currency=currency)
        

# calendar item 생성하기(spent가 true면 이미 소비한 내역, false면 소비계획)
@api_view(['POST'])
def add_item(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            user = request.user
            category = request.POST.get('category', None)
            # time_now = str(datetime.now())[:10]
            time_now = request.POST['time_now']
            time_now = time_now[:4] + '-' + time_now[4:6] + '-' + time_now[6:8]
            content = request.POST['content']
            money = float(request.POST['money'])
            # card = request.POST.get('card', False)
            spent = request.POST.get('spent', True)
            currency = request.POST.get('currency', '원')
            calendar = Calendar.objects.create(user_id=user, category=category, time_now=time_now, content=content, money=money, card=card, spent=spent, currency=currency)
            calendars = list(Calendar.objects.filter(user_id=user))
            result = []
            for item in calendars:
                serializer = CalendarSerializer(item)
                result.append(serializer.data)
            return Response(data=result, status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)


@api_view(['POST'])
def switch_spent(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            user = request.user
            pk = request.POST['pk']
            calendar = Calendar.objects.get(pk=pk)
            if calendar.spent == True:
                calendar.spent = False
            else:
                calendar.spent = True
            calendar.save()
            # calendar 목록 갱신
            calendars = Calendar.objects.filter(user_id=user)
            result = {}
            for item in calendars:
                serializer = CalendarSerializer(item)
                tmp = serializer.data
                if serializer.data['time_now'] in result:
                    result[tmp['time_now']].append({'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']})
                else:
                    result[tmp['time_now']] = [{'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']}]
            return Response(data=result, status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def delete_item(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            user = request.user
            pk = request.POST['pk']
            calendar = Calendar.objects.get(pk=pk)
            calendar.delete()
            # calendar 목록 갱신
            calendars = Calendar.objects.filter(user_id=user)
            result = {}
            for item in calendars:
                serializer = CalendarSerializer(item)
                tmp = serializer.data
                if serializer.data['time_now'] in result:
                    result[tmp['time_now']].append({'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']})
                else:
                    result[tmp['time_now']] = [{'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']}]
            return Response(data=result, status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def edit_item(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            user = request.user
            pk = request.POST['pk']
            calendar = Calendar.objects.get(pk=pk)
            category = request.POST.get('category', None)
            content = request.POST.get('content', None)
            money = request.POST.get('money', None)
            # card = request.POST.get('card', None)
            spent = request.POST.get('spent', None)
            currency = request.POST.get('currency', None)
            if category is not None:
                calendar.category = category
            if content is not None:
                calendar.content = content
            if money is not None:
                calendar.money = float(money)
            if card is not None:
                calendar.card = card
            if spent is not None:
                calendar.spent = spent
            if currency is not None:
                calendar.currency = currency
            calendar.save()

            # calendar 목록 갱신
            calendars = Calendar.objects.filter(user_id=user)
            result = {}
            for item in calendars:
                serializer = CalendarSerializer(item)
                tmp = serializer.data
                if serializer.data['time_now'] in result:
                    result[tmp['time_now']].append({'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']})
                else:
                    result[tmp['time_now']] = [{'pk': tmp['id'], 'category': tmp['category'], 'content': tmp['content'], 'money': tmp['money'], 'card': tmp['card'], 'spent': tmp['spent'], 'currency': tmp['currency']}]
            return Response(data=result, status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        
        