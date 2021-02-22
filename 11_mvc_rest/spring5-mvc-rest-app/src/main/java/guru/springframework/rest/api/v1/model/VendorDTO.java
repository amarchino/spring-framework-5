package guru.springframework.rest.api.v1.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import guru.springframework.rest.controllers.v1.VendorController;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

	@Schema(description = "Identifier of the vendor")
	private Long id;
	@NotNull
	@NotEmpty
	@Schema(description = "Name of the vendor")
	private String name;
	
	@Schema(description = "URL of the vendor")
	@JsonProperty("vendor_url")
	public String getVendorUrl() {
		return VendorController.BASE_URL + "/" + this.id;
	}
}
