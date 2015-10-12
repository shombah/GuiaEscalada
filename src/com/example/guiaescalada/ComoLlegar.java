package com.example.guiaescalada;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ComoLlegar extends ListaLugares{
	
	TextView infoComoLlegar;
	Button privada, colectiva;
	LinearLayout menu;
	ScrollView info;
	
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
        setContentView(R.layout.comollegar);
       
        menu = (LinearLayout) findViewById(R.id.menuComoLlegar);
        info = (ScrollView) findViewById(R.id.scrollInfoLlegar);	
        colectiva = (Button)findViewById(R.id.locPublica);
        privada = (Button)findViewById(R.id.locPrivada);
        infoComoLlegar = (TextView)findViewById(R.id.informacionComoLlegar);
        
        final Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
        this.title.setText(lugar.getNombre());
        infoComoLlegar.setText(lugar.getLlegar());
       
        colectiva.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				menu.setVisibility(View.GONE);
				info.setVisibility(View.VISIBLE);
			}
	    });
       
        privada.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(isOnline()){
					mostrarMapa(lugar.getCoordenadas());
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(ComoLlegar.this);
					builder.setMessage("No está conectado a internet")
					       .setTitle("Error");
					builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
					            dialog.dismiss();
				           }
				    });
					AlertDialog alert = builder.create();
					alert.show();
				}
 			}
 		});
	}
	
	private void mostrarMapa(String coordenadas){
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+coordenadas));
		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		if (intent.resolveActivity(getPackageManager()) != null) {
	        startActivity(intent);
	    }
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(ComoLlegar.this.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	@Override
	public void onBackPressed() {
		if(menu.getVisibility() == View.VISIBLE){
			finish();
		}else{
			info.setVisibility(View.GONE);
			menu.setVisibility(View.VISIBLE);
		}
	}
}
