from django.conf.urls import url

from . import views

urlpatterns = [
	url(r'add_measure/$', views.add_measure, name='add_measure'),
	url(r'lastday/$', views.lastday, name='lastday')
]