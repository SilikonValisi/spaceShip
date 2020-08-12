package com.game8.client;

import com.game8.client.controller.ControllerUtility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientApplicationTests {
	private ControllerUtility controllerUtility = new ControllerUtility();
	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void loginTest(){
		String userApiAddress = controllerUtility.getUserApiAddress();
		HttpEntity<String> entity = ControllerUtility.createUserEntity("clientTest", "clientTest");
		ResponseEntity<String> response = restTemplate.exchange(
				userApiAddress + "/login",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<>() {});

		System.out.println(response.getBody());
		assert response.getBody().equals("Auth");
	}

	@Test
	public void invalidLoginTest(){
		String userApiAddress = controllerUtility.getUserApiAddress();
		HttpEntity<String> entity = ControllerUtility.createUserEntity("clientTestNot", "clientTestNot");
		ResponseEntity<String> response = restTemplate.exchange(
				userApiAddress + "/login",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<>() {});

		assert !response.getBody().equals("Auth");
	}

	@Test
	public void registerTest(){
		String username = "ClientTest" + Math.random()*(100);
		String password = "password";
		HttpEntity<String> entity = ControllerUtility.createUserEntity(username, password);
		String userApiAddress = controllerUtility.getUserApiAddress();
		ResponseEntity<String> response = restTemplate.exchange(
				userApiAddress + "/createUser",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<>() {});
		assert response.getBody().equals("RegisterSuccess");
	}

	@Test
	public void invalidRegisterTest(){
		String username = "ClientTest";
		String password = "client";
		HttpEntity<String> entity = ControllerUtility.createUserEntity(username, password);
		String userApiAddress = controllerUtility.getUserApiAddress();
		ResponseEntity<String> response = restTemplate.exchange(
				userApiAddress + "/createUser",
				HttpMethod.POST,
				entity,
				new ParameterizedTypeReference<>() {});
		assert !response.getBody().equals("RegisterSuccess");
	}

}
