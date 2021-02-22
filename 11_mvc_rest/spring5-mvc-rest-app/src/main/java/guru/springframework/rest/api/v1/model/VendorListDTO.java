package guru.springframework.rest.api.v1.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendorListDTO {

	@Schema(description = "Vendor list")
	private List<VendorDTO> vendors;
}
