from rest_framework.response import Response
from rest_framework import status
from rest_framework.decorators import api_view
from home.models import Measure
from datetime import datetime


@api_view(['POST'])
def add_measure(request):
    measure = Measure()
    measure.temperature = request.data.get('temperature')
    measure.pressure = request.data.get('pressure')
    measure.humidity = request.data.get('humidity')
    measure.date = datetime.strptime(request.data.get('date'), "%Y-%m-%dT%H:%M:%S")
    measure.save()
    return Response(status=status.HTTP_201_CREATED)

