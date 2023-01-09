package com.ethanaquino.Spotify.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    
    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String login() {
        return loginService.getSpotifyUri();
    }

    @GetMapping("/callback")
    public void callback(@RequestParam String code) {
        try {
            loginService.retrieveTokens(code);
        } catch (Error e) {
            System.out.println(e.getMessage());
        }
    }
}
