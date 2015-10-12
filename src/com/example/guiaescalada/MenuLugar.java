package com.example.guiaescalada;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuLugar extends ListaLugares{
	Button DescripcionLugar, ComoLlegar, Topos, DondeAcampar, Peligros, clima, toposIr, toposPoi, toposNormal;
	TextView titulo;
	LinearLayout opcionesLugar, opcionesRA;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.menulugar);

       final Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
       this.title.setText(lugar.getNombre());
  
       DescripcionLugar = (Button) findViewById(R.id.descripcion);
       ComoLlegar = (Button) findViewById(R.id.como_llegar);
       Topos = (Button) findViewById(R.id.ver_topos);
       DondeAcampar = (Button) findViewById(R.id.dondeAcampar);
       Peligros = (Button) findViewById(R.id.peligros);
       clima = (Button) findViewById(R.id.clima);
       opcionesLugar = (LinearLayout) findViewById(R.id.opcionesLugar);
       opcionesRA = (LinearLayout) findViewById(R.id.opcionesRA);
       toposIr = (Button) findViewById(R.id.ir);
       toposPoi = (Button) findViewById(R.id.poi);
       toposNormal = (Button) findViewById(R.id.normal);
       

       toposIr.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.realidadaumentada");
				intent.putExtra("parametro", true);
				startActivity(intent);
			}
       });
       toposPoi.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.realidadaumentada");
				intent.putExtra("parametro", false);
				startActivity(intent);
			}
       });
       toposNormal.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.topos");
				intent.putExtra("parametro", lugar);
				startActivity(intent);
			}
       });
       DescripcionLugar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.descripcionlugar");
				intent.putExtra("parametro",lugar);
				startActivity(intent);
			}
       });
       ComoLlegar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.comollegar");
				intent.putExtra("parametro",lugar);
				startActivity(intent);
			}
       });
       Topos.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				opcionesLugar.setVisibility(View.GONE);
				opcionesRA.setVisibility(View.VISIBLE);
			}
       });
       DondeAcampar.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.dondeacampar");
				intent.putExtra("parametro",lugar);
				startActivity(intent);
			}
       });
       Peligros.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				Intent intent = new Intent ("com.example.guiaescalada.peligros");
				intent.putExtra("parametro",lugar);
				startActivity(intent);
			}
       });
       clima.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if(isOnline()){
					Intent intent = new Intent ("com.example.guiaescalada.clima");
					intent.putExtra("parametro",lugar);
					startActivity(intent);
				}else{
					AlertDialog.Builder builder = new AlertDialog.Builder(MenuLugar.this);
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
	
	public boolean isOnline() {//Revisa si el celular está conectado a internet
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(MenuLugar.this.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	@Override
	public void onBackPressed() {
		if(opcionesRA.getVisibility() == View.VISIBLE){
			opcionesRA.setVisibility(View.GONE);
			opcionesLugar.setVisibility(View.VISIBLE);
		}else{
			finish();
		}
	}
}
