package com.restfull.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.restfull.example.converter.Convertidor;
import com.restfull.example.entity.Nota;
import com.restfull.example.model.MNota;
import com.restfull.example.repository.NotaRepository;

@Service("servicio")
public class NotaService {

	@Autowired // vamos a inyectar un bean
	@Qualifier("repositorio") // cual es el bean que vamos a utilizar
	private NotaRepository notaRepository;

	@Autowired // vamos a inyectar un bean
	@Qualifier("convertidor") // cual es el bean que vamos a utilizar
	private Convertidor convertidor;

	public boolean crear(Nota nota) {
		try {
			notaRepository.save(nota);
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	/**
	 * se valida si existe el objeto con el ID, si esta se modifica
	 * 
	 * @param nota
	 * @return
	 */
	public boolean actualizar(Nota nota) {
		try {
			if (notaRepository.existsById(nota.getId())) {
				notaRepository.save(nota);
			}
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	/**
	 * se busca el objeto y despues de elimina
	 * 
	 * @param nombre
	 * @param id
	 * @return
	 */
	public boolean borrar(String nombre, long id) {
		try {
			notaRepository.delete(notaRepository.findByNombreAndId(nombre, id));
			return Boolean.TRUE;
		} catch (Exception e) {
			return Boolean.FALSE;
		}
	}

	/**
	 * obtiene una lista de MNota por el titulo
	 * 
	 * @return
	 */
	public List<MNota> obtener() {
		return convertidor.convertirLista(notaRepository.findAll());
	}

	/**
	 * obtiene una nota con nombre y titulo
	 * 
	 * @param nombre
	 * @param titulo
	 * @return
	 */
	public MNota findByNombreAndTitulo(String nombre, String titulo) {
		return new MNota(notaRepository.findByNombreAndTitulo(nombre, titulo));
	}

	/**
	 * obtiene una lista de MNota por el titulo
	 * 
	 * @return
	 */
	public List<MNota> findByTitulo(String titulo) {
		return convertidor.convertirLista(notaRepository.findByTitulo(titulo));
	}

	/**
	 * obtiene una lista de MNota por el titulo y ver el metodo
	 * notaRepository.findAll(pageable).getContent() que retorna automaticamente una
	 * lista de objeto Entity Nota
	 * 
	 * @param pageable
	 * @return
	 */
	public List<MNota> obtenerPorPaginacion(Pageable pageable) {
		return convertidor.convertirLista(notaRepository.findAll(pageable).getContent());
	}
}
