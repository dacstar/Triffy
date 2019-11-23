from django.conf.urls import url
from django.urls import path
from api.views import auth_views
from api.views import pre_travel_views, rapidapi_views

urlpatterns = [
    path('auth/signup/', auth_views.signup, name='sign_up'),
    path('auth/signin/', auth_views.signin, name='sign_in'),
    url('users/$', auth_views.users, name='users'),
    url('get_balance/$', pre_travel_views.get_balance, name='get _balance'),
    url('checkList/$', pre_travel_views.checkList, name='check_list'),
    path('check/', pre_travel_views.check, name='check'),
    path('delete_item/', pre_travel_views.delete_item, name='delete_item'),
    path('edit_item/', pre_travel_views.edit_item, name='edit_item'),
    url('show_airplane/', rapidapi_views.show_airplane, name='show_airplane'),
    url('show_house/', rapidapi_views.show_house, name='show_house')
]
