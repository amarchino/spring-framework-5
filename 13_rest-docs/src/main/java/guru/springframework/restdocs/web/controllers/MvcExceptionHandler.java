package guru.springframework.restdocs.web.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MvcExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<String> validationErrorHandler(ConstraintViolationException ex) {
		return ex.getConstraintViolations()
			.stream()
			.map(error -> error.toString())
			.collect(Collectors.toList());
	}
}
