package guru.springframework.rest.controllers.v1;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.rest.api.v1.model.VendorDTO;
import guru.springframework.rest.services.ResourceNotFoundException;
import guru.springframework.rest.services.VendorService;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {
	
	private static final Long ID = 1L;
	private static final String NAME = "Franks Fresh Fruits from France Ltd.";
	@Mock private VendorService vendorService;
	@InjectMocks private VendorController vendorController;
	private	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.standaloneSetup(vendorController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	@Test
	public void getAllCategories() throws Exception {
		List<VendorDTO> categories = Arrays.asList(
			VendorDTO.builder().id(ID).name(NAME).build(),
			VendorDTO.builder().id(2L).name("Bob").build()
		);
		when(vendorService.getAllVendors()).thenReturn(categories);
		
		mockMvc.perform(get(VendorController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.vendors", Matchers.hasSize(2)));
	}
	@Test
	public void getVendorByName() throws Exception {
		VendorDTO vendorDTO = VendorDTO.builder().id(ID).name(NAME).build();
		when(vendorService.getVendorById(Mockito.anyLong())).thenReturn(vendorDTO);
		
		mockMvc.perform(get(VendorController.BASE_URL + "/" + ID).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", Matchers.equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/" + ID)));
	}
	@Test
	public void getVendorByNameNotFound() throws Exception {
		when(vendorService.getVendorById(Mockito.anyLong())).thenThrow(new ResourceNotFoundException());
		mockMvc.perform(get(VendorController.BASE_URL + "/" + ID).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
	}
	@Test
	public void createNewVendor() throws Exception {
		VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
		VendorDTO savedVendorDTO = VendorDTO.builder().id(ID).name(NAME).build();
		when(vendorService.createNewVendor(Mockito.any(VendorDTO.class))).thenReturn(savedVendorDTO);
		
		mockMvc.perform(
				post(VendorController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(vendorDTO))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(jsonPath("$.name", Matchers.equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/" + ID)));
	}
	@Test
	public void updateVendor() throws Exception {
		VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
		VendorDTO savedVendorDTO = VendorDTO.builder().id(ID).name(NAME).build();
		when(vendorService.saveVendorByDTO(Mockito.anyLong(), Mockito.any(VendorDTO.class))).thenReturn(savedVendorDTO);
		
		mockMvc.perform(
				put(VendorController.BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(vendorDTO))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(jsonPath("$.name", Matchers.equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/" + ID)));
	}
	@Test
	public void patchVendor() throws Exception {
		VendorDTO vendorDTO = VendorDTO.builder().name(NAME).build();
		VendorDTO returnVendorDTO = VendorDTO.builder().id(ID).name(NAME).build();
		when(vendorService.patchVendor(Mockito.anyLong(), Mockito.any(VendorDTO.class))).thenReturn(returnVendorDTO);
		
		mockMvc.perform(
				patch(VendorController.BASE_URL + "/" + ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(vendorDTO))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(jsonPath("$.name", Matchers.equalTo(NAME)))
			.andExpect(jsonPath("$.vendor_url", Matchers.equalTo(VendorController.BASE_URL + "/" + ID)));
	}

	@Test
	public void deleteVendor() throws Exception {
		mockMvc.perform(delete(VendorController.BASE_URL + "/" + ID))
			.andExpect(status().isOk());
		verify(vendorService, times(1)).deleteVendorById(Mockito.anyLong());
	}
}
