package org.spring.userservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(origins = "*")
public class WelcomeController {

	@RequestMapping("/")
	public String welcome() {
		log.info("Welcome to User Service");
		return "Welcome to User Service";
	}
}
