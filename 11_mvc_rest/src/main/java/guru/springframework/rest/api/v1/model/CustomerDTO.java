package guru.springframework.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

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
		return "/shop/customers/" + this.id;
	}
}
