package com.ethanaquino.Spotify.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    ExternalApiComponent spotifyComponent;

    public String getSpotifyUri(String scope) {
        return spotifyComponent.loginToSpotify(scope);
    }

    public void retrieveTokens(String code) {
        spotifyComponent.getTokens(code);
    }
    
}
