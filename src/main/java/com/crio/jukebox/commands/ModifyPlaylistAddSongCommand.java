package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.dto.ModifyPlaylistDto;
import com.crio.jukebox.services.IPlaylistService;

public class ModifyPlaylistAddSongCommand implements ICommand{

    private final IPlaylistService playlistService;

    public ModifyPlaylistAddSongCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }


    // modifyplaylist(message, userid, playlistid, list of song)
    //tokens = MODIFY-PLAYLIST ADD-SONG 1 1 7
    @Override
    public void execute(List<String> tokens) {
        if(tokens.get(0).equalsIgnoreCase("modify-playlist")){
            int tokensSize = tokens.size();
            List<String> songIds = new ArrayList<>();
            for(int i = 4; i < tokensSize; i++){
                songIds.add(tokens.get(i));
            }
            ModifyPlaylistDto addSongToPlaylist = playlistService.modifyPlaylist(tokens.get(1), tokens.get(2), tokens.get(3), songIds);
            System.out.println(addSongToPlaylist);
        }
        
    }
    
}
