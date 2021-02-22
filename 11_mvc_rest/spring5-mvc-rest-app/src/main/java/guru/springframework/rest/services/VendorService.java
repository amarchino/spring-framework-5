package guru.springframework.rest.services;

import java.util.List;

import guru.springframework.rest.api.v1.model.VendorDTO;

public interface VendorService {

	List<VendorDTO> getAllVendors();
	VendorDTO getVendorById(Long id);
	VendorDTO createNewVendor(VendorDTO customerDTO);
	VendorDTO saveVendorByDTO(Long id, VendorDTO customerDTO);
	VendorDTO patchVendor(Long id, VendorDTO customerDTO);
	void deleteVendorById(Long id);
}
