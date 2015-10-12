package com.example.guiaescalada.beans;

import android.location.Location;

public class Weather {
	
	public Location location;
	public CurrentCondition currentCondition = new CurrentCondition();
	public Temperature temperature = new Temperature();
	public Wind wind = new Wind();
	public Rain rain = new Rain();
	public Clouds clouds = new Clouds();
	
	public byte[] iconData;
	
	public  class CurrentCondition {
		private int weatherId;
		private String condition;
		private String descr;
		private String icon;
		private String Fecha;
		public String getFecha() {
			String dia, mes, ano;
			dia = this.Fecha.split("-")[2];
			mes = this.Fecha.split("-")[1];
			ano = this.Fecha.split("-")[0];
			return dia+"/"+mes+"/"+ano;
		}
		public void setFecha(String fecha) {
			Fecha = fecha;
		}
		
		public int getWeatherId() {
			return weatherId;
		}
		public void setWeatherId(int weatherId) {
			this.weatherId = weatherId;
		}
		public String getCondition() {
			return condition;
		}
		public void setCondition(String condition) {
			this.condition = condition;
		}
		public String getDescr() {
			return descr;
		}
		public void setDescr(String descr) {
			this.descr = descr;
		}
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
	}
	
	public  class Temperature {
		private float temp;
		private float minTemp;
		private float maxTemp;
		private float evening;
		private float morning;
		
		public float getEvening() {
			return evening;
		}
		public void setEvening(float evening) {
			this.evening = evening;
		}
		public float getMorning() {
			return morning;
		}
		public void setMorning(float morning) {
			this.morning = morning;
		}
		public float getTemp() {
			return temp;
		}
		public void setTemp(float temp) {
			this.temp = temp;
		}
		public float getMinTemp() {
			return minTemp;
		}
		public void setMinTemp(float minTemp) {
			this.minTemp = minTemp;
		}
		public float getMaxTemp() {
			return maxTemp;
		}
		public void setMaxTemp(float maxTemp) {
			this.maxTemp = maxTemp;
		}
	}
	
	public  class Wind {
		private float speed;
		private float deg;
		
		public float getSpeed() {
			return speed;
		}
		public void setSpeed(float speed) {
			this.speed = speed;
		}
		public float getDeg() {
			return deg;
		}
		public void setDeg(float deg) {
			this.deg = deg;
		}
	}
	
	public  class Rain {
		private String time;
		private float ammount;
		
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public float getAmmount() {
			return ammount;
		}
		public void setAmmount(float ammount) {
			this.ammount = ammount;
		}
	}
	
	public  class Clouds {
		private int perc;

		public int getPerc() {
			return perc;
		}
		public void setPerc(int perc) {
			this.perc = perc;
		}
	}
}