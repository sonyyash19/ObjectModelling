package com.crio.jukebox.services;

public interface ISongService {

    public String playSong(String userId, String songToPlay);
    public String loadSongs(String fileName);
    
}
