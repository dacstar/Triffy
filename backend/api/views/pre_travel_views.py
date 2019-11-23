import requests, json
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from datetime import datetime, timedelta
from django.contrib.auth.models import User
from api.models import Profile, Balance, CheckList, Airplane
from api.serializers import ProfileSerializer, CheckListSerializer
from pytz import timezone
        
# 적금 상세내역 조회({'user_id'})
@api_view(['POST'])
def get_balance(request):
    if request.method == 'POST':
        # user_id를 받아 적금 계좌 상세 조회 리턴
        user_id = request.POST['user_id']
        balance = Balance.objects.get(user_id=user_id)
        data = {
            'account': balance.account,
            'name': balance.name,
            'interest': balance.interest,
            'goal_amount': balance.goal_amount,
            'now_amount': balance.now_amount,
            'start_date': balance.start_date,
            'end_date': balance.end_date,
            'achieve': (balance.now_amount/ balance.goal_amount) * 100
        }
        return Response(data=data, status=status.HTTP_200_OK)


# 사용자가 저장한 체크리스트 아이템 리스트 가져오기({'user_id'})
@api_view(['POST'])
def checkList(request):
    if request.method == 'POST':
        # user_id를 get요청하기
        user_id = request.POST['user_id']
        user = request.user
        checklists = list(CheckList.objects.filter(user=user))
        result = []
        for item in checklists:
            serializer = CheckListSerializer(item)
            result.append(serializer.data)
        data = {'items': result}
        return Response(data=data, status=status.HTTP_200_OK)
    
# 체크리스트 아이템 추가하기({user_id, content})
@api_view(['POST'])
def add_item(request):
    if request.method == 'POST':
        user_id = request.POST['user_id']
        content = request.POST['content']
        user = User.objects.get(pk=user_id)
        item = CheckList.objects.create(user=user, content=content, checked=False)
        checklists = list(CheckList.objects.filter(user=user))
        result = []
        for item in checklists:
            serializer = CheckListSerializer(item)
            result.append(serializer.data)
        data = {'items': result}
        return Response(data=data, status=status.HTTP_200_OK)

# 해당 체크리스트 아이템 완료 체크 또는 해제({user_id, check_id})
@api_view(['POST'])
def check(request):
    if request.method == 'POST':
        user_id = request.POST['user_id']
        check_id = request.POST['check_id']
        item = CheckList.objects.get(pk=check_id)
        if item.checked == False:
            item.checked = True
        else:
            item.checked = False
        item.save()
        checklists = list(CheckList.objects.filter(user=User.objects.get(pk=user_id)))
        result = []
        for item in checklists:
            serializer = CheckListSerializer(item)
            result.append(serializer.data)
        data = {'items': result}
        return Response(data=data, status=status.HTTP_200_OK)

# 해당 체크리스트 아이템 삭제({user_id, check_id})
@api_view(['POST'])
def delete_item(request):
    if request.method == 'POST':
        check_id = request.POST['check_id']
        user_id = request.POST['user_id']
        item = CheckList.objects.get(pk=check_id)
        item.delete()
        checklists = list(CheckList.objects.filter(user=User.objects.get(pk=user_id)))
        result = []
        for item in checklists:
            serializer = CheckListSerializer(item)
            result.append(serializer.data)
        data = {'items': result}
        return Response(data=data, status=status.HTTP_200_OK)

# 해당 체크리스트 아이템 수정({user_id, check_id, content})
@api_view(['POST'])
def edit_item(request):
    if request.method == 'POST':
        check_id = request.POST['check_id']
        user_id = request.POST['user_id']
        content = request.POST['content']
        item = CheckList.objects.get(pk=check_id)
        item.content = content
        item.save()
        checklists = list(CheckList.objects.filter(user=User.objects.get(pk=user_id)))
        result = []
        for item in checklists:
            serializer = CheckListSerializer(item)
            result.append(serializer.data)
        data = {'items': result}
        return Response(data=data, status=status.HTTP_200_OK)

@api_view(['POST'])
def sub_balance(request):
    if request.method == 'POST':
        user = request.user
        print(user)
        balance = Balance.objects.get(user_id = user)
        user_id = request.POST['user_id']
        spent_amount = int(request.POST['spent_amount'])
        category = request.POST['category']
        balance.goal_amount -= spent_amount
        balance.now_amount -= spent_amount
        balance.save()

        # checklist 체크하기
        if category == '항공권':
            check = list(CheckList.objects.filter(user=user, content='항공권 예약'))[0]
        elif category == '숙박':
            check = list(CheckList.objects.filter(user=user, content='숙박 예약'))[0]
        check.checked = True
        check.save()
        data = {
            'account': balance.account,
            'name': balance.name,
            'interest': balance.interest,
            'goal_amount': balance.goal_amount,
            'now_amount': balance.now_amount,
            'start_date': balance.start_date,
            'end_date': balance.end_date,
            'achieve': (balance.now_amount/ balance.goal_amount) * 100
        }
        return Response(data=data, status=status.HTTP_200_OK)

@api_view(['POST'])
def reserve_airplane(request):
    if request.method == 'POST':
        if request.user.is_authenticated:
            user = request.user
            print(user)
            airplane = Airplane.objects.get(user_id=user)
            data = {
                'price': airplane.price,
                'outdeparture': airplane.outdeparture,
                'outarrival': airplane.outarrival,
                'indeparture': airplane.indeparture,
                'inarrival': airplane.inarrival,
                'name': airplane.name
            }
            return Response(data=data, status=status.HTTP_200_OK)

@api_view(['POST'])
def get_exchange(request):
    if request.method == 'POST':
        url = 'http://10.3.17.61:8080/v1/search/realtime-fx/period'
        currency = ["USD"]
        result = []
        tmp = str(datetime.now())
        now_date = tmp[:10].replace('-', '.')
        now_time = tmp[11:16]
        for item in currency:
            params = {
                "dataHeader":
                {
                },
                "dataBody":
                {
                "통화코드": item,
                "serviceCode":"F3750",
                "고시일자041":"20190618",
                "고시일자042":"20190625"
                }
            }
            exchange_rate = requests.post(url, data=json.dumps(params)).json()
            exchange_rate = exchange_rate['dataBody']['조회내역'][-1]
            data = {'buy': exchange_rate['지폐매입환율'], 'sell': exchange_rate['지폐매도환율'], 'currency': item, 'now_date': now_date, 'now_time': now_time}
            result.append(data)
        print(result)
        return Response(data=result, status=status.HTTP_200_OK)
