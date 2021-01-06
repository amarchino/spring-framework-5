package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;

@Service
@Profile("springdatajpa")
public class OwnerServiceSDJpa extends BaseServiceSDJpa<Owner, Long, OwnerRepository> implements OwnerService {
	
	public OwnerServiceSDJpa(OwnerRepository ownerRepository) {
		super(ownerRepository);
	}

	@Override
	public Owner findByLastName(String lastName) {
		return repository.findByLastName(lastName);
	}

}
