package com.restfull.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restfull.example.entity.Usuario;
import com.restfull.example.repository.GestorUsuario;

@Service("usuarioService")
public class UsuarioService implements UserDetailsService {

	@Autowired // vamos a inyectar un bean
	@Qualifier("gestorUsuario") // cual es el bean que vamos a utilizar
	private GestorUsuario gestorUsuario;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//para que encripte el password
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		Usuario usuario = gestorUsuario.findByUsuario(username);
		return new User(usuario.getUsuario(),encoder.encode(usuario.getContrasena()), usuario.isActivo(), usuario.isActivo(),
				usuario.isActivo(), usuario.isActivo(), buildGrante(usuario.getRol()));
	}

	public List<GrantedAuthority> buildGrante(byte rol) {
		String[] roles = { "LECTOR", "USUARIO", "ADMINISTRADOR" };
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		for (int i = 0; i < rol; i++) {
			auths.add(new SimpleGrantedAuthority(roles[i]));
		}
		return auths;
	}

}
