package com.example.guiaescalada.beans;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Zona implements Serializable {
	private Integer id_zona;
	private String nombre;
	private Integer id_lugar;
	private String imagen_zona;
	private ArrayList<Ruta> rutas;
	
	
	//constructor
	public Zona(Integer id_zona, Integer id_lugar,String nombre, String imagen_zona) {
		this.id_zona = id_zona;
		this.nombre = nombre;
		this.imagen_zona=imagen_zona;
		this.id_lugar = id_lugar;
		rutas=new ArrayList<Ruta>();
	}
	
	// metodos set y get
	public Integer getId_zona() {
		return id_zona;
	}
	public void setId_zona(Integer id_zona) {
		this.id_zona = id_zona;
	}
	public Integer getId_lugar() {
		return id_lugar;
	}
	public void setId_lugar(Integer id_lugar) {
		this.id_lugar = id_lugar;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public ArrayList<Ruta> getRutas() {
		return rutas;
	}
	public void setRutas(ArrayList<Ruta> rutas) {
		this.rutas = rutas;
	}
	public String getImagen_zona() {
		return imagen_zona;
	}

	public void setImagen_zona(String imagen_zona) {
		this.imagen_zona = imagen_zona;
	}
	//Funcion agregar ruta
	public void agregarRuta (Ruta ruta){
		rutas.add(ruta);//agrega la ruta en la zona 
	}
	
	//Funcion buscar ruta. retorna la ruta 
	public Ruta buscar_ruta(String nombre){ // busca en las rutas de una zona especifica si esta ese nombre
		Ruta ruta;
		rutas = this.getRutas();
		for(int i = 0; i < rutas.size(); i++){
			ruta = rutas.get(i);
			if(nombre.equalsIgnoreCase(ruta.getNombre()))
				return ruta;
		}
		return null ;
	}
	
	//cantidad de rutas de una zona
	public int cantidad_rutas(Zona zona){
			int aux=0;
			aux=zona.getRutas().size();
			return aux;
	}

}
