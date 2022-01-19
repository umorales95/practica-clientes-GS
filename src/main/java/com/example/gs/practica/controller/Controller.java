package com.example.gs.practica.controller;

import java.util.HashMap;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.gs.practica.model.ModelCliente;
import com.example.gs.practica.service.ServiceClienteImpl;

@RestController
public class Controller {

	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	
	@Autowired
	ServiceClienteImpl service ;
	@GetMapping(path = "/cliente")
	public ResponseEntity<?> getClient(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "nombre", required = false) String nombre, @RequestParam(value = "correo", required = false) String correo) {
		
		return service.getClient(id, nombre, correo);
		
	}
	
	@PostMapping(path = "/cliente")
	public ResponseEntity<?> createClient(@RequestBody ModelCliente client){
		
		
		return service.createClient(client);
	}
	
	@DeleteMapping(path = "/cliente/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable (value = "id") String id){
		
		return service.deleteClient(id);
	}
	
	@PutMapping(path = "/cliente/{id}")
	public ResponseEntity<?> putClient (@PathVariable (value = "id") String id, @RequestBody ModelCliente cliente){
		
		return service.putClient(id, cliente);
		
	}
	

}
