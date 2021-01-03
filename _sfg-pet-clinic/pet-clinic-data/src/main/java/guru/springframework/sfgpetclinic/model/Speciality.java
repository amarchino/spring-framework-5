package guru.springframework.sfgpetclinic.model;

public class Speciality extends BaseEntity {

	private static final long serialVersionUID = -6769572462102821198L;

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
