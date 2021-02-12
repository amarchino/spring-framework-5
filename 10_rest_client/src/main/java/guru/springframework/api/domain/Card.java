package guru.springframework.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Card {

	private String type;
	private String number;
	@JsonProperty(value = "expiration_date")
	private DateType expirationDate;
}
