package com.example.gs.practica.service;

import static org.hamcrest.CoreMatchers.startsWith;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.gs.practica.model.ModelCliente;
import com.example.gs.practica.utils.ClienteUtils;

@Service
public class ServiceClienteImpl implements ServiceCliente {

	@Autowired
	ClienteUtils utils;
	
	
	@Override
	public ResponseEntity<?> createClient(ModelCliente client) {
		ModelCliente cliente = utils.insertaCliente(client);
		
		if(cliente != null) {
			return ResponseEntity.status(201).body(cliente);
		}else {
			return ResponseEntity.status(400).body("Error");
		}

	}

	@Override
	public ResponseEntity<?> getClient(String id, String nombre, String correo) {
		List<ModelCliente> respuesta = utils.consultaClientePorFiltro(id, nombre, correo);
		if(respuesta.size() == 0) {
			
			return ResponseEntity.status(404).body("Cliente no encontrado.");
		}
		else {
			return ResponseEntity.status(200).body(respuesta);
		}
	}

	@Override
	public ResponseEntity<?> deleteClient(String id) {
		boolean res = utils.eliminaCliente(id);
		
		if(res) {
			return ResponseEntity.status(200).body("Usuario Eliminado Correctamente.");
		}
		else {
			return ResponseEntity.status(400).body("Error al eliminar cliente.");
		}
		
	}

	@Override
	public ResponseEntity<?> putClient(String id, ModelCliente client) {
		ModelCliente respuesta = utils.actualizaCliente(id, client);
		if(respuesta != null) {
			return ResponseEntity.status(200).body(respuesta);
		}
		else {
			return ResponseEntity.status(400).body("No fue posible actualizar el cliente.");
		}
	}

}
