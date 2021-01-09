package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;

class VisitMapServiceTest {
	
	private VisitService service;
	private final Long visitId = Long.valueOf(1);
	private final Long petId = Long.valueOf(1);
	private final Long ownerId = Long.valueOf(1);

	@BeforeEach
	void setUp() throws Exception {
		service = new VisitMapService();
		service.save(Visit.builder().id(visitId).pet(Pet.builder().id(petId).owner(Owner.builder().id(ownerId).build()).build()).build());
	}

	@Test
	void saveNoPet() {
		Visit visit = Visit.builder().build();
		assertThrows(RuntimeException.class, () -> service.save(visit));
	}
	@Test
	void saveNoOwner() {
		Visit visit = Visit.builder().pet(Pet.builder().id(petId).build()).build();
		assertThrows(RuntimeException.class, () -> service.save(visit));
	}
	@Test
	void saveExistingId() {
		Long id = 2L;
		Visit visit = Visit.builder().pet(Pet.builder().id(petId).owner(Owner.builder().id(ownerId).build()).build()).id(id).build();
		service.save(visit);
		assertEquals(id, visit.getId());
	}
	@Test
	void saveNoId() {
		Visit visit = Visit.builder().pet(Pet.builder().id(petId).owner(Owner.builder().id(ownerId).build()).build()).build();
		service.save(visit);
		assertNotNull(visit);
		assertNotNull(visit.getId());
		assertEquals(2L, visit.getId());
	}
	@Test
	void findAll() {
		Set<Visit> visits = service.findAll();
		assertEquals(1, visits.size());
	}
	@Test
	void findById() {
		Visit visit = service.findById(visitId);
		assertNotNull(visit);
		assertEquals(visitId, visit.getId());
	}
	@Test
	void deleteById() {
		service.deleteById(visitId);
		assertEquals(0L, service.findAll().size());
	}
	@Test
	void delete() {
		service.delete(service.findById(visitId));
		assertEquals(0L, service.findAll().size());
	}

}
