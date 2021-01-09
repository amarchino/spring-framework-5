package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.services.SpecialityService;

class SpecialityMapServiceTest {
	
	private SpecialityService service;
	private final Long specialityId = Long.valueOf(1);

	@BeforeEach
	void setUp() throws Exception {
		service = new SpecialityMapService();
		service.save(Speciality.builder().id(specialityId).build());
	}

	@Test
	void saveSpecialityExistingId() {
		Long id = 2L;
		Speciality speciality = Speciality.builder().id(id).build();
		service.save(speciality);
		assertEquals(id, speciality.getId());
	}
	@Test
	void saveSpecialityNoId() {
		Speciality speciality = Speciality.builder().build();
		service.save(speciality);
		assertNotNull(speciality);
		assertNotNull(speciality.getId());
		assertEquals(2L, speciality.getId());
	}

	@Test
	void findAll() {
		Set<Speciality> specialities = service.findAll();
		assertEquals(1, specialities.size());
	}

	@Test
	void findById() {
		Speciality speciality = service.findById(specialityId);
		assertNotNull(speciality);
		assertEquals(specialityId, speciality.getId());
	}

	@Test
	void deleteById() {
		service.deleteById(specialityId);
		assertEquals(0L, service.findAll().size());
	}

	@Test
	void delete() {
		service.delete(service.findById(specialityId));
		assertEquals(0L, service.findAll().size());
	}

}
