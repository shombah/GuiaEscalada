package com.example.guiaescalada;

import java.util.ArrayList;

import com.example.guiaescalada.beans.Escala;
import com.example.guiaescalada.beans.Grado;
import com.example.guiaescalada.parser.EscalasParser;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.AlertDialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class Convertidor extends ListaLugares{
	
    ArrayList<Escala> listaEscalas = new ArrayList<Escala>();
 	ArrayList<Grado> gradosEscala = new ArrayList<Grado>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.convertidor);
		LayoutInflater inflater = LayoutInflater.from(this);
	    View builderView = inflater.inflate(R.layout.wheel_item_time, null);
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setView(builderView);       
        this.title.setText("Convertidor de graduación");
        
	    //Cargo los datos con el parser
	    AssetManager assetMan = getAssets();
        EscalasParser oEscalasParser = new EscalasParser(assetMan);
        listaEscalas = oEscalasParser.getEscalas();
	    
        
        final WheelView wheelEscalaOriginal = (WheelView) findViewById(R.id.escalasOriginalWheel);
        final WheelView wheelGrados  = (WheelView) findViewById(R.id.gradosWheel);
        final WheelView wheelEscalaObjetivo = (WheelView) findViewById(R.id.escalasObjetivoWheel);
        final TextView resultados = (TextView) findViewById(R.id.resultado_conversion);
        final String textoDefault = "Resultado: ";
        //Array de Strings que contiene los nombres de las escalas, ya que en el wheel solo es posible poner texto.
        final String[] escalas = new Escala().retornaNombresEscalas(listaEscalas);
        
        //Creo el adaptador del Wheel, con los Strings que va a contener.
        ArrayWheelAdapter<String> adapterEscalaOriginal = new ArrayWheelAdapter<String>(this, escalas);
	    adapterEscalaOriginal.setTextSize(18); //Configuro el texto
	    wheelEscalaOriginal.setViewAdapter(adapterEscalaOriginal);//Seteo el adaptador al wheel
	    wheelEscalaOriginal.setCurrentItem(0);//Selecciono el primer item por defecto
        
	    //Obtengo los grados de la escala, los retorno como arraylist de Strings
	    gradosEscala = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaOriginal.getCurrentItem()).getNombre()).getGrados(); //retorno los grados de la escala seleccionada en el wheel (En un inicio es la primera) 
        //Creo un Arraylist de Strings con los nombres de los grados, ya que solo puedo poner texto en el wheel
	    String[] grados = new String[gradosEscala.size()];
        for(int i = 0; i < gradosEscala.size(); i++)
        	grados[i] = gradosEscala.get(i).toString(); //Copio los nombres de los grados al arraylist
        
        //Creo el adapter de grados con el arraylist de strings, estos son los grados de la primera escala
        ArrayWheelAdapter<String> adapterGrados = new ArrayWheelAdapter<String>(this,grados);
	    adapterGrados.setTextSize(18);
	    wheelGrados.setViewAdapter(adapterGrados);
	    wheelGrados.setCurrentItem(0);
	   
	    //Creo el wheel de escala objetivo
	    ArrayWheelAdapter<String> adapterEscalaObjetivo = new ArrayWheelAdapter<String>(this, escalas);
	    adapterEscalaObjetivo.setTextSize(18);
	    wheelEscalaObjetivo.setViewAdapter(adapterEscalaObjetivo);
	    wheelEscalaObjetivo.setCurrentItem(1);
	    
	    //Agrego un listener a la escala original para actualizar el wheel de los grados.
	    wheelEscalaOriginal.addChangingListener(new OnWheelChangedListener(){
	    	@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
	    		//Obtengo los grados de la escala que esta actualmente seleccionada en el Wheel EscalaOriginal
	    		gradosEscala = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaOriginal.getCurrentItem()).getNombre()).getGrados(); //retorno los grados de la primera escala
	            //Creo el arraylist para iniciar el adapter del wheel grados
	    		String[] grados = new String[gradosEscala.size()];
	            for(int i = 0; i < gradosEscala.size(); i++)
	            	grados[i] = gradosEscala.get(i).toString(); //Transformo los grados de la escala seleccionada en un Arraylist de Strings
	            
	            //Creo un nuevo adapter para los grados
	            ArrayWheelAdapter<String> adapterGrados = new ArrayWheelAdapter<String>(Convertidor.this, grados);
	            adapterGrados.setTextSize(18);
	    	    wheelGrados.setViewAdapter(adapterGrados);
	    	    wheelGrados.setCurrentItem(0);
	    	    //Sha está, el wheel de grados se actualiza según la escala original que se selecciona en el wheel correspondiente.
			
	    	    //Buscar el grado seleccinado en la escala objetivo.
	    	    Escala escalaObjetivo = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaObjetivo.getCurrentItem()).getNombre());
	    	    int gradoObjetivo = new Grado(0,0,null).retornaGradoSegunNombre(gradosEscala, gradosEscala.get(wheelGrados.getCurrentItem()).getDificultad()).getId();
	    	    resultados.setText(textoDefault + escalaObjetivo.buscaGrado(gradoObjetivo));
	    	}
	    });
	    
	    wheelGrados.addChangingListener( new OnWheelChangedListener(){
	    	@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
	    		//Obtengo los grados de la escala que esta actualmente seleccionada en el Wheel EscalaOriginal
	    		gradosEscala = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaOriginal.getCurrentItem()).getNombre()).getGrados(); 
	    		Escala escalaObjetivo = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaObjetivo.getCurrentItem()).getNombre());
	    	    int gradoObjetivo = new Grado(0,0,null).retornaGradoSegunNombre(gradosEscala, gradosEscala.get(wheelGrados.getCurrentItem()).getDificultad()).getId();
	    	    resultados.setText(textoDefault + escalaObjetivo.buscaGrado(gradoObjetivo));
	    	}
	    });
	    
	    wheelEscalaObjetivo.addChangingListener( new OnWheelChangedListener(){
	    	@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
	    		//Obtengo los grados de la escala que esta actualmente seleccionada en el Wheel EscalaOriginal
	    		gradosEscala = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaOriginal.getCurrentItem()).getNombre()).getGrados(); 
	    		Escala escalaObjetivo = new Escala().retornaEscalaSegunNombre(listaEscalas, listaEscalas.get(wheelEscalaObjetivo.getCurrentItem()).getNombre());
	    	    int gradoObjetivo = new Grado(0,0,null).retornaGradoSegunNombre(gradosEscala, gradosEscala.get(wheelGrados.getCurrentItem()).getDificultad()).getId();
	    	    resultados.setText(textoDefault + escalaObjetivo.buscaGrado(gradoObjetivo));
	    	}
	    });
	}
}