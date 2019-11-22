from django.conf.urls import url
from api.views import auth_views
from rest_framework_jwt.views import obtain_jwt_token

urlpatterns = [
    url('auth/signup-many/$', auth_views.signup_many, name='sign_up_many'),
    url('login/$', obtain_jwt_token),
    url('users/$',auth_views.users, name='users')
]
