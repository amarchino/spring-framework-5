package guru.springframework.sfgpetclinic.services.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialityRepository;
import guru.springframework.sfgpetclinic.services.SpecialityService;

@Service
@Profile("springdatajpa")
public class SpecialitySDJpaService extends BaseSDJpaService<Speciality, Long, SpecialityRepository> implements SpecialityService {
	
	public SpecialitySDJpaService(SpecialityRepository repository) {
		super(repository);
	}

}
