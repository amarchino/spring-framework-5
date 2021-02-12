package guru.springframework.api.domain;

import lombok.Data;

@Data
public class Login {

	private String username;
	private String password;
	private String md5;
	private String sha1;
	private String sha256;
}
