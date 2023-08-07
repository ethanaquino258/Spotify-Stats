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

        return sortUniqueGenres(performers); 
    }

    public Collection<Genre> sortUniqueGenres(Collection<Performer> performers) {
         HashMap<Integer, Genre> genreMap = new HashMap<>();

        for (Performer performer : performers) {
            for (Genre genre : performer.getGenreList()) {
                Genre genreObj = new Genre(genre.getGenreName());
                int hashKey = genreObj.hashCode();

                if (genreMap.containsKey(hashKey)) {
                    Genre genreAtKey = genreMap.get(hashKey);
                    genreAtKey.setCount(genreAtKey.getCount() + 1);
                    genreAtKey.setSongCount(genreAtKey.getSongCount() + performer.getCount());
                    genreMap.put(hashKey, genreAtKey);
                } else {
                    genreObj.setCount(1);
                    genreObj.setSongCount(performer.getCount());
                    genreMap.put(hashKey, genreObj);
                }
            }
        }

        return genreMap.values();
    }
}
