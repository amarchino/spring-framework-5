package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
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
	void findAllByLastNameLike() {
		List<Owner> owners = service.findAllByLastNameLike(lastName);
		assertNotNull(owners);
		assertEquals(1, owners.size());
		Owner owner = owners.get(0);
		assertEquals(lastName, owner.getLastName());
		assertEquals(1L, owner.getId());
	}
	@Test
	void findAllByLastNameLikeEmpty() {
		service.save(Owner.builder().id(2L).lastName("Marian").build());
		service.save(Owner.builder().id(3L).lastName("Edgar").build());

		List<Owner> owners = service.findAllByLastNameLike("");
		assertNotNull(owners);
		assertEquals(3, owners.size());
	}
	@Test
	void findAllByLastNameLikeMultiple() {
		service.save(Owner.builder().id(2L).lastName("Marian").build());
		service.save(Owner.builder().id(3L).lastName("Edgar").build());

		List<Owner> owners = service.findAllByLastNameLike("aR");
		assertNotNull(owners);
		assertEquals(2, owners.size());
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
	void saveWithPet() {
		Owner owner = Owner.builder().pets(Set.of(
			Pet.builder().petType(PetType.builder().name("Dog").build()).build(),
			Pet.builder().petType(PetType.builder().name("Cat").build()).build()
		)).build();
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
