package com.crio.jukebox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.dto.ModifyPlaylistDto;
import com.crio.jukebox.dto.SongDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;

public class PlaylistServiceImpl implements IPlaylistService{

    private final IPlaylistRepository playlistRepository;
    private final ISongRepository songRepository;
    private final IUserRepository userRepository;

    public PlaylistServiceImpl(IPlaylistRepository playlistRepository, ISongRepository songRepository, IUserRepository userRepository){
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String  create(String userId, String playlistName, List<String> songId) {
        List<Song> songs = new ArrayList<>();
        Optional<User> user = userRepository.findById(userId);
        for(String id: songId){
            Optional<Song> song = songRepository.findById(id);

            if(song.equals(Optional.empty())){
                throw new SongNotAvailableException("Some songs requested are not available in the pool");
            }

            songs.add(song.get());
        }

        Playlist playlistToBeSaved = new Playlist(playlistName, songs);
        Playlist playlist = playlistRepository.save(playlistToBeSaved);
        user.get().addPlaylist(playlist);
        userRepository.save(user.get());
        return playlist.getId();
    }

    @Override
    public String delete(String userId, String playlistId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        if(playlist.equals(Optional.empty())){
            throw new PlaylistNotFoundException("Playlist Not Found");
        }

        user.get().deletePlaylist(playlist.get());
        playlistRepository.delete(playlist.get());
        playlistRepository.save(playlist.get());
        userRepository.save(user.get());

        return "Deleted Successfully";
    }

    

    @Override
    public ModifyPlaylistDto modifyPlaylist(String message, String userId, String playlistId, List<String> songId){

        if(message.equalsIgnoreCase("add-song")){
            addSongToPlaylist(userId, playlistId, songId);
        }

        if(message.equalsIgnoreCase("delete-song")){
            deleteSongFromPlaylist(userId, playlistId, songId);
        }

        return null;
    }

    public ModifyPlaylistDto addSongToPlaylist(String userId, String playlistId,
            List<String> songId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        List<Song> songs = new ArrayList<Song>();
        for(String id: songId){
            Optional<Song> song = songRepository.findById(id);

            if(song.equals(Optional.empty())){
                throw new SongNotAvailableException("Song not available to be added to playlist");
            }

            songs.add(song.get());
        }

        playlist.get().addSongs(songs);
        playlistRepository.save(playlist.get());
        userRepository.save(user.get());
        ModifyPlaylistDto modifyPlaylistDto = new ModifyPlaylistDto(playlistId, playlist.get().getPlaylistName(), songId);
        return modifyPlaylistDto;
    }

    
    public ModifyPlaylistDto deleteSongFromPlaylist(String userId, String playlistId,
            List<String> songId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);

        List<Song> songs = new ArrayList<Song>();
        for(String id: songId){
            Optional<Song> song = songRepository.findById(id);
            songs.add(song.get());
        }

        playlist.get().deleteSong(songs);
        playlistRepository.save(playlist.get());
        userRepository.save(user.get());

        ModifyPlaylistDto modifyPlaylistDto = new ModifyPlaylistDto("1", playlist.get().getPlaylistName(), songId);
        return modifyPlaylistDto;
    }

    @Override
    public SongDto playPlaylist(String userId, String playlistId) {
        User user = userRepository.findById(userId).get();
        List<Playlist> uPlaylists = user.getPlaylist();

        Playlist playlist = uPlaylists.stream().filter(p -> p.getId().equals(playlistId)).findAny().orElse(null);

        if(playlist.getSongs().size() == 0){
            throw new EmptyPlaylistException("Playlist is empty.");
        }

        user.setActivePlaylist(playlist);
        user.setCurrentSong(playlist.getSongs().get(0));
        userRepository.save(user);

        String songName = playlist.getSongs().get(0).getSongName();
        String albumName = playlist.getSongs().get(0).getAlbumName();
        String featuredArtists = playlist.getSongs().get(0).getFeaturedArtists();

        SongDto songDto = new SongDto("Current Song Playing.", songName, albumName, featuredArtists);
        return songDto;
    }
    
}
