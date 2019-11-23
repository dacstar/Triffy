from django.conf.urls import url
from django.urls import path
from api.views import auth_views
<<<<<<< HEAD
from api.views import pre_travel_views
from api.views import inter_travel_views
=======
from api.views import pre_travel_views, rapidapi_views
>>>>>>> dfccdf44ac4d5a1478251a793f7ad56a373a2d9a

urlpatterns = [
    # auth 관련 api
    path('auth/signup/', auth_views.signup, name='sign_up'),
    path('auth/signin/', auth_views.signin, name='sign_in'),
    url('users/$', auth_views.users, name='users'),

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
    
    # 항공권 조회 api
    url('show_airplane/', rapidapi_views.show_airplane, name='show_airplane'),
]
