package guru.springframework.sfgpetclinic.services.map;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;

@Service
public class PetMapService extends AbstractMapService<Pet> implements PetService {
	
	private final PetTypeService petTypeService;
	
	public PetMapService(PetTypeService petTypeService) {
		this.petTypeService = petTypeService;
	}
	
	@Override
	public Pet save(Pet object) {
		if(object == null) {
			return super.save(object);
		}
		if(object.getPetType() == null) {
			throw new RuntimeException("Pet Type is required");
		}
		object.setPetType(petTypeService.save(object.getPetType()));
		return super.save(object);
	}
}
