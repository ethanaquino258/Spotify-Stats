package com.ethanaquino.Spotify.Stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    
    @Autowired
    PerformerService performerService;

    public Collection<Genre> getTopGenres(String timeFrame) throws Exception {

        Collection<Performer> performers = new ArrayList<Performer>();
        performers = performerService.getTopArtists(timeFrame);

        Collection<Genre> genreCollection = new ArrayList<Genre>();
        HashMap<Integer, Genre> genreMap = new HashMap<>();


        return null;
    }
}
