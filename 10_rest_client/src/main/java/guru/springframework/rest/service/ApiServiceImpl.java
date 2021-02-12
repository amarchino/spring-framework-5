package guru.springframework.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

	private final RestTemplate restTemplate;

	@Override
	public List<User> getUsers(Integer limit) {
		UserData userData = restTemplate.getForObject("http://private-anon-2da50522dc-apifaketory.apiary-mock.com/api/user?limit=" + limit, UserData.class);
		return userData.getData();
	}

}
