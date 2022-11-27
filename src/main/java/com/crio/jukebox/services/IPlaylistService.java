package com.crio.jukebox.services;

import java.util.List;

public interface IPlaylistService {
    
    public String create(String userId, String playlistName, List<String> songId);
    public String delete(String userId, String playlistId);
    public String modifyPlaylist(String message, String userId, String playlistId, List<String> songId);
    public String playPlaylist(String userId, String playlistId);
}
