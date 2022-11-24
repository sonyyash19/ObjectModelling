package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.dto.SongDto;
import com.crio.jukebox.entities.Song;

public interface ISongService {

    // public SongDto playSong(String userId, String playlistId, String songToPlay);
    public SongDto playSong(String userId, String songToPlay);

    public List<Song> loadSongs(String fileName);
    
}
