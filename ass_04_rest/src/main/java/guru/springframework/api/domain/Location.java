package guru.springframework.api.domain;

import lombok.Data;

@Data
public class Location {

	private String street;
	private String city;
	private String state;
	private String postcode;
}
