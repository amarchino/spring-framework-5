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

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetSDJpaServiceTest {
	private static final Long ID = 1L;

	private Pet returnPet;
	@InjectMocks private PetSDJpaService service;
	@Mock private PetRepository petRepository;


	@BeforeEach
	void setUp() throws Exception {
		returnPet = Pet.builder().id(ID).build();
	}

	@Test
	void findAll() {
		when(petRepository.findAll()).thenReturn(
			Set.of(
				Pet.builder().id(1L).build(),
				Pet.builder().id(2L).build()
			)
		);
		Set<Pet> pets = service.findAll();
		assertNotNull(pets);
		assertEquals(2, pets.size());
	}

	@Test
	void findById() {
		when(petRepository.findById(anyLong())).thenReturn(Optional.of(returnPet));
		Pet pet = service.findById(ID);
		assertNotNull(pet);
		assertEquals(ID, pet.getId());
	}
	
	@Test
	void findByIdNotFound() {
		when(petRepository.findById(anyLong())).thenReturn(Optional.empty());
		Pet pet = service.findById(ID);
		assertNull(pet);
	}

	@Test
	void save() {
		Pet pet = Pet.builder().build();
		when(petRepository.save(pet)).thenReturn(returnPet);
		Pet saved = service.save(pet);
		assertNotNull(saved);
		assertEquals(ID, saved.getId());
	}

	@Test
	void delete() {
		service.delete(returnPet);
		verify(petRepository, times(1)).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(ID);
		verify(petRepository, times(1)).deleteById(anyLong());
	}

}
