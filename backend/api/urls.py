from django.conf.urls import url
from api.views import auth_views
from api.views import pre_travel_views

urlpatterns = [
    url('auth/signup/$', auth_views.signup, name='sign_up'),
    url('users/$', auth_views.users, name='users'),
    url('get_balance/$', pre_travel_views.get_balance, name='get_balance'),
    url('checkList/$', pre_travel_views.checkList, name='check_list'),
    url('check/', pre_travel_views.check, name='check'),
    url('delete_item/', pre_travel_views.delete_item, name='delete_item'),
    url('edit_item/', pre_travel_views.edit_item, name='edit_item'),

    
]
