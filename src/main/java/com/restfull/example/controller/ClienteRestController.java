package com.restfull.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfull.example.entity.Usuario;
import com.restfull.example.model.MNota;

@Controller
@RequestMapping("/nota")
public class ClienteRestController {

	public static final Log LOGGER = LogFactory.getLog(ClienteRestController.class);

	public static final String TEMPLATE = "template";
	public static final String AUTHORIZATION = "Authorization";

	@GetMapping("")
	public RedirectView redirect() {
		return new RedirectView("/nota/all");
	}

	@GetMapping("/all")
	public ModelAndView devolvertodos() {

		ModelAndView mav = new ModelAndView("template");

		RestTemplate rest = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();

		headers.add(AUTHORIZATION, getToken());

		HttpEntity entity = new HttpEntity(headers);

		ResponseEntity<MNota[]> notasEntity = rest.exchange("http://localhost:8090/v1/notas", HttpMethod.GET, entity,
				MNota[].class);

		MNota[] notas = notasEntity.getBody();

		mav.addObject("notas", notas);
		return mav;
	}

	/**
	 * desde la pagina de Login se obtiene el token para realizar la consulta
	 * @return
	 */
	public String getToken() {

		String token = "";
		try {
			RestTemplate rest = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// El objeto Usuario
			com.restfull.example.configuration.Usuario user = new com.restfull.example.configuration.Usuario();
			user.setUsuario("usuario2");
			user.setContrasena("123456");
			ObjectMapper objectMapper = new ObjectMapper();

			String userAsString = null;

			userAsString = objectMapper.writeValueAsString(user);

			HttpEntity entity = new HttpEntity<String>(userAsString, headers);
			
			// se realiza la consulta a la pagina de login para obtener el login
			ResponseEntity<Usuario[]> notasEntity = rest.exchange("http://localhost:8090/login", HttpMethod.POST,
					entity, Usuario[].class);

			if (notasEntity.getStatusCode().is2xxSuccessful()) {
				HttpHeaders responseHeaders = notasEntity.getHeaders();
				for (Entry<String, List<String>> iterHeaders : responseHeaders.entrySet()) {
					if (AUTHORIZATION.equalsIgnoreCase(iterHeaders.getKey())) {
						token = iterHeaders.getValue().get(0);
					}
					LOGGER.info(iterHeaders.getKey().concat(":").concat(iterHeaders.getValue().toString()));
				}
			} else {
				LOGGER.error("-- Mensaje de ERROR: " + notasEntity);
			}
		} catch (JsonProcessingException e) {
			LOGGER.error("-- Mensaje de ERROR: " + e.getMessage());
		}

		return token;
	}

}
