package guru.springframework.api.domain;

import lombok.Data;

@Data
public class Billing {
	
	private Card card;
	private String iban;
	private String swift;
}
