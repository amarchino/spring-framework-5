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

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {
	private static final Long ID = 1L;

	private Vet returnVet;
	@InjectMocks private VetSDJpaService service;
	@Mock private VetRepository vetRepository;


	@BeforeEach
	void setUp() throws Exception {
		returnVet = Vet.builder().id(ID).build();
	}

	@Test
	void findAll() {
		when(vetRepository.findAll()).thenReturn(
			Set.of(
				Vet.builder().id(1L).build(),
				Vet.builder().id(2L).build()
			)
		);
		Set<Vet> vets = service.findAll();
		assertNotNull(vets);
		assertEquals(2, vets.size());
	}

	@Test
	void findById() {
		when(vetRepository.findById(anyLong())).thenReturn(Optional.of(returnVet));
		Vet vet = service.findById(ID);
		assertNotNull(vet);
		assertEquals(ID, vet.getId());
	}
	
	@Test
	void findByIdNotFound() {
		when(vetRepository.findById(anyLong())).thenReturn(Optional.empty());
		Vet vet = service.findById(ID);
		assertNull(vet);
	}

	@Test
	void save() {
		Vet vet = Vet.builder().build();
		when(vetRepository.save(vet)).thenReturn(returnVet);
		Vet saved = service.save(vet);
		assertNotNull(saved);
		assertEquals(ID, saved.getId());
	}

	@Test
	void delete() {
		service.delete(returnVet);
		verify(vetRepository, times(1)).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(ID);
		verify(vetRepository, times(1)).deleteById(anyLong());
	}

}
