from django.conf.urls import url
from api.views import auth_views

urlpatterns = [
    url('auth/signup/$', auth_views.signup, name='sign_up'),
    url('users/$', auth_views.users, name='users')
]
