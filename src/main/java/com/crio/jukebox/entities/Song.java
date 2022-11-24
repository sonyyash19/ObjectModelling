package com.crio.jukebox.entities;


public class Song extends BaseEntity{

    private final String songName;
    private final String genre;
    private final String albumName;
    private final String artist;
    private String featuredArtists;

    public Song(Song song){
        this(song.id, song.songName, song.genre,song.albumName, song.artist, song.featuredArtists);
    }

    public Song(String id, String songName, String genre, String albumName, String artist, String featuredArtists){
        this(songName, genre, albumName, artist, featuredArtists);
        this.id = id;
    }

    public Song(String name, String genre, String albumName, String artist, String featuredArtists){
        this.songName = name;
        this.genre = genre; 
        this.albumName = albumName;
        this.artist = artist;
        this.featuredArtists = featuredArtists;
    }

    public String getSongName() {
        return songName;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public String getFeaturedArtists() {
        return featuredArtists;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Song other = (Song) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Song [albumName=" + albumName + ", artist=" + artist + ", featuredArtists="
                + featuredArtists + ", genre=" + genre + ", songName=" + songName + ", id=" + id + "]";
    }
    
}

