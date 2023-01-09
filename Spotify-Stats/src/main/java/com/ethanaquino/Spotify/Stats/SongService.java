package com.ethanaquino.Spotify.Stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.hc.core5.http.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
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

            //could implement enhanced for loops but would have to cast or convert trackPaging to an array or collection
            for (int songCount=0; songCount < trackPaging.getItems().length; songCount++) {

                Track thisTrack = trackPaging.getItems()[songCount];
                Collection<Artist> artistCollection = new ArrayList<Artist>();

                for (int artistCount=0; artistCount < thisTrack.getArtists().length; artistCount++) {

                    ArtistSimplified thisArtist = thisTrack.getArtists()[artistCount];

                    Artist artistObj = new Artist(thisArtist.getName(), thisArtist.getUri());
                    artistCollection.add(artistObj);
                }

                //note that each album has 3 image urls ordered by decreasing image size
                Song songObj = new Song(thisTrack.getName(), artistCollection, thisTrack.getAlbum().getName(), thisTrack.getUri(), thisTrack.getAlbum().getImages()[0].getUrl());
                songCollection.add(songObj);
            }

            return songCollection;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }
    }


}
