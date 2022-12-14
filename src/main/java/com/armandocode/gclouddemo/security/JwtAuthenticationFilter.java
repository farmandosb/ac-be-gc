package com.armandocode.gclouddemo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override 	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String jwt = getJwtFromRequest(request);
		LOGGER.info("doFilterInternal jwt: "+jwt);
		
		if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {

			String username = jwtProvider.getUsernameFromJWT(jwt);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(
							userDetails,
							null, 
							userDetails.getAuthorities() 
							);

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
	
		String bearerToken = request.getHeader("Authorization");
		LOGGER.info("getJwtFromRequest: "+bearerToken);
		

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			
			LOGGER.debug("getJwtFromRequest conditional");
			LOGGER.debug("token substring: "+bearerToken.substring(7));
			
			return bearerToken.substring(7);
			
		}

		return bearerToken;

	}

}
