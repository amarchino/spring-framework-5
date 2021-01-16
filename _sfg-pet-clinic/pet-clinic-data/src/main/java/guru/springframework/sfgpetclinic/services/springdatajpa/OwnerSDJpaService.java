package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.services.OwnerService;

@Service
@Profile("springdatajpa")
public class OwnerSDJpaService extends BaseSDJpaService<Owner, Long, OwnerRepository> implements OwnerService {
	
	public OwnerSDJpaService(OwnerRepository repository) {
		super(repository);
	}

	@Override
	public Owner findByLastName(String lastName) {
		return repository.findByLastName(lastName);
	}

	@Override
	public List<Owner> findAllByLastNameLike(String lastName) {
		return repository.findAllByLastNameLike(lastName);
	}

}
