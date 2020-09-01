package com.restfull.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restfull.example.entity.Nota;
import com.restfull.example.model.MNota;
import com.restfull.example.service.NotaService;

@RestController
@RequestMapping("/v1")
public class NotaController {

	@Autowired // vamos a inyectar un bean
	@Qualifier("servicio") // cual es el bean que vamos a utilizar
	private NotaService notaService;

	// @RequestBody cuerpo de la peticion es un JSON
	/**
	 * { "nombre":"Hernan", "titulo":"MiSegundaNota", "contenido":"Este es la
	 * descripcion" }
	 * 
	 * @param nota
	 * @return
	 */
	@PutMapping("/nota")
	public boolean agregarNota(@RequestBody Nota nota) {

		return notaService.crear(nota);

	}

	/**
	 * ojo que tiene el mismo path "/nota", pero como es Post se va por otro canal
	 * { "id":2, "nombre":"Hernan", "titulo":"MiSegundaNota", "contenido":"Se
	 * modifico Este es la descripcion" }
	 * 
	 * @param nota
	 * @return
	 */
	@PostMapping("/nota")
	public boolean actualizarNota(@RequestBody Nota nota) {
		return notaService.actualizar(nota);

	}
	/**
	 * con Delete
	 * http://localhost:8090/v1/nota/2/Hernan
	 * @param id
	 * @param nombre
	 * @return
	 */
	@DeleteMapping("/nota/{id}/{nombre}")
	public boolean eliminarNota(@PathVariable("id") long id, @PathVariable("nombre") String nombre) {
		return notaService.borrar(nombre, id);

	}
		
	/**
	 * retornamos todas las notas
	 * @return
	 */
	@GetMapping("/notas")
	public List<MNota> obtenerNotas(){
		return notaService.obtener();
	}
	
}
