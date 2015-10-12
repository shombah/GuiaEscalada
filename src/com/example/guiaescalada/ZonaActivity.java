package com.example.guiaescalada;

import java.util.ArrayList;

import com.example.guiaescalada.adapter.RutasAdapter;
import com.example.guiaescalada.beans.*;
import com.example.guiaescalada.R;

import junit.framework.Assert;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ZonaActivity extends ListaLugares{
	
	View imagen, imagenAmpliada;
	LinearLayout llImagen, llInfo;
	ListView lista;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState); 
       setContentView(R.layout.zona);
       
       ArrayList<Ruta> listaRutas = new ArrayList<Ruta>();
       Zona zona = (Zona)getIntent().getExtras().getSerializable("parametro");
       this.title.setText("Zona " + zona.getNombre());
       listaRutas = zona.getRutas();
       final Context contexto = this;
       final String nombre = zona.getImagen_zona();
       
       lista = (ListView) findViewById(R.id.ListView_lista_rutas);
       lista.setBackgroundResource(R.color.negro50);
       llImagen = (LinearLayout) findViewById(R.id.llFoto);
       llInfo = (LinearLayout) findViewById(R.id.llInfo);
       imagen = (View) findViewById(R.id.imagenRutas);
       imagenAmpliada = (View) findViewById(R.id.imagenZonaAmpliada);
       
       lista.setAdapter(new RutasAdapter(this, R.layout.entrada_ruta, listaRutas){
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
		            TextView textView_nombre = (TextView) view.findViewById(R.id.textView_nombre);
		            if (textView_nombre != null)
		            {
		            	textView_nombre.setText(((Ruta) entrada).getId_ruta()+") "+((Ruta) entrada).getNombre()); 
		            	textView_nombre.setTypeface(null, Typeface.ITALIC);
		            }
		            TextView textView_rutas = (TextView) view.findViewById(R.id.textView_dificultad); 
		            if (textView_rutas != null)
		            {
		            	textView_rutas.setText("  "+((Ruta) entrada).getDificultad());
		            }
		            int id = this.getDrawable(contexto, nombre);
		            imagen.setBackgroundResource(id);
		            imagenAmpliada.setBackgroundResource(id);
				}
			}

			public int getDrawable(Context context, String name){
		        Assert.assertNotNull(context);
		        Assert.assertNotNull(name); 
		        return context.getResources().getIdentifier(name,"drawable", context.getPackageName());
		    }
		});
       
       imagen.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				llInfo.setVisibility(View.GONE);
				llImagen.setVisibility(View.VISIBLE);
			}
        });
        
        llImagen.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				llImagen.setVisibility(View.GONE);
				llInfo.setVisibility(View.VISIBLE);
			}
        });
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}