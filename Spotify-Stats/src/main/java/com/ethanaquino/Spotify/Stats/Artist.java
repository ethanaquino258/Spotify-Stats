package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

public class Artist {

    private long id;
    private String artistName;
    private String artistUri;
    //have to make a separate call to obtain genres and images
    private Collection<String> genre;
    private String imageUrl;

    public Artist(String name, String uri) {
        this.artistName = name;
        this.artistUri = uri;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public String getArtistUri() {
        return this.artistUri;
    }

    public Collection<String> getGenreList() {
        return this.genre;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    //there's also a simpler implementation from Objects.class (.hash())
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (artistName == null ? 0 : artistName.hashCode());
        hash = 31 * hash + (imageUrl == null ? 0 : imageUrl.hashCode());
        return hash;
    }

    @Override
    public boolean equals (Object a) {
        if (this == a) return true;
        if (a == null) return false;
        if (this.getClass() != a.getClass()) return false;
        Artist artist = (Artist) a;
        return id == artist.id && (artistName.equals(artist.artistName) && imageUrl.equals(artist.imageUrl));
    }
    
}
