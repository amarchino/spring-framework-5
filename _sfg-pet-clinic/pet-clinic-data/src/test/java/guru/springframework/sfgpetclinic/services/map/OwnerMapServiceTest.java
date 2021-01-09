package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

class OwnerMapServiceTest {
	
	private OwnerService service;
	private final Long ownerId = Long.valueOf(1);
	private final String lastName = "Smith";

	@BeforeEach
	void setUp() throws Exception {
		service = new OwnerMapService(new PetMapService(new PetTypeMapService()));
		service.save(Owner.builder().id(ownerId).lastName(lastName).build());
	}

	@Test
	void findByLastNameExisting() {
		Owner owner = service.findByLastName(lastName);
		assertNotNull(owner);
		assertEquals(lastName, owner.getLastName());
		assertEquals(ownerId, owner.getId());
	}
	@Test
	void findByLastNameExistingCaseInsensitive() {
		Owner owner = service.findByLastName(lastName.toUpperCase());
		assertNotNull(owner);
		assertEquals(lastName, owner.getLastName());
		assertEquals(ownerId, owner.getId());
	}
	@Test
	void findByLastNameNotFound() {
		Owner owner = service.findByLastName("foo");
		assertNull(owner);
	}
	@Test
	void saveExistingId() {
		Long id = 2L;
		Owner owner = Owner.builder().id(id).build();
		service.save(owner);
		assertEquals(id, owner.getId());
	}
	@Test
	void saveNoId() {
		Owner owner = Owner.builder().build();
		service.save(owner);
		assertNotNull(owner);
		assertNotNull(owner.getId());
		assertEquals(2L, owner.getId());
	}
	@Test
	void findAll() {
		Set<Owner> owners = service.findAll();
		assertEquals(1, owners.size());
	}
	@Test
	void findById() {
		Owner owner = service.findById(ownerId);
		assertNotNull(owner);
		assertEquals(ownerId, owner.getId());
	}
	@Test
	void deleteById() {
		service.deleteById(ownerId);
		assertEquals(0L, service.findAll().size());
	}
	@Test
	void delete() {
		service.delete(service.findById(ownerId));
		assertEquals(0L, service.findAll().size());
	}

}
