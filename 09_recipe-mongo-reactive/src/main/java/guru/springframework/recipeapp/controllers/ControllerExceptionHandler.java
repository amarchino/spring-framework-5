package guru.springframework.recipeapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

//@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(NumberFormatException.class)
//	public ModelAndView handleNotFound(NumberFormatException e) {
//		log.error("Handling number format exception: " + e.getMessage());
//		ModelAndView mav = new ModelAndView("400error", Map.of("exception", e));
//		return mav;
//	}
}
