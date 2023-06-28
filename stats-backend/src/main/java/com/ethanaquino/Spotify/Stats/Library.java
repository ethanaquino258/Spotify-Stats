package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

public class Library {

    private Collection<Song> songs;
    private Collection<Performer> performers;
    // private Collection<Genre> genres;

    public Library(Collection<Song> songs, Collection<Performer> performers) {
        this.songs = songs;
        this.performers = performers;
    }

    public Collection<Song> getSongs() {
        return this.songs;
    }

    public Collection<Performer> getPerformers() {
        return this.performers;
    }
}