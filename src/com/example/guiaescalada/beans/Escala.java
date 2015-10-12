package com.example.guiaescalada.beans;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Escala implements Serializable{
	private Integer id;
	private String nombre;
	private ArrayList<Grado> grados;
	
	//constructor
	public Escala(Integer id, String nombre){
		this.id = id;
		this.nombre = nombre;
		grados = new ArrayList<Grado>();
	}

	public Escala() {
	}

	// metodos set y get
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombre()
	{return this.nombre;}
	public ArrayList<Grado> getGrados() {
		return grados;
	}
	public void setGrados(ArrayList<Grado> grados){
		this.grados = grados;
	}
	
	//FUNCION agregar grado 
	public void agregarGrado (Grado grado){
		grados.add(grado);//agrego el grado a la escala 
	}

	public String buscaGrado(Integer id){
		grados = this.getGrados();
		for(int i = 0; i<grados.size(); i++){
			if(id == grados.get(i).getId()){
				return grados.get(i).getDificultad() ;
			}
		}
		return "";
	}
	
	//Retorna arraylist de Strings con los nombres de las escalas dado cierto arraylist de escalas.
	public String[] retornaNombresEscalas(ArrayList<Escala> escalas){
		String[] nombreEscalas = new String[escalas.size()];
		for(int i = 0 ; i<escalas.size(); i++){
			nombreEscalas[i] = capitalize(escalas.get(i).toString());
		}
		return nombreEscalas;
	}
	
	//Retorna el objeto Escala comparando por el nombre, dado un arraylist de eescalas
	public Escala retornaEscalaSegunNombre(ArrayList<Escala> escalas, String nombre){
		for(int i = 0; i<escalas.size(); i++){
			if(nombre.equals(escalas.get(i).getNombre()))
				return escalas.get(i);
		}
		return null;
	}
	
	//Pone en Mayusculas la primera letra de un string
	private String capitalize(final String line) {
		   return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}
}
