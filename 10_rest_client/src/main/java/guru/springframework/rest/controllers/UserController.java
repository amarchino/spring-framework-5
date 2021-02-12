package guru.springframework.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

import guru.springframework.rest.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final ApiService apiService;
	
	@GetMapping({"", "/", "/index"})
	public String index() {
		return "index";
	}

	@PostMapping("/users")
	public Mono<String> formPost(Model model, ServerWebExchange serverWebExchange) {
		return serverWebExchange.getFormData()
			.map(map -> {
				Integer limit = Integer.valueOf(map.get("limit").get(0));
				log.debug("Received limit value: " + limit);
				if(limit == null || limit <= 0) {
					log.debug("Setting limit to default of 10");
					limit = 10;
				}
				model.addAttribute("users", apiService.getUsers(limit));
				return "userlist";
			});
	}
}
