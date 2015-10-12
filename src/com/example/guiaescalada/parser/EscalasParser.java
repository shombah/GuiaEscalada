package com.example.guiaescalada.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.res.AssetManager;

import com.example.guiaescalada.beans.Escala;
import com.example.guiaescalada.beans.Grado;

public class EscalasParser extends Activity{
	
	AssetManager assetMan;
	private ArrayList<Escala> aListaEscalas = new ArrayList<Escala>();
	
	public EscalasParser(AssetManager assetMan){
		try {
			this.assetMan = assetMan;
			aListaEscalas = cargarDatosEscalas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Escala> getEscalas(){
		return aListaEscalas;
	}
	
	public ArrayList<Escala> cargarDatosEscalas() throws Exception{
		ArrayList<Grado> grados = new ArrayList<Grado>();
	    ArrayList<Escala> escalas = new ArrayList<Escala>();
		
		//CODIGO PARA ABRIR XML DESDE CARPETA ASSETS
		InputStream archivo = null;
			
		try{
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			archivo = assetMan.open("escalasdificultad.xml");//File f = new File ("escalasdificultad.xml");
		    Document doc = builder.parse(archivo);
			
			escalas = this.leer_escalas(doc);
			grados = this.leer_grados(doc);
			archivo.close();
			
			int id_escala = 0; 
			Escala escala;
			Grado grado;
			
			for(int i = 0; i < grados.size(); i++){  //recorro la lista de grados y la asigno a sus respectiva escala 
				int cont = 0;
				grado = grados.get(i); // asigno un grado en especifico
				id_escala = grado.getId_escala(); // asigno la id de escala del grado especifico
				while(cont < escalas.size()){ // recorro la lista de escalas para buscar la escala con la misma id de grado 
					escala = escalas.get(cont);// asigno una escala en especifico para acceder a su id
					if(id_escala == escala.getId()){ // comparo si las id son iguales si es asi agrego el grado en la lista de grados de esa escala especifico
						escala.agregarGrado(grado);//agrego el grado a su escala
					}
					cont++;
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		return escalas;
	}
	
	public ArrayList<Escala> leer_escalas(Node nodo){
		
		Escala escala = null;
	    ArrayList<Escala> escalas = new ArrayList<Escala>();
		int id = 0;
		String nombre = "";
		 
		NodeList listaescalas = ((Document) nodo).getElementsByTagName("escala");//lista de escalas xml

        for(int i = 0; i < listaescalas.getLength(); i++) {   //recorre la lista de escalas correspondiente a la cantidad de tag escala en el xml
            int aux = 0;
            Node escalita = listaescalas.item(i);
            NodeList datosEscalita = escalita.getChildNodes();
            for(int j = 0; j < datosEscalita.getLength(); j++) {
                Node dato = datosEscalita.item(j);
                if(dato.getNodeType() == Node.ELEMENT_NODE) { 
                    Node datoContenido = dato.getFirstChild();                        
                    if(datoContenido != null && datoContenido.getNodeType() == Node.TEXT_NODE){ 
                         if(aux == 0)
                        	 nombre = datoContenido.getNodeValue();
                     	 if(aux == 1)
                     		 id = Integer.parseInt(datoContenido.getNodeValue());
                     	 aux = aux+1;
                    }
                }
            }
            escala = new Escala(id, nombre);
            escalas.add(escala);
        }
		return escalas;	
	}
	
	public ArrayList<Grado> leer_grados (Node nodo){
		ArrayList<Grado> grados = new ArrayList<Grado>();
		Grado grado = null; 
		int id_grado = 0, id_escala=0;
		String nombre = "";
		
		NodeList listagrados = ((Document) nodo).getElementsByTagName("grado");

        for(int i = 0; i<listagrados.getLength(); i++) {   //recorre la lista de lugares correspondiente a la cantidad de tag lugar en el xml
            int aux = 0;
            Node gradito = listagrados.item(i);
            NodeList datosgradito = gradito.getChildNodes();
            for(int j = 0; j<datosgradito.getLength(); j++) { 
                Node dato = datosgradito.item(j);
                if(dato.getNodeType() == Node.ELEMENT_NODE) {
                    Node datoContenido = dato.getFirstChild();                        
                    if(datoContenido != null && datoContenido.getNodeType() == Node.TEXT_NODE){	                    	
                         if(aux == 0){
                     		id_grado = Integer.parseInt(datoContenido.getNodeValue());
                         }
                     	 if(aux == 1)
                     		id_escala = Integer.parseInt(datoContenido.getNodeValue());
                     	 if(aux == 2)
                     		nombre = datoContenido.getNodeValue();
                     	 aux = aux+1;
                    }
                }
            }
            grado = new Grado(id_escala ,id_grado, nombre);
            grados.add(grado);
        }
		return grados;	
	}
}
