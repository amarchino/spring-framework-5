package guru.springframework.sfgpetclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "visits")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Visit extends BaseEntity {

	private static final long serialVersionUID = -875930165598690383L;

	@Column(name = "date")
	private LocalDate date;
	@Column(name = "description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

}
