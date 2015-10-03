from app import app, db
from models import Measure
from sqlalchemy import desc
from flask import render_template, jsonify, request
from datetime import datetime, timedelta


def last_measure():
	result = {}
	measure = Measure.query.order_by(desc(Measure.date)).limit(1)[0]
	result['date'] = measure.date
	result['temperature'] = measure.temperature
	result['humidity'] = measure.humidity
	result['pressure'] = measure.pressure
	return result


@app.route('/', methods=['GET'])
@app.route('/home', methods=['GET'])
def home():
	measure = last_measure()
	return render_template('home.html', measure=measure)


@app.route('/last_day', methods=['GET'])
def chart():
	yesterday = datetime.now() - timedelta(days=1)
	measures = Measure.query.filter(Measure.date > yesterday).all()
	if (len(measures) > 0):
		temperatures = map(lambda measure : [ datetime_epoch(measure.date), measure.temperature ], measures)
		pressures = map(lambda measure : [ datetime_epoch(measure.date), measure.pressure ], measures)
		humidities = map(lambda measure : [ datetime_epoch(measure.date), measure.humidity ], measures)
		return render_template('chart.html', temperatures=temperatures, pressures=pressures, humidities=humidities)
	return render_template('chart.html')

@app.route('/about', methods=['GET'])
def about():
	return render_template('about.html')


@app.route('/api/measure/', methods=['GET'])
def api_last_measure():
	measure = last_measure()
	return jsonify(measure)


@app.route('/api/add_measure/', methods=['POST'])
@app.route('/api/measure/', methods=['POST'])
def add_measure():
	measure = Measure()
	measure.temperature = request.json['temperature']
	measure.pressure = request.json['pressure']
	measure.humidity = request.json['humidity']
	measure.date = datetime.strptime(request.json['date'], "%Y-%m-%dT%H:%M:%S")
	db.session.add(measure)
	db.session.commit()
	return jsonify({'result': True}), 201


def datetime_epoch(dt):
	epoch = datetime.utcfromtimestamp(0)
	delta = dt - epoch
	return ((delta.microseconds + (delta.seconds + delta.days * 24 * 3600) * 10**6) / 10**6) * 1000