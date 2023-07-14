package com.ethanaquino.Spotify.Stats;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;

@Component
public class ExternalApiComponent {
    @Value("${client.id}")
    private String id;

    private static String CLIENT_ID;

    @Value("${client.id}")
    public void setClientIdStatic(String clientId) {
        ExternalApiComponent.CLIENT_ID = clientId;
    }

    @Value("${client.secret}")
    private String secret;

    private static String CLIENT_SECRET;

    @Value("${client.secret}")
    public void setClientSecretStatic(String clientSecret) {
        ExternalApiComponent.CLIENT_SECRET = clientSecret;
    }

    //Throws an error if I try to set the uri to an environment variable
    // @Value("${client.redirectUri}")
    // private String redirectString;

    // private static String REDIRECT_URI;

    // @Value("${client.id}")
    // public void setRedirectUriStatic(String redirectUri) {
    //     ExternalApiComponent.REDIRECT_URI = redirectUri;
    // }

    private String accessToken;
    private String refreshToken;
    private SpotifyApi spotifyApi;

    @PostConstruct
    private void buildApiObject() {
        SpotifyApi spotifyApi = new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).setRedirectUri(SpotifyHttpManager.makeUri("http://localhost:8080/login")).build();

        this.spotifyApi = spotifyApi;
    }

    public SpotifyApi getSpotifyApi() throws Exception {
        return this.spotifyApi;
    }

    
    public String loginToSpotify(String scope) {
        AuthorizationCodeUriRequest authCodeUriRequest = spotifyApi.authorizationCodeUri().scope(scope).build();
        URI uri = authCodeUriRequest.execute();
        // System.out.println(uri.toString());
        return uri.toString();
    }

    public void getTokens(String code) {
        AuthorizationCodeRequest authCodeRequest = spotifyApi.authorizationCode(code).build();
        try {
            AuthorizationCodeCredentials authCodeCredentials = authCodeRequest.execute();
            spotifyApi.setAccessToken(authCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authCodeCredentials.getRefreshToken());
            this.accessToken = authCodeCredentials.getAccessToken();
            this.refreshToken = authCodeCredentials.getRefreshToken();
        }  catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
          }
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

}
