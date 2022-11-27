package com.crio.jukebox.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;

public class SongServiceImpl implements ISongService{

    private final ISongRepository songRepository;
    private final IUserRepository userRepository;

    public SongServiceImpl(ISongRepository songRepository, IUserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public SongServiceImpl(ISongRepository songRepository) {
        this.songRepository = songRepository;
        this.userRepository = null;
    }

    @Override
    public String loadSongs(String fileName){
        List<Song> songs = new ArrayList<>();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line != null){
                String[] attributes = line.split(",");

                Song song = new Song(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4], attributes[5]);
                
                songs.add(song);
                songRepository.save(song);

                line = reader.readLine();
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return "Songs Loaded successfully";
    }

    @Override
    public String playSong(String userId, String songToPlay) {
        
        User user = userRepository.findById(userId).get();
        Playlist activePlaylist = user.getActivePlaylist();

        if(activePlaylist != null){

            List<Song> songs = activePlaylist.getSongs();
            if(songs.size() == 0){
                throw new EmptyPlaylistException("There is no song in the playlist.");
            }

            Song currentSong = user.getCurrentSong();

            if(songToPlay.equalsIgnoreCase("next")){
                return nextSong(currentSong, songs, user);
            }
            if(songToPlay.equalsIgnoreCase("back")){
                return previousSong(currentSong, songs, user);
            }
            
            return songAtSongId(songToPlay, songs, user);
        }
        return null;
    }

    private String nextSong(Song currentSong, List<Song> songs, User user){
        int index = songs.indexOf(currentSong); 
        if(index == songs.size() - 1){
             index = -1;
        }

        Song nextSong = songs.get(index + 1);
        user.setCurrentSong(nextSong);
        userRepository.save(user);
 
        return "Current Song Playing\n" + "Song - " + nextSong.getSongName() + "\nAlbum - " 
                       + nextSong.getAlbumName() + "\nArtists - " + nextSong.getFeaturedArtists();
    }

    private String previousSong(Song currentSong, List<Song> songs, User user){
        int index = songs.indexOf(currentSong); 
         if(index == 0){
            index = songs.size();
        }

        Song prevSong = songs.get(index - 1);
        user.setCurrentSong(prevSong);
        userRepository.save(user);
        return "Current Song Playing\n" + "Song - " + prevSong.getSongName() + "\nAlbum - " 
                + prevSong.getAlbumName() + "\nArtists - " + prevSong.getFeaturedArtists();
    }

    private String songAtSongId(String songToPlay, List<Song> songs, User user){
        Song song = songs.stream().filter(s -> s.getId().equals(songToPlay)).findAny().orElse(null);

        if(song == null){
            throw new SongNotAvailableException("Given song id is not a part of the active playlist");
        }

        user.setCurrentSong(song);
        userRepository.save(user);
        return "Current Song Playing\n" + "Song - " + song.getSongName() + "\nAlbum - " 
            + song.getAlbumName() + "\nArtists - " + song.getFeaturedArtists();
    }
    
    
}
