package dev.engripaye.secure_oauth2_third_party_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class SecureOauth2ThirdPartyIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SecureOauth2ThirdPartyIntegrationApplication.class);

		// Tell Spring Boot to look for application-secret.yaml
		app.setDefaultProperties(Map.of(
				"spring.config.name", "application-secret",
				"spring.config.location", "classpath:/"
		));

		app.run(args);
	}
}
