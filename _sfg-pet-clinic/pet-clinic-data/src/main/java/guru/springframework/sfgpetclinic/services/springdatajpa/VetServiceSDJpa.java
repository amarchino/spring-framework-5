package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.services.VetService;

@Service
@Profile("springdatajpa")
public class VetServiceSDJpa extends BaseServiceSDJpa<Vet, Long, VetRepository> implements VetService {
	
	public VetServiceSDJpa(VetRepository repository) {
		super(repository);
	}

}
