package com.armandocode.gclouddemo.controller;

import com.armandocode.gclouddemo.dto.AuthenticationResponse;

import com.armandocode.gclouddemo.dto.LoginRequest;
import com.armandocode.gclouddemo.dto.RegisterRequest;
import com.armandocode.gclouddemo.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody RegisterRequest registerRequest) {
		System.out.println("signup method inside AuthController");
		System.out.println(registerRequest);
		authService.signup(registerRequest);
		System.out.println("inside authcontroller");
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
		
	}
	
	
	

}
