package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialityRepository;
import guru.springframework.sfgpetclinic.services.SpecialityService;

@Service
@Profile("springdatajpa")
public class SpecialityServiceSDJpa extends BaseServiceSDJpa<Speciality, Long, SpecialityRepository> implements SpecialityService {
	
	public SpecialityServiceSDJpa(SpecialityRepository repository) {
		super(repository);
	}

}
