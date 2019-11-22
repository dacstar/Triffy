from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from datetime import datetime, timedelta
from django.contrib.auth.models import User
from api.models import Profile, Balance, CheckList
from pytz import timezone
        
# 적금 상세내역 조회
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


# 사용자가 저장한 체크리스트 아이템 리스트 가져오기
@api_view(['POST'])
def checkList(request):
    if request.method == 'POST':
        # user_id를 get요청하기
        user_id = request.POST['user_id']
        checklists = list(CheckList.objects.filter(user=User.objects.get(pk=user_id)))
        data = {'items': checklists}
        return Response(data=data, status=status.HTTP_200_OK)
    
# 체크리스트 아이템 추가하기({user_id, content})
def add_item(request):
    if request.method == 'POST':
        user_id = request.POST['user_id']
        content = request.POST['content']
        user = User.objects.get(user_id)
        item = CheckList(user=user, content=content, checked=False)
        checklists = list(CheckList.objects.filter(user=user))
        data = {'items': checklists}
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
        data = {'items': checklists}
        return Response(data=data, status=status.HTTP_200_OK)

# 해당 체크리스트 아이템 삭제({user_id, check_id})
@api_view(['POST'])
def delete_item(request):
    if reques.method == 'POST':
        check_id = request.POST['check_id']
        user_id = request.POST['user_id']
        item = CheckList.objects.get(pk=check_id)
        item.delete()
        checklists = CheckList.objects.filter(user=User.objects.get(pk=user_id))
        data = {'items': checklists}
        return Response(data=data, status=status.HTTP_200_OK)

# 해당 체크리스트 아이템 수정({user_id, check_id, content})
@api_view(['POST'])
def edit_item(request):
    if reques.method == 'POST':
        check_id = request.POST['check_id']
        user_id = request.POST['user_id']
        content = request.POST['content']
        item = CheckList.objects.get(pk=check_id)
        item.content = content
        item.save()
        checklists = CheckList.objects.filter(user=User.objects.get(pk=user_id))
        data = {'items': checklists}
        return Response(data=data, status=status.HTTP_200_OK)