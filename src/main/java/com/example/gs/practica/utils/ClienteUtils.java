package com.example.gs.practica.utils;

import com.example.gs.practica.controller.Controller;
import com.example.gs.practica.model.ModelCliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;


@Component
public class ClienteUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteUtils.class);
	
	
	
	public List<ModelCliente> consultaArchivoClientes () {
		try
        {
			List<ModelCliente> clientes = new ArrayList<ModelCliente>();
			
            FileReader reader = new FileReader("clientes.json");
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray array = (JSONArray) obj;
            ObjectMapper objectMapper = new ObjectMapper();
            ListIterator it = array.listIterator();
            
            while (it.hasNext()) {
                ModelCliente cliente = objectMapper.convertValue(it.next(),ModelCliente.class);
                log.info("Extrayendo cliente con id: " + cliente.getId());
                clientes.add(cliente);
                
            }
            return clientes;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
		
	}
	
	public ModelCliente insertaCliente (ModelCliente cliente) {
		
		List<ModelCliente> clientes = consultaArchivoClientes();
		
		if(clientes.size() == 0) {
			cliente.setId(1);
		}else {
			cliente.setId(clientes.size()+1);
		}
		
		clientes.add(cliente);
		
		try {
			FileWriter writer = new FileWriter("clientes.json");
			Gson gson = new Gson();
			String json = gson.toJson(clientes);
			
			writer.write(json);
			writer.flush();
			writer.close();
			
			return cliente;
			
		}catch(IOException ex) {
			ex.getStackTrace();
			return null;
		}
	}
	
	
	public List<ModelCliente> consultaClientePorFiltro (String id, String nombre, String correo){
		
		List<ModelCliente> clientes = consultaArchivoClientes();
		List<ModelCliente> respuesta = new ArrayList<ModelCliente>();
		
		if(id != null && !id.isEmpty()) {
			
			for (ModelCliente clienteActual : clientes) {
				if(clienteActual.getId() == Long.parseLong(id)) {
					respuesta.add(clienteActual);
					return respuesta;
				}
			}
		}
		
		if( (correo != null && !correo.isEmpty()) && (nombre != null && !nombre.isEmpty()) ) {
			for (ModelCliente clienteActual : clientes) {
				if(clienteActual.getNombre().equals(nombre) && clienteActual.getCorreo().equals(correo)) {
					respuesta.add(clienteActual);
				}
			}
		}else {
			if(nombre != null && !nombre.isEmpty()) {
				for (ModelCliente clienteActual : clientes) {
					
					if(clienteActual.getNombre().equals(nombre)) {
						respuesta.add(clienteActual);
					}
				}
			}
			
			if(correo != null && !correo.isEmpty()) {
				for (ModelCliente clienteActial : clientes) {
					if(clienteActial.getCorreo().equals(correo)) {
						respuesta.add(clienteActial);
					}
				}
			}
		}
		
		
		return respuesta;
	}
	
	public boolean eliminaCliente (String id) {
		List<ModelCliente> clientes = consultaArchivoClientes();
		
		if(id != null && !id.isEmpty()) {
			for (ModelCliente clienteActual : clientes) {
				if(clienteActual.getId() == Long.parseLong(id)) {
					clientes.remove(clienteActual);
					
					escribeArchivo(clientes);
					
					return true;
				}
			}
		}
		return false;
		
	}
	
	public ModelCliente actualizaCliente (String id, ModelCliente cliente) {
		List<ModelCliente> clientes = consultaArchivoClientes();
		ModelCliente respuesta = new ModelCliente();
		ObjectMapper mapper = new ObjectMapper();
		if(clientes.size() > 0) {
			
			for (ModelCliente clienteA : clientes) {
				
				if(clienteA.getId() == Long.parseLong(id)) {
					
					if(cliente.getNombre() != null && !cliente.getNombre().equals("")) {
						clienteA.setNombre(cliente.getNombre());
					}
					
					if(cliente.getCorreo() != null && !cliente.getCorreo().equals("")) {
						clienteA.setCorreo(cliente.getCorreo());
					}
					respuesta = clienteA;
					break;
				}
				
			}
			
			escribeArchivo(clientes);
			return respuesta;
						
		}else {
			return null;
		}
	}
	public void escribeArchivo (List<ModelCliente> clientes) {
		
		try {
			FileWriter writer = new FileWriter("clientes.json");
			Gson gson = new Gson();
			String json = gson.toJson(clientes);
			
			writer.write(json);
			writer.flush();
			writer.close();
			
		
			
		}catch(IOException ex) {
			ex.getStackTrace();
		}
	}
	
	
	
}
