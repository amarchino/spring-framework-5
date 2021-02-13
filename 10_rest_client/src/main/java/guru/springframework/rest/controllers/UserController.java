package guru.springframework.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;

import guru.springframework.rest.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

	private final ApiService apiService;

	@GetMapping({ "", "/", "/index" })
	public String index() {
		return "index";
	}

	@PostMapping("/users")
	public String formPost(Model model, ServerWebExchange serverWebExchange) {
		model.addAttribute("users", apiService.getUsers(serverWebExchange.getFormData().map(data -> {
			String limitInput = data.getFirst("limit");
			log.debug("Received Limit value: " + limitInput);
			Integer limit;
			try {
				limit = Integer.valueOf(limitInput);
			} catch (NumberFormatException e) {
				limit = 0;
			}

			// default if zero
			if (limit == 0) {
				log.debug("Setting limit to default of 10");
				limit = 10;
			}
			return limit;
		})));
		return "userlist";
//		
//		
//		return serverWebExchange.getFormData()
//			.map(map -> {
//				Integer limit = Integer.valueOf(map.get("limit").get(0));
//				log.debug("Received limit value: " + limit);
//				if(limit == null || limit <= 0) {
//					log.debug("Setting limit to default of 10");
//					limit = 10;
//				}
//				model.addAttribute("users", apiService.getUsers(limit));
//				return "userlist";
//			});
	}
}
