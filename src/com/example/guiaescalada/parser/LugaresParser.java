package com.example.guiaescalada.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.res.AssetManager;

import com.example.guiaescalada.beans.Lugar;
import com.example.guiaescalada.beans.Ruta;
import com.example.guiaescalada.beans.Zona;

public class LugaresParser {
	
	AssetManager assetMan;
	private ArrayList<Lugar> aListaLugares = new ArrayList<Lugar>();
	
	public LugaresParser(AssetManager assetMan){
		try {
			this.assetMan = assetMan;
			aListaLugares = cargarDatos();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Lugar> getLugares(){
		return aListaLugares;
	}
	
	public ArrayList<Lugar> cargarDatos() throws Exception{
		ArrayList<Lugar> lugares = new ArrayList<Lugar>();
		ArrayList<Ruta> rutas = new ArrayList<Ruta>();
		ArrayList<Zona> zonas = new ArrayList<Zona>();
		
		//CODIGO PARA ABRIR XML DESDE CARPETA ASSETS
		InputStream archivo_xml = null;
				
			try {
				DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				archivo_xml = assetMan.open("mistertopo2.xml");
		        Document dom = builder.parse(archivo_xml);
		        
		        lugares = this.leer_lugares(dom);
		        zonas = this.leer_zonas(dom);
		        rutas = this.leer_rutas(dom);
		        
		        int id_lugar = 0; 
				Lugar lugar;
				Zona zona;
				
				for(int i = 0; i<zonas.size(); i++){  //recorro la lista de zonas y la asigno a sus respectivos lugares 
					int cont = 0;
					zona = zonas.get(i); // asigno una zona en especifico
					id_lugar=zona.getId_lugar(); // asigno la id del lugar de la zona especifica
					while(cont<lugares.size()){ // recorro la lista de lugares para buscar el lugar con la misma id de zona 
						lugar = lugares.get(cont);// asigno un lugar en especifico para acceder a su id
						if(id_lugar == lugar.getId()){ // comparo si las id son iguales si es asi agregar la zona en la lista de zonas de ese lugar especifico
							lugar.agregarZona(zona);//agrego la zona a su lugar
						}
						cont++;
					}
				}
				  
				for(int j = 0; j<lugares.size(); j++){
					lugar = lugares.get(j);
					lugar.agregarRutas(rutas);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		return lugares;
	}
	
	public ArrayList<Lugar> leer_lugares(Node nodo){
		ArrayList<Lugar> lugares = new ArrayList<Lugar>();
		Lugar lugar = null;
		int idn = 0;
		String nombre="", zona="", descripcion="", acampar="", peligro="", llegar="", imagen_lugar="", coordenadas="";
		
		NodeList listalugares = ((Document) nodo).getElementsByTagName("lugar");
		
        for(int i = 0; i<listalugares.getLength(); i++) {
            int aux = 0;
            Node lugarcito = listalugares.item(i);
            NodeList datoslugarcito = lugarcito.getChildNodes();
            for(int j = 0; j<datoslugarcito.getLength(); j++) {
                Node dato = datoslugarcito.item(j);
                if(dato.getNodeType() == Node.ELEMENT_NODE) {
                    Node datoContenido = dato.getFirstChild();                        
                    if(datoContenido != null && datoContenido.getNodeType() == Node.TEXT_NODE){ 
                         if(aux == 0)
                     		idn = Integer.parseInt(datoContenido.getNodeValue());
                     	 if(aux == 1)
                     		nombre = datoContenido.getNodeValue();
                     	 if(aux == 2)
                     		zona = datoContenido.getNodeValue();
                     	 if(aux == 3)
                     		descripcion = datoContenido.getNodeValue();
                     	 if(aux == 4)
                     		acampar = datoContenido.getNodeValue();
                     	 if(aux == 5)
                     		peligro = datoContenido.getNodeValue();
                     	 if(aux == 6)
                     		llegar = datoContenido.getNodeValue();
                     	 if(aux == 7)
                     		imagen_lugar = datoContenido.getNodeValue();
                     	 if(aux == 8)
                     		coordenadas = datoContenido.getNodeValue();
                     	 aux=aux+1;
                    }
                }
            }
            lugar = new Lugar(idn, nombre, peligro, zona, descripcion, acampar, llegar, imagen_lugar, coordenadas);
            lugares.add(lugar);
        }	
		return lugares;	
	}
		
	public ArrayList<Zona> leer_zonas (Node nodo){
		ArrayList<Zona> zonas=new ArrayList<Zona>();
		Zona zona = null; 
		int id_zona = 0, id_lugar= 0; 
		String nombre="", imagen_zona="";
		
		NodeList listazonas = ((Document) nodo).getElementsByTagName("zona");

        for(int i=0; i<listazonas.getLength(); i++){   //recorre la lista de lugares correspondiente a la cantidad de tag lugar en el xml
            int aux=  0;
            Node zonita = listazonas.item(i);
            NodeList datoszonita = zonita.getChildNodes();
            for(int j=0; j<datoszonita.getLength(); j++) { 
                Node dato = datoszonita.item(j);
                if(dato.getNodeType()==Node.ELEMENT_NODE) {
                    Node datoContenido = dato.getFirstChild();                        
                    if(datoContenido!=null && datoContenido.getNodeType()==Node.TEXT_NODE){
                         if(aux==0)
                     		id_zona=Integer.parseInt(datoContenido.getNodeValue());
                     	 if(aux==1)
                     		id_lugar=Integer.parseInt(datoContenido.getNodeValue());
                     	 if(aux==2)
                     		nombre=datoContenido.getNodeValue();
                     	 if(aux==3)
                     		imagen_zona=datoContenido.getNodeValue();
                     	 aux=aux+1;
                    }
                }
             
            }
            zona = new Zona(id_zona, id_lugar, nombre, imagen_zona);
            zonas.add(zona);
        }	
		return zonas;	
	}
	
	public ArrayList<Ruta> leer_rutas (Node nodo){
		ArrayList<Ruta> rutas=new ArrayList<Ruta>();
		Ruta ruta=null; 
		int id_ruta=0; int id_zona=0; String nombre = null;String dificultad=null;
		
		NodeList listarutas = ((Document) nodo).getElementsByTagName("ruta");

        for(int i = 0; i<listarutas.getLength(); i++) {   //recorre la lista de lugares correspondiente a la cantidad de tag lugar en el xml
            int aux = 0;
            Node rutita = listarutas.item(i);
            NodeList datosrutita = rutita.getChildNodes();
            for(int j=0; j<datosrutita.getLength(); j++) {
                Node dato = datosrutita.item(j);
                if(dato.getNodeType()==Node.ELEMENT_NODE) {
                    Node datoContenido = dato.getFirstChild();                        
                    if(datoContenido!=null && datoContenido.getNodeType()==Node.TEXT_NODE){
                         if(aux==0)
                     		id_ruta=Integer.parseInt(datoContenido.getNodeValue());
                     	 if(aux==1)
                     		id_zona=Integer.parseInt(datoContenido.getNodeValue());
                     	 if(aux==2)
                     		nombre=datoContenido.getNodeValue();
                     	if(aux==3)
                     		dificultad=datoContenido.getNodeValue();
                     	 aux=aux+1;
                    }
                }
            }
            ruta = new Ruta(id_ruta, id_zona, nombre, dificultad);
            rutas.add(ruta);
        }
		return rutas;	
	}
}