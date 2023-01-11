package com.ethanaquino.Spotify.Stats;

import java.util.Collection;

public class Performer {

    private long id;
    private String performerName;
    private String performerId;
    private String performerUri;
    //have to make a separate call to obtain genres and images
    private Collection<String> genres;
    private String imageUrl;

    public Performer(String name, String performerId, String uri) {
        this.performerName = name;
        this.performerId = performerId;
        this.performerUri = uri;
    }

    public String getPerformerName() {
        return this.performerName;
    }

    public String getPerformerId() {
        return this.performerId;
    }

    public String getperformerUri() {
        return this.performerUri;
    }

    public Collection<String> getGenreList() {
        return this.genres;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setGenre(Collection<String> genres) {
        this.genres = genres;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    //there's also a simpler implementation from Objects.class (.hash())
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (performerName == null ? 0 : performerName.hashCode());
        hash = 31 * hash + (imageUrl == null ? 0 : imageUrl.hashCode());
        return hash;
    }

    @Override
    public boolean equals (Object a) {
        if (this == a) return true;
        if (a == null) return false;
        if (this.getClass() != a.getClass()) return false;
        Performer performer = (Performer) a;
        return id == performer.id && (performerName.equals(performer.performerName) && imageUrl.equals(performer.imageUrl));
    }
    
}
