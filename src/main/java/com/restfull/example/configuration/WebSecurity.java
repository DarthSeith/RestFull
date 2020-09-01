package com.restfull.example.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.restfull.example.service.UsuarioService;

/**
 * WebSecurityConfigurerAdapter es una configuracion de spring Para realizar la
 * prueba con el metodo POST en el PostMan http://localhost:8090/login y poner
 * en el body { "usuario":"usuario2", "contrasena":"123456" } (ojo que el
 * usuario tiene que estar en la BD con el campo activo en true) para recuperar
 * el token de tipo Bearer en la Authorization: eysadsa.... y despues poner ese
 * token en la consulta que se va a realizar
 * 
 * 
 * @author gigio
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;

	/**
	 * aca se extrae el usuario para validar el token
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService);
//		auth.userDetailsService(usuarioService).passwordEncoder(NoOpPasswordEncoder.getInstance()); // ingresamos el usuario y encriptamos
		// la contraseña

//		// Creamos una cuenta de usuario por default        
//        auth.  inMemoryAuthentication()
//                .withUser("ask")
//                .password("123")
//                .roles("USER").and()
//                .withUser("admin").password("{noop}password").roles("ADMIN");
	}

	/**
	 * http nos permite crear querys, la cual se le dice que desactive la
	 * Authentication (que no necesita autorizacion en la /login
	 * autmaticamente),nosotros programamos para que que sea una Authentication
	 * personalizada
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests() // desactiva el login por defecto
				.antMatchers("/login").permitAll() // permitimos el acceso a /login a cualquiera
				.anyRequest().authenticated() // qualquiera otra peticion requiere autenticacion
				.and()
				// Las peticiones /login pasaran previamente por este filtro
				.addFilterBefore(new LoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// Las demas paticiones pasaran por este filtro para validar token
				.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
