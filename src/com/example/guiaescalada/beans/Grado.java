package com.example.guiaescalada.beans;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Grado implements Serializable{
	private Integer id_escala;
	private Integer id;
	private String dificultad; 
	
	public Grado(Integer id_escala, Integer id, String dificultad) {
		this.id_escala=id_escala;
		this.id = id;
		this.dificultad = dificultad;
	}
	
	@Override
	public String toString() {
		return dificultad;
	}
	
	public Integer getId_escala() {
		return id_escala;
	}

	public void setId_escala(Integer id_escala) {
		this.id_escala = id_escala;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	//Dado un AL de grados, retorna el objeto grado buscando por el nombre dado(dificultad)
	public Grado retornaGradoSegunNombre(ArrayList<Grado> listaGrados, String nombreGrado)
	{
		for(int i = 0; i<listaGrados.size();i++)
		{
			if(nombreGrado.equals(listaGrados.get(i).toString()))
				return listaGrados.get(i);
		}
		return null;
	}
}