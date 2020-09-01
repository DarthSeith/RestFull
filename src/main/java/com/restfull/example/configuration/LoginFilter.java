package com.restfull.example.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

	protected LoginFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		// TODO Auto-generated constructor stub
	}

	public LoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException, IOException, ServletException {

		// obtenemos el body de la peticion que asumimos viene en formato JSON
		InputStream body = req.getInputStream();

		// Asumimos que el body tendrá el siguiente JSON
		// {"username":"ask", "contrasena":"123"}
		// Realizamos un mapeo a nuestra clase User para tener ahi los datos
		Usuario user = new ObjectMapper().readValue(body, Usuario.class);

		// Finalmente autenticamos
		// Spring comparará el user/password recibidos
		// contra el que definimos en la clase SecurityConfig
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsuario(),
				user.getContrasena(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		// Si la autenticacion fue exitosa, agregamos el token a la respuesta
		JwtUtil.addAuthentication(res, auth.getName());
	}

}