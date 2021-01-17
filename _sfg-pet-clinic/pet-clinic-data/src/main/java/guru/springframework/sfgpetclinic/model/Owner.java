package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "owners")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Owner extends Person {

	private static final long serialVersionUID = 6058308647075194395L;
	@Column(name = "address")
	private String address;
	@Column(name = "city")
	private String city;
	@Column(name = "telephone")
	private String telephone;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
	@Builder.Default
	private Set<Pet> pets = new HashSet<>();

	public Pet getPet(String name) {
		return getPet(name, false);
	}
	public Pet getPet(String name, boolean ignoreNew) {
		for(Pet pet : pets) {
			if(!ignoreNew || !pet.isNew()) {
				if(pet.getName().equalsIgnoreCase(name)) {
					return pet;
				}
			}
		}
		return null;
	}
	public void addPet(Pet pet) {
		pet.setOwner(this);
		if(pet.isNew()) {
			pets.add(pet);
		}
	}
}
