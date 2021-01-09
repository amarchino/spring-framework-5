package guru.springframework.sfgpetclinic.services.springdatajpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;

@ExtendWith(MockitoExtension.class)
class PetTypeSDJpaServiceTest {
	private static final Long ID = 1L;

	private PetType returnPetType;
	@InjectMocks private PetTypeSDJpaService service;
	@Mock private PetTypeRepository petTypeRepository;


	@BeforeEach
	void setUp() throws Exception {
		returnPetType = PetType.builder().id(ID).build();
	}

	@Test
	void findAll() {
		when(petTypeRepository.findAll()).thenReturn(
			Set.of(
				PetType.builder().id(1L).build(),
				PetType.builder().id(2L).build()
			)
		);
		Set<PetType> petTypes = service.findAll();
		assertNotNull(petTypes);
		assertEquals(2, petTypes.size());
	}

	@Test
	void findById() {
		when(petTypeRepository.findById(anyLong())).thenReturn(Optional.of(returnPetType));
		PetType petType = service.findById(ID);
		assertNotNull(petType);
		assertEquals(ID, petType.getId());
	}
	
	@Test
	void findByIdNotFound() {
		when(petTypeRepository.findById(anyLong())).thenReturn(Optional.empty());
		PetType petType = service.findById(ID);
		assertNull(petType);
	}

	@Test
	void save() {
		PetType petType = PetType.builder().build();
		when(petTypeRepository.save(petType)).thenReturn(returnPetType);
		PetType saved = service.save(petType);
		assertNotNull(saved);
		assertEquals(ID, saved.getId());
	}

	@Test
	void delete() {
		service.delete(returnPetType);
		verify(petTypeRepository, times(1)).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(ID);
		verify(petTypeRepository, times(1)).deleteById(anyLong());
	}

}
