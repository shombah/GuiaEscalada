package com.example.guiaescalada.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {
	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
	private static String LENGUAJE_DATA = "&mode=xml&units=metric&cnt=5&lang=sp&APPID=ffc6c4f21c1c4c78bba584b397ea8888";
	
	public String getWeatherData(String location) {
		HttpURLConnection con = null ;
		InputStream is = null;

		try {
			con = (HttpURLConnection) ( new URL(BASE_URL + location + LENGUAJE_DATA)).openConnection();
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();
			
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while((line = br.readLine()) != null)
				buffer.append(line + "\r\n");
			
			is.close();
			con.disconnect();
			return buffer.toString();
	    }
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}

		return null;
	}
}
