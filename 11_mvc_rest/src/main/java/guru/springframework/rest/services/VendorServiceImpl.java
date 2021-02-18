package guru.springframework.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import guru.springframework.rest.api.v1.mapper.VendorMapper;
import guru.springframework.rest.api.v1.model.VendorDTO;
import guru.springframework.rest.domain.Vendor;
import guru.springframework.rest.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
	
	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;

	@Override
	public List<VendorDTO> getAllVendors() {
		return vendorRepository
			.findAll()
			.stream()
			.map(vendorMapper::vendorToVendorDTO)
			.collect(Collectors.toList());
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id)
				.map(vendorMapper::vendorToVendorDTO)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {
		return saveAndReturnDTO(vendorMapper.vendorDTOToVendor(vendorDTO));
	}

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
		vendor.setId(id);
		return saveAndReturnDTO(vendor);
	}

	private VendorDTO saveAndReturnDTO(Vendor vendor) {
		vendor = vendorRepository.save(vendor);
		return vendorMapper.vendorToVendorDTO(vendor);
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
		return vendorRepository.findById(id)
			.map(vendor -> {
				if(vendorDTO.getName() != null) {
					vendor.setName(vendorDTO.getName());
				}
				return vendorRepository.save(vendor);
			})
			.map(vendorMapper::vendorToVendorDTO)
			.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {
		vendorRepository.deleteById(id);
	}
}
