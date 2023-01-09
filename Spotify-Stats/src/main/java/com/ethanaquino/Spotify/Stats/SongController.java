package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {

    @Autowired
    SongService songService;

    @GetMapping("/topTracks")
    public Collection<Song> getTopTracks(@RequestParam(required = false) String timeFrame) throws Exception {
        return songService.getTopTracks(timeFrame);
    }
}
