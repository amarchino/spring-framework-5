package guru.springframework.sfgpetclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;

@Component
public class DataLoader implements CommandLineRunner {
	
	private final OwnerService ownerService;
	private final VetService vetService;

	public DataLoader(OwnerService ownerService, VetService vetService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
	}

	@Override
	public void run(String... args) throws Exception {
		saveOwner("Michael", "Weston");
		saveOwner("Fiona", "Glenanne");
		System.out.println("Loaded Owners...");
		saveVet("Sam", "Axe");
		saveVet("Jessie", "Porter");
		System.out.println("Loaded vets...");
	}

	private void saveOwner(String firstName, String lastName) {
		Owner owner = new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		ownerService.save(owner);
	}
	private void saveVet(String firstName, String lastName) {
		Vet vet = new Vet();
		vet.setFirstName(firstName);
		vet.setLastName(lastName);
		vetService.save(vet);
	}

}
