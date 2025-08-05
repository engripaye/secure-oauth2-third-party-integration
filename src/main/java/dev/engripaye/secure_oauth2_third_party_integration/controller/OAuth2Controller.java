package dev.engripaye.secure_oauth2_third_party_integration.controller;

import dev.engripaye.secure_oauth2_third_party_integration.service.TokenService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/integrations")
public class OAuth2Controller {

    private final TokenService tokenService;

    public OAuth2Controller(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/success")
    public String handleOAuth2Success(@AuthenticationPrincipal OAuth2User principal) throws Exception{
        String provider = principal.getAttribute("provider");
        String userId = principal.getAttribute("email") != null ? principal.getAttribute("email") : principal.getAttribute("login");
        String accessToken = principal.getAttribute("access_token");
        String refreshToken = principal.getAttribute("refresh_token");
        Long expiresIn = principal.getAttribute("expires_in");
        String scope = principal.getAttribute("scope");

        tokenService.saveToken(userId, provider, accessToken, refreshToken, expiresIn, scope);
        return "Successfully connected to " + provider + "!";

    }
}
