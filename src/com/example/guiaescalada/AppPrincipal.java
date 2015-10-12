package com.example.guiaescalada;

import java.util.ArrayList;

import com.example.guiaescalada.adapter.MyFragmentPagerAdapter;
import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.beans.Ruta;
import com.example.guiaescalada.beans.Zona;
import com.example.guiaescalada.parser.LugaresParser;
import com.example.guiaescalada.util.SliderPageFragment;
import com.example.guiaescalada.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class AppPrincipal extends FragmentActivity {
	
	public static ArrayList<Lugar> listaLugares = new ArrayList<Lugar>();
	public static ArrayList<Lugar> lugaresVisitados = null;
	private Button Busqueda, ListaLugares, contacto, convertidor;
	private LinearLayout layoutPager;
	private ViewPager pager = null;
	private TextView lblLugaresVisitados;
	
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
    	SharedPreferences sharedPreferences = getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    	
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.app_principal);

        pager = (ViewPager) findViewById(R.id.pager);
        layoutPager = (LinearLayout) findViewById(R.id.layoutPager);
        lblLugaresVisitados = (TextView) findViewById(R.id.lblLugaresVisitados);
        Busqueda = (Button) findViewById(R.id.buscar);
        ListaLugares = (Button) findViewById(R.id.listaLugares);
        contacto = (Button) findViewById(R.id.contacto);
        convertidor = (Button) findViewById(R.id.convertidor);
        
        try { 
        	AssetManager assetMan = getAssets();
        	LugaresParser oLugaresParser = new LugaresParser(assetMan);
        	listaLugares = oLugaresParser.getLugares();
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        lugaresVisitados = null;
        
        for(int i = 3; i>=0; i--){//veo los 4 primeros nombres guardados en sp y agrego el objeto al arraylist
        	for(int j = 0; j<listaLugares.size(); j++){
        		if(sharedPreferences.getString("Numero" + i, "").equals(listaLugares.get(j).getNombre()) && lugaresVisitados == null){
        			lugaresVisitados = new ArrayList<Lugar>();
        			lugaresVisitados.add(listaLugares.get(j));
            		break;
        		}else if(sharedPreferences.getString("Numero" + i, "").equals(listaLugares.get(j).getNombre()) && lugaresVisitados != null){
            		lugaresVisitados.add(listaLugares.get(j));
            		break;
            	}
        	}
        }
        
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        if(lugaresVisitados != null){
        	
        	if(lugaresVisitados.size() == 1)
        		lblLugaresVisitados.setText("�ltimo lugar visitado");
        	if(lugaresVisitados.size() == 2)
        		lblLugaresVisitados.setText("�ltimos 2 lugares visitados");
        	if(lugaresVisitados.size() == 3)
        		lblLugaresVisitados.setText("�ltimos 3 lugares visitados");
        	if(lugaresVisitados.size() == 4)
        		lblLugaresVisitados.setText("�ltimos 4 lugares visitados");
        	
	        for(int i = 0; i < lugaresVisitados.size(); i++){
	        	adapter.addFragment(SliderPageFragment.newInstance(getResources().getColor(R.color.transparent), i, lugaresVisitados.get(i), lugaresVisitados.size()));
	        }
            pager.setAdapter(adapter);
        }else{
        	layoutPager.setVisibility(View.INVISIBLE);
        }
        
        convertidor.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent ("com.example.guiaescalada.convertidor");
				startActivity(intent);
			}
		});
        
        ListaLugares.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent ("com.example.guiaescalada.listatopos");
				startActivity(intent);
			}
		});
        
        contacto.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent ("com.example.guiaescalada.contacto");
				startActivity(intent);
			}
		});
        
        Busqueda.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		final EditText txtTexto = (EditText)findViewById(R.id.text_buscar);
        		String nombre = txtTexto.getText().toString();
        		if(!nombre.isEmpty()){
        			int aux = 0;
        			aux = buscado(nombre, listaLugares); //ESTO FUNCIONA NO TOCAR 1 PARA LUGAR 2 PARA ZONA 3 PARA RUTA 4 NO HAY NADA
        		
        			if(aux == 1){
        				Lugar lugar = buscaLugar(nombre, listaLugares);
        				Intent intent = new Intent ("com.example.guiaescalada.menulugar");
        				intent.putExtra("parametro",lugar);
        				startActivity(intent);	
        			}
        			if(aux == 2){
        				Zona zona;
        				zona = buscaZona(nombre, listaLugares);
        				Intent intent = new Intent ("com.example.guiaescalada.zona");
        				intent.putExtra("parametro",zona);
        				startActivity(intent);	
        			}
        			if(aux == 3){
        				Zona zona;
        				zona = buscaRuta(nombre, listaLugares);
        				Intent intent = new Intent ("com.example.guiaescalada.zona");
        				intent.putExtra("parametro", zona);
        				startActivity(intent);	
        			}
        			if(aux == 0){
        				AlertDialog.Builder builder = new AlertDialog.Builder(AppPrincipal.this);
        				builder.setMessage("Su busqueda no fue encontrada")
        				        .setTitle("Informacion")
        				        .setCancelable(false)
        				        .setNeutralButton("Aceptar",
        				                new DialogInterface.OnClickListener() {
        				                    public void onClick(DialogInterface dialog, int id) {
        				                        dialog.cancel();
        				                    }
        				                });
        				AlertDialog alert = builder.create();
        				alert.show();
        			}
        		}
        		else{
        			AlertDialog.Builder builder = new AlertDialog.Builder(AppPrincipal.this);
    				builder.setMessage("Debe ingresar texto para buscar")
    				        .setTitle("Error")
    				        .setCancelable(false)
    				        .setNeutralButton("Aceptar",
    				                new DialogInterface.OnClickListener() {
    				                    public void onClick(DialogInterface dialog, int id) {
    				                        dialog.cancel();
    				                    }
    				                });
    				AlertDialog alert = builder.create();
    				alert.show();
        		}
        	}
			
	        
        });
    }
    
    // funcion que busca el nombre los 3 arraylist entregando el arraylist en que hubo concidencia de nombres para despues ser buscado
    public int buscado(String nombre, ArrayList<Lugar> lugares){
		Lugar lugar;
		Zona zona;
		Ruta ruta;
		ArrayList<Zona> zonas = new ArrayList<Zona>();
		ArrayList<Ruta> rutas = new ArrayList<Ruta>();
		
		for (int i = 0; i < lugares.size(); i++){
			lugar = lugares.get(i);
			if(nombre.equalsIgnoreCase(lugar.getNombre()))
				return 1; // fue encontrado en lugar
			
			zonas = lugar.getZonas();
			for(int j = 0; j < zonas.size(); j++){
				zona = zonas.get(j);
				if(nombre.equalsIgnoreCase(zona.getNombre()))
					return 2; //fue encontrado en zona
				
				rutas = zona.getRutas();
				for(int k = 0; k < rutas.size(); k++){
					ruta = rutas.get(k);
					if(nombre.equalsIgnoreCase(ruta.getNombre()))
						return 3; //fue encontrado en ruta
				}	
			}
		}
		return 0;
    }
    
    //busca el nombre en lugares retornando el objeto si lo encuentra
	public Lugar buscaLugar(String nombre, ArrayList<Lugar> lugares){
		Lugar lugar;
		for (int i = 0; i < lugares.size(); i++){
			lugar = lugares.get(i);
			if(nombre.equalsIgnoreCase(lugar.getNombre()))
				return lugar;
		}
		return null;
	}
	
	// busca el nombre en zona retornando el objeto si lo encuentra
	public Zona buscaZona(String nombre, ArrayList<Lugar> lugares){
		Lugar lugar;
		Zona zona;
		for (int i = 0; i < lugares.size(); i++){
			lugar = lugares.get(i);
			zona = lugar.buscar_zona(nombre);
			if(zona != null)
				return zona;
		}
		return null;
	}
	
	// busca el nombre en rutas retornando la zona si lo encuentra
	public Zona buscaRuta(String nombre, ArrayList<Lugar> lugares){
		Lugar lugar;
		Zona zona;
		for (int i = 0; i < lugares.size(); i++){
			lugar = lugares.get(i);
			zona = lugar.buscar_rutas(nombre);
			if(zona != null){
				return zona;
			}
		}
		return null;
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setTitle("Confirmar");
	    builder.setMessage("�Desea cerrar la aplicaci�n?");

	    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	finish();
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
	        @Override
	        public void onClick(DialogInterface dialog, int which){
	            dialog.dismiss();
	        }
	    });
	    
	    AlertDialog alert = builder.create();
	    alert.show();
	}
}