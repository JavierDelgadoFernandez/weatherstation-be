from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view
from home.models import Measure
from datetime import datetime, timedelta


@api_view(['POST'])
def add_measure(request):
	measure = Measure()
	measure.temperature = request.data.get('temperature')
	measure.pressure = request.data.get('pressure')
	measure.humidity = request.data.get('humidity')
	measure.date = datetime.strptime(request.data.get('date'), "%Y-%m-%dT%H:%M:%S")
	measure.save()
	return Response(status=status.HTTP_201_CREATED)

def get_dates(measures):
	result = []
	
	for measure in measures:
		result.append(measure.date.strftime('%Y-%m-%dT%H:%M:%S'))
	
	return result

def get_temperatures(measures):
	result = {}
	
	data = []
	for measure in measures:
		data.append(measure.temperature)
	
	result['name'] = "Temperature"
	result['data'] = data
	result['unit'] = "C"
	result['type'] = "line"
	result['valueDecimals'] = 1
	
	return result

def get_humidities(measures):
	result = {}
	
	data = []
	for measure in measures:
		data.append(measure.humidity)
	
	result['name'] = "Humidity"
	result['data'] = data
	result['unit'] = "%"
	result['type'] = "line"
	result['valueDecimals'] = 1
	
	return result

def get_pressures(measures):
	result = {}
	
	data = []
	for measure in measures:
		data.append(measure.pressure)
	
	result['name'] = "Pressure"
	result['data'] = data
	result['unit'] = "hPa"
	result['type'] = "line"
	result['valueDecimals'] = 1
	
	return result

@api_view(['GET'])
def lastday(request):
	date_yesterday = datetime.utcnow() - timedelta(days=1)
	measures = Measure.objects.filter(date__lte=date_yesterday)
	response = {}
	response['xData'] = get_dates(measures)
	dataset = []
	response['datasets'] = dataset
	dataset.append(get_temperatures(measures))
	dataset.append(get_humidities(measures))
	dataset.append(get_pressures(measures))
	return Response(response)

