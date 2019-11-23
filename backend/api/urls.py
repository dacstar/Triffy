from django.conf.urls import url
from django.urls import path
from api.views import auth_views
from api.views import pre_travel_views, inter_travel_views, rapidapi_views

urlpatterns = [
    # auth 관련 api
    path('auth/signup/', auth_views.signup, name='sign_up'),
    path('auth/signin/', auth_views.signin, name='sign_in'),
    path('auth/logout/', auth_views.logout, name='logout'),
    # url('users/$', auth_views.users, name='users'),

    # pre_travel 관련 api
    url('get_balance/$', pre_travel_views.get_balance, name='get _balance'),
    url('checkList/$', pre_travel_views.checkList, name='check_list'),
    path('check/', pre_travel_views.check, name='check'),
    path('check/add_item/', pre_travel_views.add_item, name='add_item'),
    path('check/delete_item/', pre_travel_views.delete_item, name='delete_item'),
    path('check/edit_item/', pre_travel_views.edit_item, name='edit_item'),

    # inter_travel 관련 api
    url('calendar/view/$', inter_travel_views.get_calendar, name='view_calendar'),
    url('calendar/category/$', inter_travel_views.by_category, name='by_category'),
    path('calendar/add_item/', inter_travel_views.add_item, name='add_calendar'),
    path('calendar/card/', inter_travel_views.by_card, name='by_card'),
    path('calendar/switch_spent/', inter_travel_views.switch_spent, name='switch_spent'),
    path('calendar/delete_item/', inter_travel_views.delete_item, name='delte_calendar'),
    path('calendar/edit_item/', inter_travel_views.edit_item, name='edit_calendar'),

    # 항공권 조회 api
    url('show_airplane/', rapidapi_views.show_airplane, name='show_airplane'),
    url('save_airline/', rapidapi_views.save_airline, name='save_airline'),
    url('show_house/', rapidapi_views.show_house, name='show_house'),
    url('save_house/', rapidapi_views.save_house, name='save_house'),
    url('show_reserve_airline/', rapidapi_views.show_reserve_airline, name='show_reserve_airline'),
    url('show_reserve_hotel/', rapidapi_views.show_reserve_hotel, name='show_reserve_hotel')
]
