package dev.engripaye.secure_oauth2_third_party_integration.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")  // 👈 points to our custom login page
                        .defaultSuccessUrl("/integration/success", true)
                .failureHandler((request, response, exception) -> {
                    exception.printStackTrace(); // Log the root cause
                    response.sendRedirect("/login?error=" + exception.getMessage()); // Optional
                }));
        return http.build();
    }
    }
