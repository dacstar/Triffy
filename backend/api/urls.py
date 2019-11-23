from django.conf.urls import url
from api.views import auth_views, rapidapi_views

urlpatterns = [
    url('auth/signup/$', auth_views.signup, name='sign_up'),
    url('users/$', auth_views.users, name='users'),
    url('show_airplane/$', rapidapi_views.show_airplane, name='show_airplane')
]
