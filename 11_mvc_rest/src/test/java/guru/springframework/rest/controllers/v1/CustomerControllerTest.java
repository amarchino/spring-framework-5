package guru.springframework.rest.controllers.v1;

import static org.mockito.Mockito.when;
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

import guru.springframework.rest.api.v1.model.CustomerDTO;
import guru.springframework.rest.services.CustomerService;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
	
	private static final Long ID = 1L;
	private static final String FIRSTNAME = "Jim";
	private static final String LASTNAME = "Roger";
	@Mock private CustomerService customerService;
	@InjectMocks private CustomerController customerController;
	private	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	public void getAllCategories() throws Exception {
		List<CustomerDTO> categories = Arrays.asList(
			CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build(),
			CustomerDTO.builder().id(2L).firstname("Bob").lastname("Edwards").build()
		);
		when(customerService.getAllCustomers()).thenReturn(categories);
		
		mockMvc.perform(get("/api/v1/customers").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.customers", Matchers.hasSize(2)));
	}
	@Test
	public void getCustomerByName() throws Exception {
		CustomerDTO customerDTO = CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		when(customerService.getCustomerById(Mockito.anyLong())).thenReturn(customerDTO);
		
		mockMvc.perform(get("/api/v1/customers/" + ID).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstname", Matchers.equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", Matchers.equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", Matchers.equalTo("/shop/customers/" + ID)));
	}
	@Test
	public void createNewCustomer() throws Exception {
		CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).lastname(LASTNAME).build();
		CustomerDTO savedCustomerDTO = CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		when(customerService.createNewCustomer(Mockito.any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
		
		mockMvc.perform(
				post("/api/v1/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(customerDTO))
			)
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(jsonPath("$.firstname", Matchers.equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", Matchers.equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", Matchers.equalTo("/shop/customers/" + ID)));
	}
	@Test
	public void updateCustomer() throws Exception {
		CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).lastname(LASTNAME).build();
		CustomerDTO savedCustomerDTO = CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		when(customerService.saveCustomerByDTO(Mockito.anyLong(), Mockito.any(CustomerDTO.class))).thenReturn(savedCustomerDTO);
		
		mockMvc.perform(
				put("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(customerDTO))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(jsonPath("$.firstname", Matchers.equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", Matchers.equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", Matchers.equalTo("/shop/customers/" + ID)));
	}
	@Test
	public void patchCustomer() throws Exception {
		CustomerDTO customerDTO = CustomerDTO.builder().firstname(FIRSTNAME).build();
		CustomerDTO returnCustomerDTO = CustomerDTO.builder().id(ID).firstname(FIRSTNAME).lastname(LASTNAME).build();
		when(customerService.patchCustomer(Mockito.anyLong(), Mockito.any(CustomerDTO.class))).thenReturn(returnCustomerDTO);
		
		mockMvc.perform(
				patch("/api/v1/customers/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(customerDTO))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
			.andExpect(jsonPath("$.firstname", Matchers.equalTo(FIRSTNAME)))
			.andExpect(jsonPath("$.lastname", Matchers.equalTo(LASTNAME)))
			.andExpect(jsonPath("$.customer_url", Matchers.equalTo("/shop/customers/" + ID)));
	}

}
