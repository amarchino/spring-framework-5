package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.SpecialityService;
import guru.springframework.sfgpetclinic.services.VetService;

@Service
@Profile({ "default", "map" })
public class VetMapService extends AbstractMapService<Vet> implements VetService {
	
	private final SpecialityService specialityService;
	
	public VetMapService(SpecialityService specialityService) {
		this.specialityService = specialityService;
	}

	@Override
	public Vet save(Vet object) {
		if(object == null) {
			return super.save(object);
		}
		if(object.getSpecialities() != null) {
			object.getSpecialities().forEach(specialityService::save);
		}
		return super.save(object);
	}
}
