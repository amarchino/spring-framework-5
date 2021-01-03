package guru.springframework.sfgpetclinic.services.map;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
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
			object.getPets().forEach(pet -> {
				Pet savedPet = petService.save(pet);
				pet.setId(savedPet.getId());
			});
		}
		return super.save(object);
	}

}
