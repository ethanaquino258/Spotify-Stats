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
            //return success or the like to frontend (frontend uses as conditional)
            return "code";

            //should throw an error if no scope provided
            //eventually should try to match scope with one in the auth scopes enum provided by the library
        } else {
            //return url to frontend
            return loginService.getSpotifyUri(scope);
        }
    }
}
