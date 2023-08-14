package com.ethanaquino.Spotify.Stats;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.hc.core5.http.ParseException;

import org.springframework.beans.factory.annotation.Autowired;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.SavedTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.library.GetUsersSavedTracksRequest;

@Service
public class LibraryService {

    @Autowired
    ExternalApiComponent spotifyComponent;

    @Autowired
    PerformerService performerService;

    @Autowired
    GenreService genreService;

    public Library getUserLibrary() throws Exception {
        SpotifyApi apiClient = spotifyComponent.getSpotifyApi();

        GetUsersSavedTracksRequest getUsersSavedTracksRequest = apiClient.getUsersSavedTracks().limit(50).build();
        int currentCount = 0;

        try {
            Paging<SavedTrack> trackPaging = getUsersSavedTracksRequest.execute();
            HashMap<Integer, Performer> performerMap = new HashMap<>();
            Collection<Song> songCollection = new ArrayList<Song>();

            //beginning of song logic
            while (trackPaging.getNext() != null || trackPaging.getItems().length > 0) {
                currentCount = currentCount + trackPaging.getItems().length;

                for (int trackCount=0; trackCount < trackPaging.getItems().length; trackCount++) {
                    Track thisTrack = trackPaging.getItems()[trackCount].getTrack();

                    Collection<Performer> performerCollection = new ArrayList<Performer>();
                    
                    for (int artistCount=0; artistCount < thisTrack.getArtists().length; artistCount++) {
                        ArtistSimplified thisArtist = thisTrack.getArtists()[artistCount];

                        Performer performerObj = new Performer(thisArtist.getName(), thisArtist.getId(), thisArtist.getUri());
                        int hashKey = performerObj.hashCode();

                        if (performerMap.containsKey(hashKey)) {
                            Performer performerAtKey = performerMap.get(hashKey);
                            performerAtKey.setCount(performerAtKey.getCount() + 1);
                            performerMap.put(hashKey, performerAtKey);
                        } else {
                            performerObj.setCount(1);
                            performerMap.put(hashKey, performerObj);
                        }

                        performerCollection.add(performerObj);
                    }

                    Song songObj = new Song(thisTrack.getName(), performerCollection, thisTrack.getAlbum().getName(), thisTrack.getUri(), thisTrack.getAlbum().getImages()[0].getUrl());
                    songCollection.add(songObj);
                }

                getUsersSavedTracksRequest = apiClient.getUsersSavedTracks().limit(50).offset(currentCount).build();
                trackPaging = getUsersSavedTracksRequest.execute();
            }
            //performer logic
            Collection<Performer> overallPerformers = performerMap.values();
    
            List<List<Performer>> partitions = new ArrayList<>();
            int partitionSize = 50;

            for (int i=0; i<overallPerformers.size(); i += partitionSize) {
                partitions.add(overallPerformers.stream().toList().subList(i, Math.min(i + partitionSize, overallPerformers.size())));
            }

            Collection<Performer> performersForLibrary = new ArrayList<>();
            for (List<Performer> list: partitions) {
                performerService.completeMultiplePerformers(list);
                try {
                    performersForLibrary.addAll(list);
                } catch (UnsupportedOperationException e) {
                    System.out.println("Error: " + e);
                    for (int i=0; i<list.size(); i++) {
                        System.out.println("Artist: " + list.get(i).getPerformerName());
                    }
                    throw e;
                }
                
            }

            Collection<Genre> genresForLibrary = genreService.sortUniqueGenres(performersForLibrary);
            
            Library userLibrary = new Library(songCollection, performersForLibrary, genresForLibrary);
            return userLibrary;

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
          }

    }

    public List<Genre> getUserGenres() throws Exception {
        Library library = this.getUserLibrary();

        Collection<Genre> allGenres = library.getGenres();

        List<Genre> listGenre = new ArrayList<>(allGenres.stream().toList());
        Collections.sort(listGenre, Collections.reverseOrder());

        return listGenre;
    }

}
