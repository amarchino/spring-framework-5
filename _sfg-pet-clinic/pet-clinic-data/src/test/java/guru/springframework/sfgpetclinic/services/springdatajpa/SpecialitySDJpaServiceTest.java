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

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialityRepository;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
	private static final Long ID = 1L;

	private Speciality returnSpeciality;
	@InjectMocks private SpecialitySDJpaService service;
	@Mock private SpecialityRepository specialityRepository;


	@BeforeEach
	void setUp() throws Exception {
		returnSpeciality = Speciality.builder().id(ID).build();
	}

	@Test
	void findAll() {
		when(specialityRepository.findAll()).thenReturn(
			Set.of(
				Speciality.builder().id(1L).build(),
				Speciality.builder().id(2L).build()
			)
		);
		Set<Speciality> specialitys = service.findAll();
		assertNotNull(specialitys);
		assertEquals(2, specialitys.size());
	}

	@Test
	void findById() {
		when(specialityRepository.findById(anyLong())).thenReturn(Optional.of(returnSpeciality));
		Speciality speciality = service.findById(ID);
		assertNotNull(speciality);
		assertEquals(ID, speciality.getId());
	}
	
	@Test
	void findByIdNotFound() {
		when(specialityRepository.findById(anyLong())).thenReturn(Optional.empty());
		Speciality speciality = service.findById(ID);
		assertNull(speciality);
	}

	@Test
	void save() {
		Speciality speciality = Speciality.builder().build();
		when(specialityRepository.save(speciality)).thenReturn(returnSpeciality);
		Speciality saved = service.save(speciality);
		assertNotNull(saved);
		assertEquals(ID, saved.getId());
	}

	@Test
	void delete() {
		service.delete(returnSpeciality);
		verify(specialityRepository, times(1)).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(ID);
		verify(specialityRepository, times(1)).deleteById(anyLong());
	}

}
