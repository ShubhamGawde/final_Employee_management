package com.employeemanagement.exceptionhandler;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.employeemanagement.Response.ErrorResponse;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		BindingResult bindingResult = exception.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();

		// Build a list of error messages
		List<String> errors = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			errors.add(fieldError.getDefaultMessage());
		}

		var response = new ErrorResponse(false, 400, exception.getMessage(), request.getRequestURI(),
				request.getMethod(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(UserException.class)
	@ResponseBody
	public ResponseEntity<?> handleUserException(UserException exception, HttpServletRequest request) {

		var response = new ErrorResponse(exception.isSuccess(), exception.getStatus(), exception.getMessage(),
				request.getRequestURI(), request.getMethod(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> handleIOException(IOException exception, HttpServletRequest req) {

		var response = new ErrorResponse(false, 500, exception.getMessage(), req.getRequestURI(), req.getMethod(),
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<?> handleMessagingException(MessagingException exception, HttpServletRequest req) {

		var response = new ErrorResponse(false, 500, exception.getMessage(), req.getRequestURI(), req.getMethod(),
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(UnsupportedEncodingException.class)
	public ResponseEntity<?> handleUnsupportedEncodingException(UnsupportedEncodingException exception,
			HttpServletRequest req) {

		var response = new ErrorResponse(false, 500, exception.getMessage(), req.getRequestURI(), req.getMethod(),
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(CustomeException.class)
	public ResponseEntity<?> CustomeExceptionException(CustomeException exception, HttpServletRequest req) {

		var response = new ErrorResponse(false, exception.getStatus(), exception.getMessage(), req.getRequestURI(),
				req.getMethod(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseBody
	public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception,
			HttpServletRequest request) {

		var response = new ErrorResponse(false, 400, "invalid password", request.getRequestURI(), request.getMethod(),
				LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException exception,
			HttpServletRequest req) {

		var response = new ErrorResponse(false, 400, exception.getMessage(), req.getRequestURI(), req.getMethod(),
				LocalDateTime.now());

		return  ResponseEntity.ok(response);
	}

	@ExceptionHandler(AuthenticationExceptions.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationExceptions(AuthenticationExceptions exception,
			HttpServletRequest req) {

		ErrorResponse response = new ErrorResponse(false, 400, exception.getMessage(), req.getRequestURI(),
				req.getMethod(), LocalDateTime.now());

		return ResponseEntity.ok(response);
	}

}
