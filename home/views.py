from django.shortcuts import render
from models import Measure
import datetime

def home(request):
	result = {}
	measure = Measure.objects.latest('date')
	result['temperature'] = measure.temperature
	result['pressure'] = measure.pressure
	result['humidity'] = measure.humidity
	result['date'] = measure.date
	return render(request, 'home/home.html', result)