package com.crio.jukebox.commands;

import java.util.List;
import com.crio.jukebox.services.ISongService;

public class LoadDataCommand implements ICommand{

    private final ISongService songService;

    public LoadDataCommand(ISongService songService) {
        this.songService = songService;
    }


    //load song(string filename)
    //tokens = LOAD-DATA songs.csv
    @Override
    public void execute(List<String> tokens) {
        if(tokens.get(0).equalsIgnoreCase("load-data")){
            String songs = songService.loadSongs(tokens.get(1));
            System.out.println(songs);
        }
        
    }
    
}
