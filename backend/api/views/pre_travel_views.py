from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
from datetime import datetime, timedelta
from django.contrib.auth.models import User
from api.models import Profile, Balance
from pytz import timezone

@api_view(['POST'])
def get_balance(request):
    if request.method == 'POST':
        user_id = request.POST('id')
        balance = Balance.objects.get(user_id=user_id)
        data = {
            'account': balance.account,
            'name': balance.name,
            'interest': balance.interest,
            'goal_amount': balance.goal_amount,
            'now_amount': balance.now_amount,
            'start_date': balance.start_date,
            'end_date': balance.end_date
        }
        return Response(data=data, status=HTTP_200_OK)

