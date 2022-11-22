package com.armandocode.gclouddemo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

	// private Key key;
	private KeyStore keyStore;
	// private String key =
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtProvider.class);

	@PostConstruct
	public void init() {
		// key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "secret".toCharArray());
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			throw new SpringBlogException("Exception occurred while loading keyStore");
		}

		LOGGER.info("init key:" + keyStore);
	}

	public String generateToken(Authentication authentication) {
		LOGGER.info("generateToken function");
		User principal = (User) authentication.getPrincipal();
		LOGGER.info(
				"generated token jwt: " + Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact());
		// Jwts.builder().setSubject(principal.getUsername()).signWith(key.getBytes()).compact()
		return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
	}
 
	public PrivateKey getPrivateKey() {

		try {
			PrivateKey asd = (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
			return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new SpringBlogException("Exception has ocurred  while retrieving private key from keystore");
		}

	}

	public boolean validateToken(String jwt) {
		LOGGER.debug("jwt token received: " + jwt);
		LOGGER.debug("validating token", Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt));
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);

		return true;
	}

	private PublicKey getPublicKey() {
		try {

			return keyStore.getCertificate("springblog").getPublicKey();

		} catch (KeyStoreException e) {
			throw new SpringBlogException("Exception has ocurred  while retrieving public key from keystore");
		}

	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();

		return claims.getSubject();

	}
}
