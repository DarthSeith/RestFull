package com.restfull.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfull.example.entity.Nota;
import com.restfull.example.service.NotaService;

@RestController
@RequestMapping("/v1")
public class NotaController {

	@Autowired // vamos a inyectar un bean
	@Qualifier("servicio") // cual es el bean que vamos a utilizar
	private NotaService notaService;
	
	//@RequestBody cuerpo de la peticion es un JSON
	@PutMapping("/nota")
	public boolean agregarNota(@RequestBody Nota nota  ) {
		
		return notaService.crear(nota);
		
	}
}
