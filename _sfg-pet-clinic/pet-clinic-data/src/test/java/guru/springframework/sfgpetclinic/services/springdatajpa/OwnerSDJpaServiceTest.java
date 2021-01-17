package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {
	private static final String LAST_NAME = "Smith";
	private static final Long ID = 1L;

	private Owner returnOwner;
	@InjectMocks private OwnerSDJpaService service;
	@Mock private OwnerRepository ownerRepository;


	@BeforeEach
	void setUp() throws Exception {
		returnOwner = Owner.builder().lastName(LAST_NAME).id(ID).build();
	}

	@Test
	void findByLastName() {
		when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);
		Owner owner = service.findByLastName(LAST_NAME);
		assertNotNull(owner);
		assertEquals(LAST_NAME, owner.getLastName());
		assertEquals(1L, owner.getId());
		
		verify(ownerRepository, times(1)).findByLastName(anyString());
	}
	
	@Test
	void findAllByLastNameLike() {
		when(ownerRepository.findAllByLastNameContainsIgnoreCase(any())).thenReturn(Arrays.asList(returnOwner));
		List<Owner> owners = service.findAllByLastNameLike(LAST_NAME);
		assertNotNull(owners);
		assertEquals(1, owners.size());
		Owner owner = owners.get(0);
		assertEquals(LAST_NAME, owner.getLastName());
		assertEquals(1L, owner.getId());
		
		verify(ownerRepository, times(1)).findAllByLastNameContainsIgnoreCase(anyString());
	}

	@Test
	void findAll() {
		when(ownerRepository.findAll()).thenReturn(
			Set.of(
				Owner.builder().id(1L).build(),
				Owner.builder().id(2L).build()
			)
		);
		Set<Owner> owners = service.findAll();
		assertNotNull(owners);
		assertEquals(2, owners.size());
	}

	@Test
	void findById() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
		Owner owner = service.findById(ID);
		assertNotNull(owner);
		assertEquals(ID, owner.getId());
	}
	
	@Test
	void findByIdNotFound() {
		when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
		Owner owner = service.findById(ID);
		assertNull(owner);
	}

	@Test
	void save() {
		Owner owner = Owner.builder().build();
		when(ownerRepository.save(owner)).thenReturn(returnOwner);
		Owner saved = service.save(owner);
		assertNotNull(saved);
		assertEquals(ID, saved.getId());
	}

	@Test
	void delete() {
		service.delete(returnOwner);
		verify(ownerRepository, times(1)).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(ID);
		verify(ownerRepository, times(1)).deleteById(anyLong());
	}

}
