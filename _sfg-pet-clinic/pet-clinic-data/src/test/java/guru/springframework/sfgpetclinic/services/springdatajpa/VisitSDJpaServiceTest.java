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

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
	private static final Long ID = 1L;

	private Visit returnVisit;
	@InjectMocks private VisitSDJpaService service;
	@Mock private VisitRepository visitRepository;


	@BeforeEach
	void setUp() throws Exception {
		returnVisit = Visit.builder().id(ID).build();
	}

	@Test
	void findAll() {
		when(visitRepository.findAll()).thenReturn(
			Set.of(
				Visit.builder().id(1L).build(),
				Visit.builder().id(2L).build()
			)
		);
		Set<Visit> visits = service.findAll();
		assertNotNull(visits);
		assertEquals(2, visits.size());
	}

	@Test
	void findById() {
		when(visitRepository.findById(anyLong())).thenReturn(Optional.of(returnVisit));
		Visit visit = service.findById(ID);
		assertNotNull(visit);
		assertEquals(ID, visit.getId());
	}
	
	@Test
	void findByIdNotFound() {
		when(visitRepository.findById(anyLong())).thenReturn(Optional.empty());
		Visit visit = service.findById(ID);
		assertNull(visit);
	}

	@Test
	void save() {
		Visit visit = Visit.builder().build();
		when(visitRepository.save(visit)).thenReturn(returnVisit);
		Visit saved = service.save(visit);
		assertNotNull(saved);
		assertEquals(ID, saved.getId());
	}

	@Test
	void delete() {
		service.delete(returnVisit);
		verify(visitRepository, times(1)).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(ID);
		verify(visitRepository, times(1)).deleteById(anyLong());
	}

}
