package com.example.guiaescalada;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import junit.framework.Assert;

import com.example.guiaescalada.adapter.Lista_adaptador;
import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.R;

public class ListaLugares extends Activity {
	
	TextView title,tv_zonaNorte;
	boolean bAgregado, bYaEsta;
	ArrayList<Lugar> lugaresNorte, LugaresCentro, LugaresSur = new ArrayList<Lugar>();
	LinearLayout llNorte, llCentral, llSur;
	ListView listaSur, listaNorte, listaCentro;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.listalugares);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.tituloprincipal);
		title = (TextView) findViewById(R.id.title);
		title.setText("Lista de lugares");
		llNorte = (LinearLayout) findViewById(R.id.zonaNorte);
		llCentral = (LinearLayout) findViewById(R.id.zonaCentral);
		llSur = (LinearLayout) findViewById(R.id.zonaSur);
        
		lugaresNorte = lugaresNorte(AppPrincipal.listaLugares);
		LugaresCentro = lugaresCentro(AppPrincipal.listaLugares);
		LugaresSur = lugaresSur(AppPrincipal.listaLugares);
		
		listaSur = (ListView) findViewById(R.id.listaLugaresSur);
		listaCentro = (ListView) findViewById(R.id.listaLugaresCentro);
		listaNorte = (ListView) findViewById(R.id.listaLugaresNorte);
		
		tv_zonaNorte = (TextView) findViewById(R.id.tv_zonaNorte);
		try {
			listaNorte.setAdapter(new Lista_adaptador(this, R.layout.entrada, lugaresNorte){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
			            TextView nombre = (TextView) view.findViewById(R.id.nombre);
			            if (nombre != null) 
			            	nombre.setText(((Lugar) entrada).getNombre()); 

			            TextView zonas = (TextView) view.findViewById(R.id.zonas); 
			            if (zonas != null)
			            	zonas.setText("zonas: "+((Lugar) entrada).getCantidadZonas());
			            
			            TextView rutas = (TextView) view.findViewById(R.id.rutas); 
			            if (rutas != null)
			            	rutas.setText("rutas: "+((Lugar) entrada).getCantidadRutas()); 

			            ImageView imagen_lugar = (ImageView) view.findViewById(R.id.imagen_lugar); 
			            int id = this.getDrawable(ListaLugares.this, ((Lugar) entrada).getImagen_lugar());
			            imagen_lugar.setImageResource(id);
					}
				}
				public int getDrawable(Context context, String name){
			        Assert.assertNotNull(context);
			        Assert.assertNotNull(name);
			        return context.getResources().getIdentifier(name,"drawable", context.getPackageName());
			    }
	       });
			
		 /* Con esto quiero hacer que la flecha cambie de orientacion
		  * cuando se le hace click a ListaNorte. Pero deje la cagá.
		   listaNorte.setOnClickListener(new OnClickListener(){
			   @Override
			public void onClick(View v) {
				Drawable flechaNorte = tv_zonaNorte.getCompoundDrawables()[2];
				//Flecha centro
				//flecha sur
				
				if(flechaNorte == getResources().getDrawable(R.drawable.arrow_down2))
					tv_zonaNorte.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.arrow_up2, 0);
				else
					tv_zonaNorte.setCompoundDrawablesWithIntrinsicBounds( 0, 0, R.drawable.arrow_down2, 0);

			}
		   
		   });
		   */
	       listaNorte.setOnItemClickListener(new OnItemClickListener() {
	    	   
				@Override
				public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {

					bAgregado = false;
					Lugar elegido = (Lugar) pariente.getItemAtPosition(posicion);
					Intent intent = new Intent ("com.example.guiaescalada.menulugar");
					intent.putExtra("parametro", elegido);

			        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
			        Editor editor = sharedPreferences.edit();
			        
			        for(int i = 0; i<4; i++){
			        	bYaEsta = false;
			        	for(int j = 0; j<4; j++){
			        		if(sharedPreferences.getString("Numero" + j, "").equals(elegido.getNombre())){
			        			i = 4;
			        			bYaEsta = true;
			        			break;
			        		}
			        	}
			        	
			        	if(sharedPreferences.getString("Numero" + i, "").equals("") && bYaEsta == false){
		        			editor.putString("Numero" + i, elegido.getNombre());
		        			bAgregado = true;
		        			break;
			        	}
			        }
			        
			        if(bAgregado == false && bYaEsta == false){
			        	for(int i = 1; i<4; i++){
			        		int j = i-1;
			        		editor.putString("Numero" + j, sharedPreferences.getString("Numero" + i, ""));
			        	}
			        	editor.putString("Numero" + 3, elegido.getNombre());
			        }
					editor.commit();
					startActivity(intent);
				}
	       });
       } catch (Exception e) {
			e.printStackTrace();
	   }
	
		try {
			listaSur.setAdapter(new Lista_adaptador(this, R.layout.entrada, LugaresSur){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
			            TextView nombre = (TextView) view.findViewById(R.id.nombre);
			            if (nombre != null) 
			            	nombre.setText(((Lugar) entrada).getNombre()); 

			            TextView zonas = (TextView) view.findViewById(R.id.zonas); 
			            if (zonas != null)
			            	zonas.setText("zonas: "+((Lugar) entrada).getCantidadZonas());
			            
			            TextView rutas = (TextView) view.findViewById(R.id.rutas); 
			            if (rutas != null)
			            	rutas.setText("rutas: "+((Lugar) entrada).getCantidadRutas()); 

			            ImageView imagen_lugar = (ImageView) view.findViewById(R.id.imagen_lugar); 
			            int id = this.getDrawable(ListaLugares.this, ((Lugar) entrada).getImagen_lugar());
			            imagen_lugar.setImageResource(id);
					}
				}
				public int getDrawable(Context context, String name){
			        Assert.assertNotNull(context);
			        Assert.assertNotNull(name);
			        return context.getResources().getIdentifier(name,"drawable", context.getPackageName());
			    }
	       });
        
	       listaSur.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
					bAgregado = false;
					Lugar elegido = (Lugar) pariente.getItemAtPosition(posicion);
					Intent intent = new Intent ("com.example.guiaescalada.menulugar");
					intent.putExtra("parametro", elegido);

			        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
			        Editor editor = sharedPreferences.edit();
			        
			        for(int i = 0; i<4; i++){
			        	bYaEsta = false;
			        	for(int j = 0; j<4; j++){
			        		if(sharedPreferences.getString("Numero" + j, "").equals(elegido.getNombre())){
			        			i = 4;
			        			bYaEsta = true;
			        			break;
			        		}
			        	}
			        	
			        	if(sharedPreferences.getString("Numero" + i, "").equals("") && bYaEsta == false){
		        			editor.putString("Numero" + i, elegido.getNombre());
		        			bAgregado = true;
		        			break;
			        	}
			        }
			        
			        if(bAgregado == false && bYaEsta == false){
			        	for(int i = 1; i<4; i++){
			        		int j = i-1;
			        		editor.putString("Numero" + j, sharedPreferences.getString("Numero" + i, ""));
			        	}
			        	editor.putString("Numero" + 3, elegido.getNombre());
			        }
					editor.commit();
					startActivity(intent);
				}
	       });
       } catch (Exception e) {
			e.printStackTrace();
	   }
		
		try {
			listaCentro.setAdapter(new Lista_adaptador(this, R.layout.entrada, LugaresCentro){
				@Override
				public void onEntrada(Object entrada, View view) {
					if (entrada != null) {
			            TextView nombre = (TextView) view.findViewById(R.id.nombre);
			            if (nombre != null) 
			            	nombre.setText(((Lugar) entrada).getNombre()); 

			            TextView zonas = (TextView) view.findViewById(R.id.zonas); 
			            if (zonas != null)
			            	zonas.setText("zonas: "+((Lugar) entrada).getCantidadZonas());
			            
			            TextView rutas = (TextView) view.findViewById(R.id.rutas); 
			            if (rutas != null)
			            	rutas.setText("rutas: "+((Lugar) entrada).getCantidadRutas()); 

			            ImageView imagen_lugar = (ImageView) view.findViewById(R.id.imagen_lugar); 
			            int id = this.getDrawable(ListaLugares.this, ((Lugar) entrada).getImagen_lugar());
			            imagen_lugar.setImageResource(id);
					}
				}
				public int getDrawable(Context context, String name){
			        Assert.assertNotNull(context);
			        Assert.assertNotNull(name);
			        return context.getResources().getIdentifier(name,"drawable", context.getPackageName());
			    }
	       });
        
	       listaCentro.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
					bAgregado = false;
					Lugar elegido = (Lugar) pariente.getItemAtPosition(posicion);
					Intent intent = new Intent ("com.example.guiaescalada.menulugar");
					intent.putExtra("parametro", elegido);

			        SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
			        Editor editor = sharedPreferences.edit();
			        
			        for(int i = 0; i<4; i++){
			        	bYaEsta = false;
			        	for(int j = 0; j<4; j++){
			        		if(sharedPreferences.getString("Numero" + j, "").equals(elegido.getNombre())){
			        			i = 4;
			        			bYaEsta = true;
			        			break;
			        		}
			        	}
			        	
			        	if(sharedPreferences.getString("Numero" + i, "").equals("") && bYaEsta == false){
		        			editor.putString("Numero" + i, elegido.getNombre());
		        			bAgregado = true;
		        			break;
			        	}
			        }
			        
			        if(bAgregado == false && bYaEsta == false){
			        	for(int i = 1; i<4; i++){
			        		int j = i-1;
			        		editor.putString("Numero" + j, sharedPreferences.getString("Numero" + i, ""));
			        	}
			        	editor.putString("Numero" + 3, elegido.getNombre());
			        }
					editor.commit();
					startActivity(intent);
				}
	       });
       } catch (Exception e) {
			e.printStackTrace();
	   }
		llNorte.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(listaNorte.getVisibility() == View.VISIBLE){
					listaNorte.setVisibility(View.GONE);
				}else if(listaNorte.getVisibility() == View.GONE){
					listaNorte.setVisibility(View.VISIBLE);
				}
			}
		});
		
		llCentral.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(listaCentro.getVisibility() == View.VISIBLE){
					listaCentro.setVisibility(View.GONE);
				}else if(listaCentro.getVisibility() == View.GONE){
					listaCentro.setVisibility(View.VISIBLE);
				}
			}
		});
		
		llSur.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(listaSur.getVisibility() == View.VISIBLE){
					listaSur.setVisibility(View.GONE);
				}else if(listaSur.getVisibility() == View.GONE){
					listaSur.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	private ArrayList<Lugar> lugaresNorte(ArrayList<Lugar> lugares){
		ArrayList<Lugar> lugaresNorte = new ArrayList<Lugar>();
		for(int i = 0; i<lugares.size(); i++){
			if((lugares.get(i).getZona()).equals("norte"))
				lugaresNorte.add(lugares.get(i));
		}
		return lugaresNorte;
	}
	
	private ArrayList<Lugar> lugaresCentro(ArrayList<Lugar> lugares){
		ArrayList<Lugar> lugaresCentro = new ArrayList<Lugar>();
		for(int i = 0; i<lugares.size(); i++){
			if((lugares.get(i).getZona()).equals("central"))
				lugaresCentro.add(lugares.get(i));
		}
		return lugaresCentro;
	}
	
	private ArrayList<Lugar> lugaresSur(ArrayList<Lugar> lugares){
		ArrayList<Lugar> lugaresSur = new ArrayList<Lugar>();
		for(int i = 0; i<lugares.size(); i++){
			if((lugares.get(i).getZona()).equals("sur"))
				lugaresSur.add(lugares.get(i));
		}
		return lugaresSur;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent ("com.example.guiaescalada.appprincipal");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}