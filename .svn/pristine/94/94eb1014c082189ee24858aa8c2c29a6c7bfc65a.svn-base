package com.example.guiaescalada;

import java.util.ArrayList;

import com.example.guiaescalada.adapter.Lista_adaptador;
import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.beans.Zona;
import com.example.guiaescalada.R;

import junit.framework.Assert;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Topos extends ListaLugares{
	
	View imagen, imagenAmpliada;
	LinearLayout llImagen, llInfo;
	ListView lista;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState); 
       setContentView(R.layout.topos);
       final Context contexto = this;
       ArrayList<Zona> listaZonas= new ArrayList<Zona>();
       Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
       this.title.setText(lugar.getNombre());
       listaZonas=lugar.getZonas();
       final String nombre = lugar.getImagen_lugar();
       
       lista = (ListView) findViewById(R.id.ListView_lista_zonas);
       lista.setBackgroundResource(R.color.negro50);
       llImagen = (LinearLayout) findViewById(R.id.llImagen);
       llInfo = (LinearLayout) findViewById(R.id.llInformacion);
       imagen = (View) findViewById(R.id.imagenlugar);
       imagenAmpliada = (View) findViewById(R.id.imagenlugarAmpliada);
       
       lista.setAdapter(new Lista_adaptador(this, R.layout.entrada_zona, listaZonas){
			@Override
			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
		            TextView textView_nombre = (TextView) view.findViewById(R.id.textView_nombre);
		            if (textView_nombre != null)
		            {
		            	textView_nombre.setText(((Zona) entrada).getNombre()); 
		            	textView_nombre.setTypeface(null, Typeface.ITALIC);
		            }
		            TextView textView_rutas = (TextView) view.findViewById(R.id.textView_rutas); 
		            if (textView_rutas != null)
		            {
		            	textView_rutas.setText(" rutas: "+((Zona) entrada).cantidad_rutas((Zona) entrada));
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
        
       lista.setOnItemClickListener(new OnItemClickListener() { 
    	   @Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				Zona zona= (Zona) pariente.getItemAtPosition(posicion);
				Intent intent = new Intent ("com.example.guiaescalada.zona");
				intent.putExtra("parametro",zona);
				startActivity(intent); 
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
