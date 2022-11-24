package com.crio.jukebox.exceptions;

public class SongAlreadyPresentInPlaylistException extends RuntimeException{

    public SongAlreadyPresentInPlaylistException(){
        super();
    }

    public SongAlreadyPresentInPlaylistException(String msg){
        super(msg);
    }
    
}
