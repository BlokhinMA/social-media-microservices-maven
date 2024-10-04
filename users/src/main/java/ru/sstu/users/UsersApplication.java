package ru.sstu.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import ru.sstu.users.models.User;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	@Bean
	public User user() {
		return new User();
	}

	@Bean
	public String accessToken() {
		return "";
	}

	@Bean
	public String refreshToken() {
		return "";
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().forEach(converter -> {
			if (converter instanceof AbstractHttpMessageConverter) {
				((AbstractHttpMessageConverter<?>) converter).setDefaultCharset(StandardCharsets.UTF_8);
			}
		});
		return restTemplate;
	}

}
