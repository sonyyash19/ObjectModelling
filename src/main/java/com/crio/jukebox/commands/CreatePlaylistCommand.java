package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.services.IPlaylistService;

public class CreatePlaylistCommand implements ICommand{

    private final IPlaylistService playlistService;

    public CreatePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    //create playlist(userId, playlistname, list song id)
    //tokens = ["CREATE-PLAYLIST", "1", "MY_PLAYLIST_", [1 1 4 5 6]]
    @Override
    public void execute(List<String> tokens) {
        if(tokens.get(0).equalsIgnoreCase("create-playlist")){
            int tokensSize = tokens.size();
            List<String> songIds = new ArrayList<>();
            for(int i = 3; i < tokensSize; i++){
                songIds.add(tokens.get(i));
            }

            String playlist = playlistService.create(tokens.get(1), tokens.get(2), songIds);
            System.out.println(playlist);
        }
        
    }
    
}
