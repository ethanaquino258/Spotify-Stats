package com.ethanaquino.Spotify.Stats;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryController {

    @Autowired
    LibraryService libraryService;

    @GetMapping("/library")
    public Library getUserLibrary() throws Exception {
        return libraryService.getUserLibrary();
    }

    @GetMapping("/allGenres")
    public List<Genre> getUserGenres() throws Exception {
        return libraryService.getUserGenres();
    }

}
