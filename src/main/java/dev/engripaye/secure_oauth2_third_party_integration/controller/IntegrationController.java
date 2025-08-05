package dev.engripaye.secure_oauth2_third_party_integration.controller;

import dev.engripaye.secure_oauth2_third_party_integration.model.TokenEntity;
import dev.engripaye.secure_oauth2_third_party_integration.service.GithubService;
import dev.engripaye.secure_oauth2_third_party_integration.service.GoogleDriveService;
import dev.engripaye.secure_oauth2_third_party_integration.service.TokenService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IntegrationController {

    private final TokenService tokenService;
    private final GoogleDriveService googleDriveService;
    private final GithubService githubService;

    public IntegrationController(TokenService tokenService, GoogleDriveService googleDriveService, GithubService githubService) {
        this.tokenService = tokenService;
        this.googleDriveService = googleDriveService;
        this.githubService = githubService;
    }

    @GetMapping("google/drive/files")
    public String listGoogleDriveFiles(@AuthenticationPrincipal OAuth2User principal) throws Exception{
        String userId = principal.getAttribute("email");
        TokenEntity token = tokenService.getToken(userId, "google");
        if(token == null ){
            return "Google Drive not connected.";
        }
        String accessToken = tokenService.decryptToken(token.getAccessToken());
        return googleDriveService.listFiles(accessToken).toString();
    }

    @GetMapping("/github/repos")
    public String listGithubRepos(@AuthenticationPrincipal OAuth2User principal) throws Exception{
        String userId = principal.getAttribute("login");
        TokenEntity token = tokenService.getToken(userId, "github");
        if(token == null ){
            return "Github not connected";
        }

        String accessToken = tokenService.decryptToken(token.getAccessToken());
        return githubService.listRepos(accessToken);
    }
}
