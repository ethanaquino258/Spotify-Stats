package com.ethanaquino.Spotify.Stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

    public Collection<Song> getTopTracks(String timeFrame) throws Exception {
        SpotifyApi apiClient = spotifyComponent.getSpotifyApi();

        if (timeFrame == null) {
            timeFrame = "short_term";
        }
        GetUsersTopTracksRequest getUsersTopTracksRequest = apiClient.getUsersTopTracks().limit(50).time_range(timeFrame).build();

        try {
            Paging<Track> trackPaging = getUsersTopTracksRequest.execute();
            
            Collection<Song> songCollection = new ArrayList<Song>();

            for (int song=0; song < trackPaging.getItems().length; song++) {
                Track iteratedTrack = trackPaging.getItems()[song];


                Collection<String> artistCollection = new ArrayList<String>();
                for (int artist=0; artist < iteratedTrack.getArtists().length; artist++) {
                    artistCollection.add(iteratedTrack.getArtists()[artist].getName());
                }

                //note that each album has 3 image urls ordered by decreasing image size
                Song songObj = new Song(iteratedTrack.getName(), artistCollection, iteratedTrack.getAlbum().getName(), iteratedTrack.getUri(), iteratedTrack.getAlbum().getImages()[0].getUrl());
                songCollection.add(songObj);
            }

            return songCollection;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }
    }


}
