package dev.engripaye.secure_oauth2_third_party_integration.controller;

import dev.engripaye.secure_oauth2_third_party_integration.service.TokenService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integrations")
public class OAuth2Controller {

    private final TokenService tokenService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public OAuth2Controller(TokenService tokenService, OAuth2AuthorizedClientService authorizedClientService) {
        this.tokenService = tokenService;
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/success")
    public String handleOAuth2Success(
            OAuth2AuthenticationToken authentication,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient
    ) throws Exception {
        if (authentication == null || authentication.getPrincipal() == null) {
            return "Authentication failed: No user info received.";
        }

        String provider = authentication.getAuthorizedClientRegistrationId();
        OAuth2User principal = authentication.getPrincipal();

        String userId = principal.getAttribute("email") != null ?
                principal.getAttribute("email") :
                principal.getAttribute("login");

        if (authorizedClient == null) {
            return "Authentication failed: Could not load authorized client for provider: " + provider;
        }

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        String refreshToken = authorizedClient.getRefreshToken() != null ?
                authorizedClient.getRefreshToken().getTokenValue() : null;

        Long expiresIn = authorizedClient.getAccessToken().getExpiresAt() != null ?
                authorizedClient.getAccessToken().getExpiresAt().toEpochMilli() : null;

        String scope = String.join(" ", authorizedClient.getAccessToken().getScopes());

        tokenService.saveToken(userId, provider, accessToken, refreshToken, expiresIn, scope);

        return "Successfully connected to " + provider + "!";
    }

}
