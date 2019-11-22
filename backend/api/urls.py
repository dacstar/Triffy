from django.conf.urls import url
from api.views import auth_views
from api.views import pre_travel_views

urlpatterns = [
    url('auth/signup/$', auth_views.signup, name='sign_up'),
    url('users/$', auth_views.users, name='users'),
    url('get_balance/$', pre_travel_views.get_balance, name='get_balance'),
]
