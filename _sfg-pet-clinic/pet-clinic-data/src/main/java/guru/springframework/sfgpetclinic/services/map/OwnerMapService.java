package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
@Profile({ "default", "map" })
public class OwnerMapService extends AbstractMapService<Owner> implements OwnerService {
	
	private final PetService petService;

	public OwnerMapService(PetTypeService petTypeService, PetService petService) {
		this.petService = petService;
	}

	@Override
	public Owner findByLastName(String lastName) {
		return null;
	}
	
	@Override
	public Owner save(Owner object) {
		if(object == null) {
			return super.save(object);
		}
		if(object.getPets() != null) {
			object.getPets().forEach(petService::save);
		}
		return super.save(object);
	}

}
