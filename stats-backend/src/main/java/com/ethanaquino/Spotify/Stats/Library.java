package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

public class Library {

    private Collection<Song> songs;
    private Collection<Performer> performers;
    private Collection<Genre> genres;
    // private Collection<Genre> genres;

    public Library(Collection<Song> songs, Collection<Performer> performers, Collection<Genre> genres) {
        this.songs = songs;
        this.performers = performers;
        this.genres = genres;
    }

    public Collection<Song> getSongs() {
        return this.songs;
    }

    public Collection<Performer> getPerformers() {
        return this.performers;
    }

    public Collection<Genre> getGenres() {
        return this.genres;
    }
}