package com.example.guiaescalada;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.R;

import android.os.Bundle;
import android.widget.TextView;

public class DescripcionLugar extends ListaLugares{
	
	TextView informacionDescLugar;
	@Override  
	protected void onCreate(Bundle savedInstanceState){
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.descripcionlugar);
	       
	       
	       informacionDescLugar = (TextView)findViewById(R.id.informacionDescLugar);
	       Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
	       this.title.setText(lugar.getNombre());
	       informacionDescLugar.setText(lugar.getDescripcion());
	 }
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
