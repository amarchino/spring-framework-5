package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;

class VetMapServiceTest {
	
	private VetService service;
	private final Long vetId = Long.valueOf(1);
	private final String lastName = "Smith";

	@BeforeEach
	void setUp() throws Exception {
		service = new VetMapService(new SpecialityMapService());
		service.save(Vet.builder().id(vetId).lastName(lastName).build());
	}

	@Test
	void saveExistingId() {
		Long id = 2L;
		Vet vet = Vet.builder().id(id).build();
		service.save(vet);
		assertEquals(id, vet.getId());
	}
	@Test
	void saveNoId() {
		Vet vet = Vet.builder().build();
		service.save(vet);
		assertNotNull(vet);
		assertNotNull(vet.getId());
		assertEquals(2L, vet.getId());
	}
	@Test
	void saveWithSpecialities() {
		Vet vet = Vet.builder().specialities(Set.of(
			Speciality.builder().build()
		)).build();
		service.save(vet);
		assertNotNull(vet);
		assertNotNull(vet.getId());
		assertEquals(2L, vet.getId());
		assertEquals(1, vet.getSpecialities().size());
	}
	@Test
	void findAll() {
		Set<Vet> vets = service.findAll();
		assertEquals(1, vets.size());
	}
	@Test
	void findById() {
		Vet vet = service.findById(vetId);
		assertNotNull(vet);
		assertEquals(vetId, vet.getId());
	}
	@Test
	void deleteById() {
		service.deleteById(vetId);
		assertEquals(0L, service.findAll().size());
	}
	@Test
	void delete() {
		service.delete(service.findById(vetId));
		assertEquals(0L, service.findAll().size());
	}

}
