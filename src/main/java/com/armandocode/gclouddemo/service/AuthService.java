package com.armandocode.gclouddemo.service;

import com.armandocode.gclouddemo.dto.AuthenticationResponse;
import com.armandocode.gclouddemo.dto.LoginRequest;
import com.armandocode.gclouddemo.dto.RegisterRequest;
import com.armandocode.gclouddemo.security.JwtProvider;
import com.armandocode.gclouddemo.user.UserRepository;
import com.armandocode.gclouddemo.user.Userapp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.datastore.core.DatastoreTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	DatastoreTemplate datastoreTemplate;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	public ResponseEntity<?> signup(RegisterRequest registerRequest) {
		LOGGER.info("AuthService signup method before persisting the new user");
		Userapp user = new Userapp();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(registerRequest.getPassword());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		this.datastoreTemplate.save(user);
		LOGGER.info("AuthService signup method after persisting the new user");
		return new ResponseEntity<Object>(HttpStatus.OK);
		
	}

	private String encodePassword(String password) {
	
		return passwordEncoder.encode(password);
	}

	public AuthenticationResponse login(LoginRequest loginRequest) {
		System.out.println("login method in AuthService, username: "+loginRequest.getUsername()+" password: "+loginRequest.getPassword());
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), 
				loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String authenticationToken = jwtProvider.generateToken(authenticate);
		
		System.out.println(authenticate.isAuthenticated());
		System.out.println("************************ ");
		LOGGER.info("authenticationToken: "+authenticationToken);
		LOGGER.info("loginRequest: "+loginRequest.getUsername());
		AuthenticationResponse authenticationResponse = new AuthenticationResponse (authenticationToken, loginRequest.getUsername());
		return authenticationResponse;
	}

	public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
	
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return Optional.of(principal);
	}

}
