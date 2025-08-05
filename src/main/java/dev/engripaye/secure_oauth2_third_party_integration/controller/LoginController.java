package dev.engripaye.secure_oauth2_third_party_integration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String dashboard() {
        return "Welcome!";
    }

}
