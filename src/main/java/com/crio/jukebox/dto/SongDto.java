package com.crio.jukebox.dto;

public class SongDto {

    private final String message;
    private final String songName;
    private final String albumName;
    private String featuredArtists;
    
    public SongDto(String message, String songName, String albumName, String featuredArtists) {
        this.message = message;
        this.songName = songName;
        this.albumName = albumName;
        this.featuredArtists = featuredArtists;
    }

    public String getMessage() {
        return message;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getSongName() {
        return songName;
    }

    public String getFeaturedArtists() {
        return featuredArtists;
    }

    @Override
    public String toString() {
        return "SongDto [featuredArtists=" + featuredArtists + ", message=" + message
                + ", songName=" + songName + "]";
    }

    
    
}
