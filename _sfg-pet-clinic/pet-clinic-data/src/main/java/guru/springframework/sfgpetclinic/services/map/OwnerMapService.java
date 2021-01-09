package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;

@Service
@Profile({ "default", "map" })
public class OwnerMapService extends AbstractMapService<Owner> implements OwnerService {
	
	private final PetService petService;

	public OwnerMapService(PetService petService) {
		this.petService = petService;
	}

	@Override
	public Owner findByLastName(String lastName) {
		return this.findAll()
				.stream()
				.filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
				.findFirst()
				.orElse(null);
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
