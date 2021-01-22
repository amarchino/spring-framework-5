package guru.springframework.sfgpetclinic.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {
	
	@Mock private VetService vetService;
	@InjectMocks private VetController vetController;
	private MockMvc mockMvc;
	private Set<Vet> vets;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
		vets = Set.of(
			Vet.builder().id(1L).build(),
			Vet.builder().id(2L).build()
		);
	}

	@Test
	void listVets() throws Exception {
		when(vetService.findAll()).thenReturn(vets);
		
		mockMvc.perform(get("/vets"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("vets"))
			.andExpect(view().name("vets/index"));
	}
	
	@Test
	void getVetsJson() throws Exception {
		when(vetService.findAll()).thenReturn(vets);
		
		mockMvc.perform(get("/api/vets").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
}
