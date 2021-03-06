package guru.springframework.sfgpetclinic.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.VisitService;

@Component
public class DataLoader implements CommandLineRunner {
	
	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;
	private final SpecialityService specialityService;
	private final VisitService visitService;

	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
		this.specialityService = specialityService;
		this.visitService = visitService;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		if(petTypeService.findAll().isEmpty()) {
			loadData();
		}
	}

	private void loadData() {
		PetType dog = savePetType("Dog");
		PetType cat = savePetType("Cat");
		System.out.println("Loaded Pet Types...");
		
		Speciality radiology = saveSpeciality("Radiology");
		Speciality surgery = saveSpeciality("Surgery");
		Speciality dentistry = saveSpeciality("Dentistry");
		System.out.println("Loaded Specialities...");
		
		Pet justCat = initPet("Just Cat", cat, LocalDate.now());
		saveOwner("Michael", "Weston", "123 Brickerel", "Miami", "123456789", initPet("Rosco", dog, LocalDate.now()));
		saveOwner("Fiona", "Glenanne", "123 Brickerel", "Miami", "123456789", justCat);
		System.out.println("Loaded Owners...");
		
		saveVisit("Sneezy Kitty", justCat);
		System.out.println("Loaded Visits...");
		
		saveVet("Sam", "Axe", radiology, surgery);
		saveVet("Jessie", "Porter", dentistry);
		saveVet("Max", "Johnson");
		System.out.println("Loaded Vets...");
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
	private Vet saveVet(String firstName, String lastName, Speciality... specialities) {
		Vet vet = new Vet();
		vet.setFirstName(firstName);
		vet.setLastName(lastName);
		for(Speciality speciality : specialities) {
			vet.getSpecialities().add(speciality);
		}
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
	private Speciality saveSpeciality(String name) {
		Speciality speciality = new Speciality();
		speciality.setDescription(name);
		return specialityService.save(speciality);
	}
	private Visit saveVisit(String description, Pet pet) {
		Visit visit = new Visit();
		visit.setDate(LocalDate.now());
		visit.setDescription(description);
		visit.setPet(pet);
		return visitService.save(visit);
	}

}
