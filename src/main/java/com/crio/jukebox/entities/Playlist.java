package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.exceptions.SongAlreadyPresentInPlaylistException;
import com.crio.jukebox.exceptions.SongNotPresentInPlaylistException;

public class Playlist extends BaseEntity{

    private String playlistName;
    private List<Song> songs;

    public Playlist(Playlist playlist){
        this(playlist.id, playlist.playlistName, playlist.songs);
    }

    public Playlist(String id, String playlistName, List<Song> songs){
        this.id = id;
        this.playlistName = playlistName;
        this.songs = songs;
    }

    public Playlist(String id, String playlistName){
        this.id = id;
        this.playlistName = playlistName;
        this.songs = new  ArrayList<>();
    }

    public Playlist(String playlistName, List<Song> songs){
        this.playlistName = playlistName;
        this.songs = songs;;
    }

    public Playlist(String playlistName){
        this.playlistName = playlistName;
        this.songs = new ArrayList<>();
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSongs(List<Song> songs) {
        checkIfSongAlreadyPresent(songs);
        this.songs.addAll(songs);
    }

    public void deleteSong(List<Song> songs){
        checkIfSongNotPresent(songs);
        this.songs.removeAll(songs);
    }

    public boolean checkIfSongAlreadyPresent(List<Song> songs){
        List<Song> songsPresentInPlaylist = new ArrayList<>();
        for (Song song : songs) {
            Song singleSong = this.songs.stream().filter(s -> s.getId().equals(song.getId())).findAny().orElse(null);

            if(singleSong != null){
                songsPresentInPlaylist.add(song);
            }
        }

        if(songsPresentInPlaylist.size() > 0){
            throw new SongAlreadyPresentInPlaylistException("Some songs are already present in the playlist.");
        }
        
        return true;
    }

    public boolean checkIfSongNotPresent(List<Song> songs){
        List<Song> songsNotPresentInPlaylist = new ArrayList<>();
        for (Song song : songs) {
            Song singleSong = this.songs.stream().filter(s -> s.getId().equals(song.getId())).findAny().orElse(null);

            if(singleSong == null){
                songsNotPresentInPlaylist.add(song);
            }
        }

        if(songsNotPresentInPlaylist.size() > 0){
            throw new SongNotPresentInPlaylistException("Some songs are not present in the playlist.");
        }
        
        return false;
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
        Playlist other = (Playlist) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        
        return true;
    }

    @Override
    public String toString() {
        return "Playlist [playlistId=" + id + ", playlistName=" + playlistName + ", songs=" + songs + "]";
    }
    
    
}
