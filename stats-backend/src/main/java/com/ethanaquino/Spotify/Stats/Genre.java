package com.ethanaquino.Spotify.Stats;

public class Genre {
    private long id;
    private String genreName;
    private int count;
    private int songCount;

    public Genre (String name) {
        this.genreName = name;
    }
    
    public String getGenreName() {
        return this.genreName;
    }

    public int getCount() {
        return this.count;
    }

    public int getSongCount() {
        return this.songCount;
    }

    public void setGenreName(String name) {
        this.genreName = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (genreName == null ? 0 : genreName.hashCode());
        return hash;
    }

    @Override
    public boolean equals (Object a) {
        if (this == a) return true;
        if (a == null) return false;
        if (this.getClass() != a.getClass()) return false;
        Genre genre = (Genre) a;
        return id == genre.id && (genreName.equals(genre.genreName));
    }

}
