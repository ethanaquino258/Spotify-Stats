package com.ethanaquino.Spotify.Stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import com.ethanaquino.Spotify.Stats.Performer;

@Service
public class PerformerService {
    @Autowired
    ExternalApiComponent spotifyComponent;

    public Collection<Performer> getTopArtists(String timeFrame) throws Exception {
        SpotifyApi apiClient = spotifyComponent.getSpotifyApi();

        if (timeFrame == null) timeFrame = "short_term";

        GetUsersTopArtistsRequest getUsersTopArtistsRequest = apiClient.getUsersTopArtists().limit(50).time_range(timeFrame).build();

        try {
            Paging<Artist> artistPaging = getUsersTopArtistsRequest.execute();

            Collection<Performer> performerCollection = new ArrayList<Performer>();

            for (int artistCount=0; artistCount < artistPaging.getItems().length; artistCount++) {

                Artist thisArtist = artistPaging.getItems()[artistCount];
                Performer performerObj = new Performer(thisArtist.getName(), thisArtist.getId(), thisArtist.getUri());
                this.completePerformer(performerObj);
                performerCollection.add(performerObj);
            }

            return performerCollection;

        }  catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }
    }

    //there's also a get multiple artists with max 50 IDs. Do that next
    public void completePerformer(Performer performer) throws Exception {
        SpotifyApi apiClient = spotifyComponent.getSpotifyApi();

        GetArtistRequest getArtistRequest = apiClient.getArtist(performer.getPerformerId()).build();

        try {
            Artist artist = getArtistRequest.execute();

            Collection<String> genreCollection = Arrays.asList(artist.getGenres());

            performer.setGenre(genreCollection);

            System.out.println(artist.getImages().length);
            for (int imageCount=0; imageCount < artist.getImages().length; imageCount++) {
                System.out.println(artist.getImages()[imageCount].getUrl());
            }
            //note that each artist has 3-4 image urls ordered by decreasing image size
            performer.setImageUrl(artist.getImages()[0].getUrl());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }
    }
}
