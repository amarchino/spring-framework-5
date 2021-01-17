package guru.springframework.sfgpetclinic.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Set;

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
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {
	
	@Mock private PetService petService;
	@Mock private OwnerService ownerService;
	@Mock private PetTypeService petTypeService;
	@InjectMocks private PetController petController;
	private MockMvc mockMvc;
	private Owner owner;
	private Set<PetType> petTypes;

	@BeforeEach
	void setUp() throws Exception {
		owner = Owner.builder().id(1L).build();
		petTypes = Set.of(
			PetType.builder().id(1L).name("Dog").build(),
			PetType.builder().id(2L).name("Cat").build()
		);
		mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
	}

	@Test
	void initCreationForm() throws Exception {
		when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		when(petTypeService.findAll()).thenReturn(petTypes);
		
		mockMvc.perform(get("/owners/1/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}
	
	@Test
	void processCreationForm() throws Exception {
		when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		when(petTypeService.findAll()).thenReturn(petTypes);
		
		mockMvc.perform(post("/owners/1/pets/new"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));
	}
	
	@Test
	void initUpdateForm() throws Exception {
		when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		when(petTypeService.findAll()).thenReturn(petTypes);
		when(petService.findById(Mockito.anyLong())).thenReturn(Pet.builder().id(2L).build());
		
		mockMvc.perform(get("/owners/1/pets/2/edit"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void processUpdateForm() throws Exception {
		when(ownerService.findById(Mockito.anyLong())).thenReturn(owner);
		when(petTypeService.findAll()).thenReturn(petTypes);
		
		mockMvc.perform(post("/owners/1/pets/2/edit"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));
	}
}
