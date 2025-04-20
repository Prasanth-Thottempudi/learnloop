package com.learnloop.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learnloop.response.ErrorResponse;
import com.learnloop.response.ValidationErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateStudentException.class)
	public ResponseEntity<String> handleDuplicateStudentException(DuplicateStudentException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
	    String rootMessage = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();

	    if (rootMessage != null && rootMessage.contains("Cannot delete or update a parent row")) {
	        if (rootMessage.contains("student_subjects")) {
	            return new ResponseEntity<>("This subject is already assigned to one or more students and cannot be deleted.", HttpStatus.CONFLICT);
	        } else if (rootMessage.contains("teacher_subjects")) {
	            return new ResponseEntity<>("This subject is already assigned to one or more teachers and cannot be deleted.", HttpStatus.CONFLICT);
	        } else {
	            return new ResponseEntity<>("This subject is already assigned and cannot be deleted due to related records.", HttpStatus.CONFLICT);
	        }
	    }

	    return new ResponseEntity<>("Data integrity violation: " + rootMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		ValidationErrorResponse response = new ValidationErrorResponse(LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(), "Validation Failed", request.getRequestURI(), errors);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		Map<String, String> errors = ex.getConstraintViolations().stream().collect(Collectors
				.toMap(violation -> violation.getPropertyPath().toString(), violation -> violation.getMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			HttpServletRequest request) {

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setError(ex.getMessage());
		errorResponse.setPath(request.getRequestURI());

		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
}
