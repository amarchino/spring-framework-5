package guru.springframework.sfgpetclinic.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
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
import guru.springframework.sfgpetclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
	
	@InjectMocks private OwnerController ownerController;
	@Mock private OwnerService ownerService;
	
	private Set<Owner> owners;
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		owners = Set.of(
			Owner.builder().id(1L).build(),
			Owner.builder().id(2L).build()
		);
		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}

	@Test
	void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/findOwners"))
			.andExpect(model().attributeExists("owner"));
		verifyNoInteractions(ownerService);
	}
	
	@Test
	void processFindOwnersReturnMany() throws Exception {
		when(ownerService.findAllByLastNameLike(Mockito.anyString())).thenReturn(new ArrayList<>(owners));
		
		mockMvc.perform(get("/owners").param("lastName", ""))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownersList"))
			.andExpect(model().attribute("selections", Matchers.hasSize(2)));
	}
	@Test
	void processFindOwnersReturnOne() throws Exception {
		when(ownerService.findAllByLastNameLike(Mockito.anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build()));
		
		mockMvc.perform(get("/owners"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"));
	}
	@Test
	void processFindOwnersReturnZero() throws Exception {
		when(ownerService.findAllByLastNameLike(Mockito.anyString())).thenReturn(List.of());
		
		mockMvc.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/findOwners"));
	}
	@Test
	void showOwner() throws Exception {
		when(ownerService.findById(Mockito.anyLong())).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(get("/owners/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownerDetails"))
			.andExpect(model().attribute("owner", Matchers.hasProperty("id", Matchers.is(1L))));
	}
	
	@Test
	void initCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"))
			.andExpect(model().attributeExists("owner"));
		verifyNoInteractions(ownerService);
	}
	@Test
	void processCreationForm() throws Exception {
		when(ownerService.save(Mockito.any(Owner.class))).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(post("/owners/new"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"))
			.andExpect(model().attributeExists("owner"));
		verify(ownerService, times(1)).save(Mockito.any(Owner.class));
	}

	@Test
	void initUpdateOwnerForm() throws Exception {
		when(ownerService.findById(Mockito.anyLong())).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(get("/owners/1/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"))
			.andExpect(model().attributeExists("owner"));
		verify(ownerService, times(1)).findById(Mockito.anyLong());
	}
	@Test
	void processUpdateOwnerForm() throws Exception {
		when(ownerService.save(Mockito.any())).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(post("/owners/1/edit"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/1"))
			.andExpect(model().attributeExists("owner"));
		verify(ownerService, times(1)).save(Mockito.any());
	}
}
