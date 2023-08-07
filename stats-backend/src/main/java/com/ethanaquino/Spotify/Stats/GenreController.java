package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {
    @Autowired
    GenreService genreService;

    @GetMapping("/topGenres")
    public Collection<Genre> getTopGenres(@RequestParam(required = false) String timeFrame) throws Exception {
        return genreService.getTopGenres(timeFrame);
    }
}
