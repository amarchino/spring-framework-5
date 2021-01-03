package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

public class Vet extends Person {

	private static final long serialVersionUID = 5719734850419870704L;
	private Set<Speciality> specialities = new HashSet<>();
	public Set<Speciality> getSpecialities() {
		return specialities;
	}
	public void setSpecialities(Set<Speciality> specialties) {
		this.specialities = specialties;
	}
}
