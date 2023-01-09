package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

public class Song {

    private String songName;
    private Collection<String> artist;
    private String albumName;
    private String songUri;
    private String imageUrl;

    public Song(String name, Collection<String> artist, String album, String uri, String url) {
        this.songName = name;
        this.artist = artist;
        this.albumName = album;
        this.songUri = uri;
        this.imageUrl = url;
    }

    public String getSongName() {
        return this.songName;
    }

    public Collection<String> getArtist() {
        return this.artist;
    }

    public String getAlbum() {
        return this.albumName;
    }

    public String getSongUri() {
        return this.songUri;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

}
