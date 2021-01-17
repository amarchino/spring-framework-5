package guru.springframework.sfgpetclinic.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
	
	@Mock private PetService petService;
	@Mock private VisitService visitService;
	@InjectMocks private VisitController visitController;
	private MockMvc mockMvc;
	private Pet pet;

	@BeforeEach
	void setUp() throws Exception {
		pet = Pet.builder().id(2L).owner(Owner.builder().id(1L).build()).build();
		mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
	}

	@Test
	void initCreationForm() throws Exception {
		when(petService.findById(Mockito.anyLong())).thenReturn(pet);
		
		mockMvc.perform(get("/owners/1/pets/2/visits/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("visit"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}
	
	@Test
	void processCreationForm() throws Exception {
		when(petService.findById(Mockito.anyLong())).thenReturn(pet);
		when(visitService.save(Mockito.any(Visit.class))).thenReturn(Visit.builder().id(1L).build());
		
		mockMvc.perform(post("/owners/1/pets/2/visits/new"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));
	}
	
}
