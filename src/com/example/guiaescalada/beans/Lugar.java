package com.example.guiaescalada.beans;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Lugar implements Serializable{
	
	private Integer id;
	private String nombre;
	private String peligro;
	private String zona;
	private String descripcion;
	private String acampar;
	private String llegar;
	private String imagen_lugar;
	private String coordenadas;
	private ArrayList<Zona> zonas;
	
	public Lugar(Integer id, String nombre, String peligro, String zona,String descripcion, String acampar,String llegar,String imagen_lugar, String coordenadas){
		this.id = id;
		this.nombre = nombre;
		this.peligro = peligro;
		this.zona = zona;
		this.descripcion = descripcion;
		this.acampar = acampar;
		this.llegar=llegar;
		this.imagen_lugar = imagen_lugar;
		this.coordenadas = coordenadas;
		zonas = new ArrayList<Zona>();
	}
	
	public Lugar() {
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPeligro() {
		return peligro;
	}
	public void setPeligro(String peligro) {
		this.peligro = peligro;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAcampar() {
		return acampar;
	}
	public void setAcampar(String acampar) {
		this.acampar = acampar;
	}
	
	public String getLlegar() {
		return llegar;
	}

	public void setLlegar(String llegar) {
		this.llegar = llegar;
	}
	
	public String getImagen_lugar() {
		return imagen_lugar;
	}

	public void setImagen_lugar(String imagen_lugar) {
		this.imagen_lugar = imagen_lugar;
	}
	
	public ArrayList<Zona> getZonas() {
		return zonas;
	}
	public void setZonas(ArrayList<Zona> zonas) {
		this.zonas = zonas;
	}
	
	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public void agregarZona (Zona zona){
		zonas.add(zona);
	}
	
	//Funcion para agregar ruta a una zona especifica segun id
	public void agregarRutas(ArrayList<Ruta> rutas){
		Ruta ruta; 
		Zona zona;
		int id_zona = 0;
	
		for(int i = 0; i < rutas.size(); i++){
			ruta = rutas.get(i); // asigno una ruta en especifico
			id_zona = ruta.getId_zona(); // asigno la id de la zona de la ruta especifica
			int cont = 0;
			while(cont<zonas.size()){ // recorro la lista de zonas para buscar la ruta con la misma id de zona 
			zona = zonas.get(cont);// asigno una zona en especifico para acceder a su id
				if(id_zona == zona.getId_zona()){ // comparo si las id son iguales si es asi agregar la ruta en la zona  de ese lugar especifico
					zona.agregarRuta(ruta);//agrego la zona a su lugar
				}
			cont++;
			}
		}
	}
	
	//funcion que busca si se encuentra el nombre de una zona
	public Zona buscar_zona(String nombre){//busca una zona de un lugar especifica segun su nombre
		Zona zona;
		int i=0;
		for(i = 0; i<zonas.size(); i++){
			zona = zonas.get(i);
			if(nombre.equalsIgnoreCase(zona.getNombre())){
				return zona;
			}
		}
		return null;
	}
	
	//Funcion para buscar rutas segun un nombre. retorna la ruta
	public Zona buscar_rutas(String nombre){  
		Ruta ruta = null;
		Zona zona;
		for(int i = 0; i < zonas.size(); i++){
			zona = zonas.get(i);
			ruta = zona.buscar_ruta(nombre);
			if(ruta != null)
				return zona;
		}
		return null;
	}
	
	public int getCantidadZonas(){
		int aux = 0;
		aux = this.getZonas().size();
		return aux;
	}
	
	public int getCantidadRutas(){
		int i = 0, aux = 0;
		Zona zona;
		for (i = 0; i<zonas.size(); i++){
			zona = zonas.get(i);
			aux = zona.getRutas().size() + aux;
		}
		return aux;
	}
}