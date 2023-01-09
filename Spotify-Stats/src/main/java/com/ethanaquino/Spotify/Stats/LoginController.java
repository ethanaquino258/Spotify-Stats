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
    public String login(@RequestParam(required = false) String code, @RequestParam(required = false) String scope) {

        if (code != null) {
            try {
                loginService.retrieveTokens(code);
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
            return "code";
        } else {
            return loginService.getSpotifyUri(scope);
        }
    }
}
