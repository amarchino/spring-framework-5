package guru.springframework.rest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.springframework.api.domain.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiServiceImplTest {
	
	@Autowired private ApiService apiService;

	@Test
	void getUsers() {
		Integer size = 1;
		List<User> users = apiService.getUsers(size);
		assertEquals(size, users.size());
	}

}
