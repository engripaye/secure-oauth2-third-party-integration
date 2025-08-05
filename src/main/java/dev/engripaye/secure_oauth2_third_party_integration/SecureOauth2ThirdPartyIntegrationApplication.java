package dev.engripaye.secure_oauth2_third_party_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map; // ✅ Correct import

@SpringBootApplication
public class SecureOauth2ThirdPartyIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SecureOauth2ThirdPartyIntegrationApplication.class);

		app.setDefaultProperties(Map.of(
				"spring.config.name", "application-secret",  // Looks for application-secret.yaml
				"spring.config.location", "classpath:/"
		));

		app.run(args);
	}
}
