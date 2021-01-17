package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "pets")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Pet extends BaseEntity {

	private static final long serialVersionUID = 9052231982640691480L;

	@Column(name = "birth_date")
	private LocalDate birthDate;
	@Column(name = "name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "type_id")
	private PetType petType;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Owner owner;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
	@Builder.Default
	private Set<Visit> visits = new HashSet<>();

	public void addVisit(Visit visit) {
		visit.setPet(this);
		if(visit.isNew()) {
			visits.add(visit);
		}
	}
	
}
