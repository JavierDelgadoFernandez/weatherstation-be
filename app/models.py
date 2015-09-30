from app import db


class Measure(db.Model):
	id = db.Column(db.Integer, primary_key=True)
	date = db.Column(db.DateTime)
	temperature = db.Column(db.Float)
	pressure = db.Column(db.Float)
	humidity = db.Column(db.Float)
	
