package guru.springframework.sfgpetclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;

@Component
public class DataLoader implements CommandLineRunner {
	
	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;

	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
	}

	@Override
	public void run(String... args) throws Exception {
		PetType dog = savePetType("Dog");
		PetType cat = savePetType("Cat");
		System.out.println("Loaded Pet Types...");
		
		saveOwner("Michael", "Weston", "123 Brickerel", "Miami", "123456789", initPet("Rosco", dog, LocalDate.now()));
		saveOwner("Fiona", "Glenanne", "123 Brickerel", "Miami", "123456789", initPet("Just Cat", cat, LocalDate.now()));
		System.out.println("Loaded Owners...");
		
		saveVet("Sam", "Axe");
		saveVet("Jessie", "Porter");
		System.out.println("Loaded vets...");
	}

	private Owner saveOwner(String firstName, String lastName, String address, String city, String telephone, Pet... pets) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setAddress(address);
		owner.setCity(city);
		owner.setTelephone(telephone);
		for(Pet pet : pets) {
			pet.setOwner(owner);
			owner.getPets().add(pet);
		}
		return ownerService.save(owner);
	}
	private Vet saveVet(String firstName, String lastName) {
		Vet vet = new Vet();
		vet.setFirstName(firstName);
		vet.setLastName(lastName);
		return vetService.save(vet);
	}
	private PetType savePetType(String name) {
		PetType petType = new PetType();
		petType.setName(name);
		return petTypeService.save(petType);
	}
	private Pet initPet(String name, PetType petType, LocalDate birthDate) {
		Pet pet = new Pet();
		pet.setName(name);
		pet.setBirthDate(birthDate);
		pet.setPetType(petType);
		return pet;
	}

}
