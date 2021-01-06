package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.services.PetService;

@Service
@Profile("springdatajpa")
public class PetServiceSDJpa extends BaseServiceSDJpa<Pet, Long, PetRepository> implements PetService {
	
	public PetServiceSDJpa(PetRepository repository) {
		super(repository);
	}

}
