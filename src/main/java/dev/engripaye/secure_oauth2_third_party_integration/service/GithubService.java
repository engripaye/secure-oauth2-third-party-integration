package dev.engripaye.secure_oauth2_third_party_integration.service;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GithubService {

    public String listRepos(String accessToken) throws Exception{
        GitHub gitHub = new GitHubBuilder().withOAuthToken(accessToken).build();
        return gitHub.getMyself().listRepositories().toList().stream()
                .map(repo -> repo.getName())
                .collect(Collectors.joining(","));
    }
}
