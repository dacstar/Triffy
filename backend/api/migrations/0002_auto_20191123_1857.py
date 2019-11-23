# Generated by Django 2.2.4 on 2019-11-23 09:57

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('api', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='posts',
            name='agreement',
            field=models.BooleanField(default=True),
        ),
        migrations.AddField(
            model_name='posts',
            name='content',
            field=models.TextField(default=''),
        ),
        migrations.AddField(
            model_name='posts',
            name='created_at',
            field=models.DateTimeField(auto_now_add=True, default=django.utils.timezone.now),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='posts',
            name='like_users',
            field=models.ManyToManyField(related_name='like_posts', to=settings.AUTH_USER_MODEL),
        ),
        migrations.AddField(
            model_name='posts',
            name='photo',
            field=models.CharField(default='', max_length=150),
        ),
        migrations.AddField(
            model_name='posts',
            name='title',
            field=models.CharField(default='', max_length=100),
        ),
        migrations.AddField(
            model_name='posts',
            name='updated_at',
            field=models.DateTimeField(auto_now=True),
        ),
        migrations.AddField(
            model_name='posts',
            name='writer',
            field=models.ForeignKey(default='', on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL),
        ),
        migrations.AlterField(
            model_name='calendar',
            name='currency',
            field=models.CharField(default='원', max_length=10),
        ),
        migrations.DeleteModel(
            name='Likes',
        ),
    ]
