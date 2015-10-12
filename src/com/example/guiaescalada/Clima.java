package com.example.guiaescalada;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.beans.Weather;
import com.example.guiaescalada.parser.WeatherParser;
import com.example.guiaescalada.util.WeatherHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class Clima extends ListaLugares {

	private ArrayList<Button> botones = new ArrayList<Button>(5);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clima);
		encuentraBotones();
		final Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
		this.title.setText("Clima de " + lugar.getNombre());
		XMLWeatherTask task = new XMLWeatherTask();
		String[] coordenadas = lugar.getCoordenadas().split(",");
		
		task.execute(new String[]{"lat=" + coordenadas[0] + "&lon=" + coordenadas[1]});
	}
	
	private class XMLWeatherTask extends AsyncTask<String, Void, ArrayList<Weather>> {
		
		@Override
		protected ArrayList<Weather> doInBackground(String... params) {
			ArrayList<Weather> weather = null;
			String data = ((new WeatherHttpClient()).getWeatherData(params[0]));
			
			if (data.indexOf("Error") != -1) {
				  return weather;
			}else{
				try {
					weather = new ArrayList<Weather>();
					weather = WeatherParser.getWeather(data);
				}catch (JSONException e) {				
					e.printStackTrace();
				}
				return weather;
			}
		}
		
		@Override
		protected void onPostExecute(ArrayList<Weather> weather) {			
			super.onPostExecute(weather);
			if(weather != null){
				for(int i = 0; i < weather.size(); i++){
					//Url del icono
					String icn = "http://openweathermap.org/img/w/"+weather.get(i).currentCondition.getIcon()+".png";
					
					if(i == 0)
						new DownloadImageTask((ImageView) findViewById(R.id.iconoclima0)).execute(icn);
					if(i == 1)
						new DownloadImageTask((ImageView) findViewById(R.id.iconoclima1)).execute(icn);
					if(i == 2)
						new DownloadImageTask((ImageView) findViewById(R.id.iconoclima2)).execute(icn);
					if(i == 3)
						new DownloadImageTask((ImageView) findViewById(R.id.iconoclima3)).execute(icn);
					if(i == 4)
						new DownloadImageTask((ImageView) findViewById(R.id.iconoclima4)).execute(icn);
					
					String input_date = weather.get(i).currentCondition.getFecha();
					SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
					Date dt1 = null;
					try {
						dt1 = format1.parse(input_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					DateFormat format2 = new SimpleDateFormat("EEEE"); 
					String finalDay = Character.toUpperCase(format2.format(dt1).charAt(0)) + format2.format(dt1).substring(1);
					
					if(i == 0){
						botones.get(i).setText(
						finalDay + " " + weather.get(i).currentCondition.getFecha().split("/")[0] +
						"\nCondición : " + weather.get(i).currentCondition.getDescr() + 
						"\nT° Actual: " + weather.get(i).temperature.getTemp() + "°C\n" +
						"T° Máxima: " + weather.get(i).temperature.getMaxTemp() + "°C\n" +
						"T° Mínima: " + weather.get(i).temperature.getMinTemp() +  "°C\n");
					}else{
						botones.get(i).setText(
						finalDay + " " + weather.get(i).currentCondition.getFecha().split("/")[0] + 
						"\nCondición : " + weather.get(i).currentCondition.getDescr() + 
						"T° Máxima: " + weather.get(i).temperature.getMaxTemp() + "°C\n" +
						"T° Mínima: " + weather.get(i).temperature.getMinTemp() +  "°C\n");
					}
				}
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(Clima.this);
				builder.setMessage("El servidor del clima está ocupado en estos momentos\nIntente más tarde por favor")
				       .setTitle("Error");
				builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
				            dialog.dismiss();
				            finish();
			           }
			    });
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
   }
	
   @Override
   public void onBackPressed() {
	   finish();
   }
   
   public void encuentraBotones(){	
	   for(int i = 0; i<5;i++){
		  Button boton = new Button(Clima.this);
		  if(i==0)
			  boton = (Button)findViewById(R.id.clima_0);
		  if(i==1)
			  boton = (Button)findViewById(R.id.clima_1);
		  if(i==2)
			  boton = (Button)findViewById(R.id.clima_2);
		  if(i==3)
			  boton = (Button)findViewById(R.id.clima_3);
		  if(i==4)
			  boton = (Button)findViewById(R.id.clima_4);
		  botones.add(boton);
	   }
   }
}


class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	
	  ImageView bmImage;

	  public DownloadImageTask(ImageView bmImage) {
	      this.bmImage = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);	  
      }
}
