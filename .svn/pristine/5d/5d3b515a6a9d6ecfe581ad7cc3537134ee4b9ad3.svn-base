package com.example.guiaescalada;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.R;

import android.os.Bundle;
import android.widget.TextView;

public class Peligros extends ListaLugares{
	
	TextView informacionPeligros;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState); 
	       setContentView(R.layout.peligros);
	       
	       informacionPeligros = (TextView)findViewById(R.id.informacionPeligros);
	       Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
	       informacionPeligros.setText(lugar.getPeligro());
	       this.title.setText(lugar.getNombre());
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
