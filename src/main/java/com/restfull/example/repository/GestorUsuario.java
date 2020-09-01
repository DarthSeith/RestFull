package com.restfull.example.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfull.example.entity.Usuario;

@Repository("gestorUsuario")
public interface GestorUsuario extends JpaRepository<Usuario, Serializable> {

	public abstract Usuario findByUsuario(String usuario);

}
