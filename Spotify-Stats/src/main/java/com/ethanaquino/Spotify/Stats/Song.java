package com.ethanaquino.Spotify.Stats;

public class Song {

    private String songName;
    private String artist;
    private String albumName;
    private String songUri;
    private String imageUrl;

    public void setSongName(String name) {
        this.songName = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.albumName = album;
    }

    public void setUri(String uri) {
        this.songUri = uri;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public String getSongName() {
        return this.songName;
    }

    public String getArtist() {
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
