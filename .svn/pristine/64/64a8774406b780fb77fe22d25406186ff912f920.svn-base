package com.example.guiaescalada;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.R;

import android.os.Bundle;
import android.widget.TextView;

public class DondeAcampar  extends ListaLugares{
	
	TextView informacionDondeAcampar;
	@Override  
	protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState); 
	       setContentView(R.layout.dondeacampar);
	       
	       informacionDondeAcampar = (TextView) findViewById(R.id.informacionDondeAcampar);
	       Lugar lugar = (Lugar)getIntent().getExtras().getSerializable("parametro");
	       informacionDondeAcampar.setText(lugar.getAcampar());
	       this.title.setText(lugar.getNombre());
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}
