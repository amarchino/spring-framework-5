package guru.springframework.rest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import guru.springframework.rest.controllers.v1.VendorController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

	private Long id;
	private String name;
	
	@JsonProperty("vendor_url")
	public String getVendorUrl() {
		return VendorController.BASE_URL + "/" + this.id;
	}
}
