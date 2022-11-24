package com.crio.jukebox.dto;

import java.util.List;

public class ModifyPlaylistDto {
    
    private final String playlistId;
    private final String playlistName;
    private final List<String> songIds;

    public ModifyPlaylistDto(String playlistId, String playlistName, List<String> songIds){
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.songIds = songIds;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public List<String> getSongIds() {
        return songIds;
    }

    @Override
    public String toString() {
        return "ModifyPlaylistDto [playlistId=" + playlistId + ", playlistName=" + playlistName
        + ", songIds=" + songIds + "]";
    }

    
}
