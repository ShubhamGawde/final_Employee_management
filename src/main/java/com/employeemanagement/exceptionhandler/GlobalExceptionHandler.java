package com.employeemanagement.exceptionhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employeemanagement.model.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		// Build a list of error messages
		List<String> errors = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			errors.add(fieldError.getDefaultMessage());
		}

		Response response = new Response(false, "Validation Failed", 400, "", errors, null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Response> handleNoSuchElementException(NoSuchElementException ex) {

		Response response = new Response(false, "user id not found", 400, "", List.of(ex.getMessage()), null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	

}
