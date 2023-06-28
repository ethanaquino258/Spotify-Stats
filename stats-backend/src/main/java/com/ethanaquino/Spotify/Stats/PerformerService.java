package com.ethanaquino.Spotify.Stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.artists.GetArtistRequest;
import se.michaelthelin.spotify.requests.data.artists.GetSeveralArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import com.ethanaquino.Spotify.Stats.Performer;

import io.micrometer.common.util.StringUtils;

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
                // this.completePerformer(performerObj);
                performerCollection.add(performerObj);
            }

            this.completeMultiplePerformers(performerCollection);

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

            //note that each artist has 3-4 image urls ordered by decreasing image size
            performer.setImageUrl(artist.getImages()[0].getUrl());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }
    }

    public void completeMultiplePerformers(Collection<Performer> performerCollection) throws Exception {
        SpotifyApi apiClient = spotifyComponent.getSpotifyApi();

        StringBuilder idString = new StringBuilder();

        Iterator<Performer> iterator = performerCollection.iterator();

        while (iterator.hasNext()) {
            idString.append(iterator.next().getPerformerId());
            idString.append(",");
        }
        String artistIDs = idString.substring(0, idString.length() - 1);

        // System.out.println(artistIDs);


        GetSeveralArtistsRequest getSeveralArtistsRequest = apiClient.getSeveralArtists(artistIDs).build();

        try {
            Artist[] artists = getSeveralArtistsRequest.execute();

            for (int artistCount=0; artistCount < artists.length; artistCount++) {
                final Artist thiArtist = artists[artistCount];
                //this could be an issue. Need to implement a more unique identifier via hashcode
                Performer thisPerformer = performerCollection.stream().filter(performer -> performer.getPerformerName().equals(thiArtist.getName())).findFirst().get();
                Collection<String> genreCollection = Arrays.asList(artists[artistCount].getGenres());
                if (thiArtist.getImages().length > 0) {
                    thisPerformer.setImageUrl(thiArtist.getImages()[0].getUrl());
                }
                thisPerformer.setGenre(genreCollection);
            }
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }

    }
}
