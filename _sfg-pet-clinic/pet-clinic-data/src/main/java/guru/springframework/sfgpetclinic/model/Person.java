package guru.springframework.sfgpetclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {

	private static final long serialVersionUID = 6996174504746063233L;

	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	
}
