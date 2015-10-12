package com.example.guiaescalada.parser;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.example.guiaescalada.beans.Weather;

public class WeatherParser {
	
	public static ArrayList<Weather> getWeather(String data) throws JSONException  {
		
		ArrayList<Weather> pronosticoSemanal = new ArrayList<Weather>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;
	    Document document = null;
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse(new InputSource(new StringReader(data)));  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
	    
	    pronosticoSemanal = obtenerClima(document);
	    return pronosticoSemanal;
	}
	
	public static ArrayList<Weather> obtenerClima(Node nodo){
		Weather weather = null;
		ArrayList<Weather> pronosticoSemanal = new ArrayList<Weather>();
		String fecha, sInfo = "", iconCode;
		float fInfo;
		NodeList pronostico = ((Document) nodo).getElementsByTagName("time");
		
		for(int i = 0; i<pronostico.getLength(); i++) {
			weather = new Weather();
            Node dia = pronostico.item(i);
            Element e = (Element) dia;
            fecha = e.getAttribute("day");
            weather.currentCondition.setFecha(fecha);
            NodeList datosDia = dia.getChildNodes();
            for(int j = 0; j<datosDia.getLength(); j++) {
            	Element element = null;
            	Node hijo = datosDia.item(j);
            	if(hijo.getNodeType() == Node.ELEMENT_NODE){
            		element = (Element) hijo;
	            	if(element.getAttribute("name").length() > 0 && hijo.getNodeName().equals("symbol")){
	            		sInfo = element.getAttribute("name"); 
	            		iconCode = element.getAttribute("var");
	            		weather.currentCondition.setDescr(sInfo);
	            		weather.currentCondition.setIcon(iconCode);
	            	}
	            	if(element.getAttribute("type").length() > 0){
	            		sInfo = element.getAttribute("type"); 
	            		weather.currentCondition.setCondition(sInfo);
	            	}
	            	if(element.getAttribute("day").length() > 0){
	            		sInfo = element.getAttribute("day"); 
	            		fInfo = Float.parseFloat(sInfo);
	            		weather.temperature.setTemp(fInfo);
	            	}
	            	if(element.getAttribute("min").length() > 0){
	            		sInfo = element.getAttribute("min"); 
	            		fInfo = Float.parseFloat(sInfo);
	            		weather.temperature.setMinTemp(fInfo);
	            	}
	            	if(element.getAttribute("max").length() > 0){
	            		sInfo = element.getAttribute("max"); 
	            		fInfo = Float.parseFloat(sInfo);
	            		weather.temperature.setMaxTemp(fInfo);
	            	}
            	}
            }
            pronosticoSemanal.add(weather);
		}
		return pronosticoSemanal;
	}
}