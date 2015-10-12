package com.example.guiaescalada.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Ruta implements Serializable{
	private Integer id_ruta;
	private Integer id_zona;
	private String nombre;
	private String dificultad;
	
	//constructor
	public Ruta(Integer id_ruta, Integer id_zona, String nombre,String dificultad) {
		this.id_ruta = id_ruta;
		this.id_zona = id_zona;
		this.nombre = nombre;
		this.dificultad = dificultad;
	}
	
	// metodos set y get
	public Integer getId_ruta() {
		return id_ruta;
	}
	public void setId_ruta(Integer id_ruta) {
		this.id_ruta = id_ruta;
	}
	public Integer getId_zona() {
		return id_zona;
	}
	public void setId_zona(Integer id_zona) {
		this.id_zona = id_zona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDificultad() {
		return dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
}