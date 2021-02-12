package guru.springframework.rest.service;

import java.util.List;

import guru.springframework.api.domain.User;

public interface ApiService {

	List<User> getUsers(Integer limit);
}
