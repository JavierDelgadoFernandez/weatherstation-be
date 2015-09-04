from django.db import models


class Measure(models.Model):
    temperature = models.FloatField()
    pressure = models.FloatField()
    humidity = models.FloatField()
    date = models.DateTimeField()
