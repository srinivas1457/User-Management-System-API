package com.jsp.ums.exceptionhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApplicationExceptionHandller extends ResponseEntityExceptionHandler {

	// Utility method to create a standardized response structure
	private ResponseEntity<Object> structure(HttpStatus status, String message, Object rootCause) {
		return new ResponseEntity<Object>(Map.of("Status", status.value(), "message", message, "rootCause", rootCause),
				status);
	}

	// Extract field errors and their corresponding error messages
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<ObjectError> allErrors = ex.getAllErrors();
		Map<String, String> errors = new HashMap<>();

		// Extract field errors and their corresponding error messages
		allErrors.forEach(error -> {
			FieldError fieldError = (FieldError) error;
			errors.put(fieldError.getField(), error.getDefaultMessage());
		});
		return structure(HttpStatus.BAD_REQUEST, "Failed To Save The Data", errors);
	}

	// Custom handling for DataNotPresentException
	@ExceptionHandler(DataNotPresentException.class)
	public ResponseEntity<Object> handllerDataNotPresent(DataNotPresentException ex) {
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "User data Not Present");
	}

	// Custom handling for UserNotFoundByIdException
	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<Object> handllerUserNotFoundById(UserNotFoundByIdException ex) {
		return structure(HttpStatus.NOT_FOUND, ex.getMessage(), "User With Given Id Not Present");
	}

}
