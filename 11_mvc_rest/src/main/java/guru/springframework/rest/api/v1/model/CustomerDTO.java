package guru.springframework.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import guru.springframework.rest.controllers.v1.CustomerController;
import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(description = "This is the first name", required = true)
	private String firstname;
	@Schema(description = "This is the last name")
	private String lastname;
	
	@JsonProperty("customer_url")
	public String getCustomerUrl() {
		return CustomerController.BASE_URL + "/" + this.id;
	}
}
