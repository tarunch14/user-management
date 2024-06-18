package com.usermanagement.config;

import java.io.InputStream;
import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class JsonLoaderConfig {

	@LocalServerPort
	private int port;

	protected HttpHeaders requestHeaders;
	protected String baseUrl;
	protected @Autowired TestRestTemplate restTemplate;
	protected @Autowired ObjectMapper mapper;

	@BeforeAll
	@SneakyThrows
	void loadJson() {
		baseUrl = "http://localhost:" + port + "/user";

		Field[] fieldsToBindJson = FieldUtils.getFieldsWithAnnotation(this.getClass(), BindJson.class);
		for (var field : fieldsToBindJson) {
			InputStream stream = JsonLoaderConfig.class.getClassLoader()
					.getResourceAsStream(field.getAnnotation(BindJson.class).value());
			Object object = mapper.readValue(stream, field.getType());
			FieldUtils.writeField(field, this, object, true);
		}
	}
	
}
