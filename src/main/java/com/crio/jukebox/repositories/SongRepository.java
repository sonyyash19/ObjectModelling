package com.crio.jukebox.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.crio.jukebox.entities.Song;

public class SongRepository implements ISongRepository{

    private final Map<String, Song> songMap;
    private Integer autoIncrement = 0;

    public SongRepository(){
        songMap = new HashMap<String, Song>();
    }

    public SongRepository(Map<String, Song> songMap) {
        this.songMap = songMap;
        this.autoIncrement = songMap.size();
    }

    @Override
    public Song save(Song entity) {
        if(entity.getId() == null){
            autoIncrement++;
            Song song = new Song(Integer.toString(autoIncrement), entity.getSongName(), entity.getGenre(), entity.getAlbumName(), entity.getArtist(), entity.getFeaturedArtists());
            songMap.put(song.getId(), song);
        }
        songMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        List<Song> songs = songMap.values().stream().collect(Collectors.toList());

        if(songs.size() == 0){
            return new ArrayList<>();
        }

        return songs;
    }

    @Override
    public Optional<Song> findById(String id) {
        return Optional.ofNullable(songMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(Song entity) {
        // TODO Auto-generated method stub
        
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
