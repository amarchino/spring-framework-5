package guru.springframework.rest.controllers.v1;

import org.springframework.http.HttpStatus;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(VendorController.BASE_URL)
@RequiredArgsConstructor
public class VendorController {
	public static final String BASE_URL = "/api/v1/vendors";
	private final VendorService vendorService;
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	public VendorListDTO getAllVendors() {
		return new VendorListDTO(vendorService.getAllVendors());
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable("id") Long id) {
		return vendorService.getVendorById(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return vendorService.createNewVendor(vendorDTO);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.saveVendorByDTO(id, vendorDTO);
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable("id") Long id, @RequestBody VendorDTO vendorDTO) {
		return vendorService.patchVendor(id, vendorDTO);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void deleteVendor(@PathVariable("id") Long id) {
		vendorService.deleteVendorById(id);
	}
}
