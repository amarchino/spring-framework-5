package guru.springframework.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	private String gender;
	private Name name;
	private Location location;
	private String email;
	private Login login;
	private String phone;
	private Job job;
	private Billing billing;
	private String language;
	private String currency;

}
