package guru.springframework.rest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.rest.api.v1.model.VendorDTO;
import guru.springframework.rest.api.v1.model.VendorListDTO;
import guru.springframework.rest.services.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(description = "This is my vendor controller", name = "VendorController")
@RestController
@RequestMapping(name = VendorController.BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";
	private final VendorService vendorService;
	
	@Operation(summary = "This will get a list of vendors.")
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public VendorListDTO getAllVendors() {
		return new VendorListDTO(vendorService.getAllVendors());
	}

	@Operation(summary = "This will get a vendor by its id.")
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable("id") Long id) {
		return vendorService.getVendorById(id);
	}
	
	@Operation(summary = "This will create a vendor.")
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return vendorService.createNewVendor(vendorDTO);
	}
	
	@Operation(summary = "This will fully update a vendor.")
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.saveVendorByDTO(id, vendorDTO);
	}
	
	@Operation(summary = "This will partially update a vendor.")
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.patchVendor(id, vendorDTO);
	}
	
	@Operation(summary = "This will delete a vendor.")
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteVendor(@PathVariable("id") Long id) {
		vendorService.deleteVendorById(id);
	}
}
