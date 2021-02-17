package guru.springframework.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import guru.springframework.rest.controllers.v1.CustomerController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Long id;
	private String firstname;
	private String lastname;
	
	@JsonProperty("customer_url")
	public String getCustomerUrl() {
		return CustomerController.BASE_URL + "/" + this.id;
	}
}
