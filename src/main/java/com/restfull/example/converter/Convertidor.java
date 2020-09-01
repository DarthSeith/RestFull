package com.restfull.example.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.restfull.example.entity.Nota;
import com.restfull.example.model.MNota;

@Component("convertidor")
public class Convertidor {

	public List<MNota> convertirLista(List<Nota> notas) {

		List<MNota> mNotas = new ArrayList<>();
		for (Nota nota : notas) {
			mNotas.add(new MNota(nota));
		}
		
		return mNotas;
	}

}
