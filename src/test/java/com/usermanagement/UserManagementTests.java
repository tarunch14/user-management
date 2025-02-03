package com.usermanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.usermanagement.Payload.EmployeeDto;
import com.usermanagement.config.BindJson;
import com.usermanagement.config.JsonLoaderConfig;

import lombok.SneakyThrows;


@SpringBootTest(classes = {UsermanagementApplication.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UserManagementTests extends JsonLoaderConfig {

	private @BindJson("user-data.json") EmployeeDto userData;

	@Test
	@Order(0)
	@SneakyThrows
	void createUserTest() {
		ResponseEntity<EmployeeDto> userEntity = restTemplate.exchange(baseUrl + "/submit", HttpMethod.POST,
				new HttpEntity<>(userData, requestHeaders), EmployeeDto.class);
		assertEquals(HttpStatus.CREATED, userEntity.getStatusCode());
	}

	@Test
	@Order(1)
	@SneakyThrows
	void getUserTest() {
		ResponseEntity<EmployeeDto> userEntity = restTemplate.exchange(baseUrl + "/view/1", HttpMethod.GET,
				new HttpEntity<>(requestHeaders), EmployeeDto.class);
		assertEquals(HttpStatus.OK, userEntity.getStatusCode());
		userData = userEntity.getBody();
	}

	@Test
	@Order(2)
	@SneakyThrows
	void updateUserTest() {
		userData.setName("mahesh");
		ResponseEntity<EmployeeDto> userEntity2 = restTemplate.exchange(baseUrl + "/update/1", HttpMethod.PUT,
				new HttpEntity<>(userData, requestHeaders), EmployeeDto.class);
		assertEquals(HttpStatus.OK, userEntity2.getStatusCode());
	}

	@Test
	@Order(3)
	@SneakyThrows
	void deleteUserTest() {
		ResponseEntity<String> userEntity = restTemplate.exchange(baseUrl + "/delete/1", HttpMethod.DELETE,
				new HttpEntity<>(requestHeaders), String.class);
		assertEquals(HttpStatus.NO_CONTENT, userEntity.getStatusCode());
	}

}
