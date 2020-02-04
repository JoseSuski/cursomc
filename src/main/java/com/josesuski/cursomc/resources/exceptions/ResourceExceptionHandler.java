package com.josesuski.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.josesuski.cursomc.services.exceptions.ObjectNoFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNoFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNoFoundException e, HttpServletRequest request){
	
	StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage() , System.currentTimeMillis());
	
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	
	}	
		
}
