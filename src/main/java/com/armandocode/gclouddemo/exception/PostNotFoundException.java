package com.armandocode.gclouddemo.exception;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String errorMessage) {
        super(errorMessage);
    }

}
