package guru.springframework.sfgpetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
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
		savePetType("Dog");
		savePetType("Cat");
		System.out.println("Loaded pet types...");

		saveOwner("Michael", "Weston");
		saveOwner("Fiona", "Glenanne");
		System.out.println("Loaded Owners...");
		
		saveVet("Sam", "Axe");
		saveVet("Jessie", "Porter");
		System.out.println("Loaded vets...");
	}

	private Owner saveOwner(String firstName, String lastName) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
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

}
