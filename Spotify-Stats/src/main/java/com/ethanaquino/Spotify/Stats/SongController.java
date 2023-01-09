package com.ethanaquino.Spotify.Stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping("/topTracks")
    public void getTopTracks() throws Exception {
        songService.getTopTracks();
    }
}
