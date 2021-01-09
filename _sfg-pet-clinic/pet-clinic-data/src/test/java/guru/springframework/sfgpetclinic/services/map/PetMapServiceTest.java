package guru.springframework.sfgpetclinic.services.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetService;

class PetMapServiceTest {
	
	private PetService service;
	private final Long petId = Long.valueOf(1);

	@BeforeEach
	void setUp() throws Exception {
		service = new PetMapService(new PetTypeMapService());
		service.save(Pet.builder().petType(PetType.builder().build()).id(petId).build());
	}
	@Test
	void savePetMissingPetType() {
		Pet pet = Pet.builder().build();
		assertThrows(RuntimeException.class, () -> service.save(pet));
	}

	@Test
	void savePetExistingId() {
		Long id = 2L;
		Pet pet = Pet.builder().petType(PetType.builder().build()).id(id).build();
		service.save(pet);
		assertEquals(id, pet.getId());
	}
	@Test
	void savePetNoId() {
		Pet pet = Pet.builder().petType(PetType.builder().build()).build();
		service.save(pet);
		assertNotNull(pet);
		assertNotNull(pet.getId());
		assertEquals(2L, pet.getId());
	}

	@Test
	void findAll() {
		Set<Pet> pets = service.findAll();
		assertEquals(1, pets.size());
	}

	@Test
	void findById() {
		Pet pet = service.findById(petId);
		assertNotNull(pet);
		assertEquals(petId, pet.getId());
	}

	@Test
	void deleteById() {
		service.deleteById(petId);
		assertEquals(0L, service.findAll().size());
	}

	@Test
	void delete() {
		service.delete(service.findById(petId));
		assertEquals(0L, service.findAll().size());
	}

}
