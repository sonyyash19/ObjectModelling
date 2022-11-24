package com.crio.jukebox.services;

import java.util.List;
import com.crio.jukebox.dto.ModifyPlaylistDto;
import com.crio.jukebox.dto.SongDto;

public interface IPlaylistService {
    
    public String create(String userId, String playlistName, List<String> songId);
    public String delete(String userId, String playlistId);
    public ModifyPlaylistDto modifyPlaylist(String message, String userId, String playlistId, List<String> songId);
    public SongDto playPlaylist(String userId, String playlistId);
}
