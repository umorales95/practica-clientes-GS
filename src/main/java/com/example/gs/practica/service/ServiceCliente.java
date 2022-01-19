package com.example.gs.practica.service;

import org.springframework.http.ResponseEntity;

import com.example.gs.practica.model.ModelCliente;

public interface ServiceCliente {

	public ResponseEntity<?> createClient(ModelCliente client);
	
	public ResponseEntity<?> getClient(String id, String nombre, String correo);
	
	public ResponseEntity<?> deleteClient(String param);
	
	public ResponseEntity<?> putClient (String id, ModelCliente client);
	
}
