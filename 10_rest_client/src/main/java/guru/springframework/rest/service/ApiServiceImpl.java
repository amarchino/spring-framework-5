package guru.springframework.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import guru.springframework.api.domain.User;
import guru.springframework.api.domain.UserData;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

	private final RestTemplate restTemplate;
	@Value("${api.url}") private final String apiUrl;
	
	@Override
	public List<User> getUsers(Integer limit) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
				.fromUriString(apiUrl)
				.queryParam("limit", limit);
		UserData userData = restTemplate.getForObject(uriBuilder.toUriString(), UserData.class);
		return userData.getData();
	}

	@Override
	public Flux<User> getUsers(Mono<Integer> limit) {
		return limit.flatMap(value -> WebClient
			.create(apiUrl)
			.get()
			.uri(uriBuilder -> uriBuilder.queryParam("limit", limit).build())
			.accept(MediaType.APPLICATION_JSON)
			.exchangeToMono(res -> res.bodyToMono(UserData.class))
		).flatMapIterable(UserData::getData);
	}

}
