package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;

public class PlaylistRepository implements IPlaylistRepository{

    private final Map<String, Playlist> playlistMap;
    private Integer autoIncrement = 0;

    public PlaylistRepository(){
        playlistMap = new HashMap<String, Playlist>();
    }

    public PlaylistRepository(Map<String, Playlist> playlistMap){
        this.playlistMap = playlistMap;
        this.autoIncrement = playlistMap.size();
    }

    @Override
    public Playlist save(Playlist entity) {
        if(entity.getId() == null){
            autoIncrement++;
            Playlist playlist = new Playlist(Integer.toString(autoIncrement), entity.getPlaylistName(), entity.getSongs());
            playlistMap.put(playlist.getId(), playlist);
            return playlist;
        }

        playlistMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Playlist> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playlistMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(Playlist entity) {
        playlistMap.remove(entity.getId());   
    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
