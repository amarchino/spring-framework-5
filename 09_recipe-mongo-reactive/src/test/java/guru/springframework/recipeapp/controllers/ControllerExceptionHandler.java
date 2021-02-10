package guru.springframework.recipeapp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WebExchangeBindException.class)
	public String handleNotFound(WebExchangeBindException e, Model model) {
		log.error("Handling web exchange bind exception: " + e.getMessage());
		model.addAttribute("exception", e);
		return "400error";
	}
}
