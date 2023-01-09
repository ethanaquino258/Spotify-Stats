package com.ethanaquino.Spotify.Stats;

import java.io.IOException;
import org.apache.hc.core5.http.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

@Service
public class SongService {
    @Autowired
    ExternalApiComponent spotifyComponent;

    public void getTopTracks() throws Exception {
        SpotifyApi apiClient = spotifyComponent.getSpotifyApi();

        GetUsersTopTracksRequest getUsersTopTracksRequest = apiClient.getUsersTopTracks().limit(50).build();

        try {
            Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            System.out.println("Total: " + trackPaging.getTotal());
            System.out.println(trackPaging.getItems());

            for (int i=0; i < trackPaging.getItems().length; i++) {
                System.out.println(trackPaging.getItems()[i].getName());
            }

            // for (int i = 0; i < trackPaging.getTotal(); i++) {
            //     System.out.println(trackPaging.);
            // }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
          }
    }


}
