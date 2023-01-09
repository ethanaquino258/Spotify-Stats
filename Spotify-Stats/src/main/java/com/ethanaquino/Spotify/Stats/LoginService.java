package com.ethanaquino.Spotify.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    ExternalApiComponent spotifyComponent;

    public String getSpotifyUri() {
        return spotifyComponent.loginToSpotify();
    }

    public void retrieveTokens(String code) {
        spotifyComponent.getTokens(code);

        // System.out.println(spotifyComponent.getAccessToken());
        // System.out.println(spotifyComponent.getRefreshToken());
    }
    
}
