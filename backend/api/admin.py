from django.contrib import admin
from .models import Profile, Balance, Calendar, CheckList, Post, City

admin.site.register(Profile)
admin.site.register(Balance)
admin.site.register(Calendar)
admin.site.register(CheckList)
admin.site.register(Post)
admin.site.register(City)