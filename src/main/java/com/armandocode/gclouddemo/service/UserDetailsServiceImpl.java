package com.armandocode.gclouddemo.service;

import com.armandocode.gclouddemo.user.UserRepository;
import com.armandocode.gclouddemo.user.Userapp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("inside UserDetailsServiceImpl loadUserByUsername");
		Userapp userapp = userRepository.findByUsername(username).orElseThrow(()->
		new UsernameNotFoundException("No user found"+username));
		System.out.println("userapp username: "+userapp.getUsername());
		return new org.springframework.security.core.userdetails.User(userapp.getUsername(),userapp.getPassword(),
				true, true, true, true, getAuthorities("ROLE_USER"));
		
	}
	

	private Collection<? extends GrantedAuthority> getAuthorities(String role_user) {
		
		return Collections.singletonList(new SimpleGrantedAuthority(role_user));
	}
	
	
	


}
