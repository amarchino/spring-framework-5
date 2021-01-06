package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
@Profile("springdatajpa")
public class PetTypeServiceSDJpa extends BaseServiceSDJpa<PetType, Long, PetTypeRepository> implements PetTypeService {
	
	public PetTypeServiceSDJpa(PetTypeRepository repository) {
		super(repository);
	}

}
