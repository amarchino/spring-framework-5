package guru.springframework.rest;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestTemplateExamples {
	
	public static final String API_ROOT = "https://api.predic8.de:443/shop";
	private RestTemplate restTemplate;
	
	@BeforeEach
	private void setUp() {
		restTemplate = new RestTemplate();
	}
	
	@Test
	public void getCategories() {
		String apiUrl = API_ROOT + "/categories/";
		JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
		log.info("Response: " + jsonNode.toString());
	}
	@Test
	public void getCustomers() {
		String apiUrl = API_ROOT + "/customers/";
		JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
		log.info("Response: " + jsonNode.toString());
	}
	@Test
	public void createCustomer() {
		String apiUrl = API_ROOT + "/customers/";
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("firstname", "Joe");
		postMap.put("lastname", "Buck");
		JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
		log.info("Response: " + jsonNode.toString());
	}
	@Test
	public void updateCustomer() {
		String apiUrl = API_ROOT + "/customers/";
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("firstname", "Michael");
		postMap.put("lastname", "Weston");
		JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
		
		String customerUrl = jsonNode.get("customer_url").textValue();
		String id = customerUrl.split("/")[3];
		log.info("Created customer id: " + id);
		
		postMap.put("firstname", "Michael 2");
		postMap.put("lastname", "Weston 2");
		
		restTemplate.put(apiUrl + id, postMap);
		
		JsonNode updatedNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);
		log.info("Response: " + updatedNode.toString());
	}
	@Test
	public void updateCustomerUsingPatchSunHttp() {
		String apiUrl = API_ROOT + "/customers/";
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("firstname", "Sam");
		postMap.put("lastname", "Axe");
		JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
		
		String customerUrl = jsonNode.get("customer_url").textValue();
		String id = customerUrl.split("/")[3];
		log.info("Created customer id: " + id);
		
		postMap.put("firstname", "Michael 2");
		postMap.put("lastname", "Weston 2");
		
		assertThrows(ResourceAccessException.class, () -> restTemplate.patchForObject(apiUrl + id, postMap,JsonNode.class));
	}
	@Test
	public void updateCustomerUsingPatch() {
		String apiUrl = API_ROOT + "/customers/";
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		restTemplate = new RestTemplate(requestFactory);
		
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("firstname", "Sam");
		postMap.put("lastname", "Axe");
		JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
		
		String customerUrl = jsonNode.get("customer_url").textValue();
		String id = customerUrl.split("/")[3];
		log.info("Created customer id: " + id);
		
		postMap.put("firstname", "Michael 2");
		postMap.put("lastname", "Weston 2");
		
		JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, postMap,JsonNode.class);
		log.info("Response: " + updatedNode.toString());
	}
	@Test
	public void deleteCustomer() {
		String apiUrl = API_ROOT + "/customers/";
		
		Map<String, Object> postMap = new HashMap<>();
		postMap.put("firstname", "Sam");
		postMap.put("lastname", "Axe");
		JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);
		
		String customerUrl = jsonNode.get("customer_url").textValue();
		String id = customerUrl.split("/")[3];
		log.info("Created customer id: " + id);
		
		restTemplate.delete(apiUrl + id);
		
		assertThrows(HttpClientErrorException.class, () -> restTemplate.getForObject(apiUrl + id, JsonNode.class));
	}

}
