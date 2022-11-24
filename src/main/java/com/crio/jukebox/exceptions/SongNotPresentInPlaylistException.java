package com.crio.jukebox.exceptions;

public class SongNotPresentInPlaylistException extends RuntimeException{

    public SongNotPresentInPlaylistException(){
        super();
    }

    public SongNotPresentInPlaylistException(String msg){
        super(msg);
    }
    
}
