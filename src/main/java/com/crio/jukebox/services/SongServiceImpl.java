package com.crio.jukebox.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.crio.jukebox.dto.SongDto;
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
    public List<Song> loadSongs(String fileName){
        List<Song> songs = new ArrayList<>();
        // Path pathToFile = Paths.get(fileName);
        // System.out.println("Path to file " + pathToFile);
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

        return songs;
    }

    // @Override
    // public SongDto playSong(String userId, String playlistId, String songToPlay) {
        
    //     User user = userRepository.findById(userId).get();
    //     List<Playlist> uPlaylists = user.getPlaylist();
    //     Playlist playlist = uPlaylists.stream().filter(p -> p.getId().equals(playlistId)).findAny().orElse(null);

    //     List<Song> songs = playlist.getSongs();

    //     if(songs.size() == 0){
    //         throw new EmptyPlaylistException("There is no song in the playlist.");
    //     }

    //     Song currentSong = songs.get(0);

    //     if(songToPlay.equalsIgnoreCase("play")){
    //         SongDto songFromPlaylist = playlistService.playPlaylist(userId, playlistId);
    //         Song playingSong = songs.stream().filter(s -> s.getSongName().equalsIgnoreCase(songFromPlaylist.getSongName())).findAny().orElse(null);
    //         currentSong = playingSong;
    //         SongDto songDto = new SongDto("Current Song Playing.", playingSong.getSongName(), playingSong.getAlbumName(), playingSong.getFeaturedArtists());
    //         return songDto;
    //     }

    //     if(songToPlay.equalsIgnoreCase("next")){
    //         int index = songs.indexOf(currentSong); 
    //         if(index == songs.size() - 1){
    //             index = -1;
    //         }
    //         Song nextSong = songs.get(index + 1);
    //         currentSong = nextSong;
    //         SongDto songDto = new SongDto("Current Song Playing.", nextSong.getSongName(), nextSong.getAlbumName(), nextSong.getFeaturedArtists());
    //         return songDto;
    //     }

    //     if(songToPlay.equalsIgnoreCase("back")){
    //         int index = songs.indexOf(currentSong); 
    //         if(index == 0){
    //             index = songs.size();
    //         }
    //         Song prevSong = songs.get(index - 1);
    //         currentSong = prevSong;
    //         SongDto songDto = new SongDto("Current Song Playing.", prevSong.getSongName(), prevSong.getAlbumName(), prevSong.getFeaturedArtists());
    //         return songDto;
    //     }
        
    //     Song song = songs.stream().filter(s -> s.getId().equals(songToPlay)).findAny().orElse(null);

    //     if(song == null){
    //         throw new SongNotAvailableException("Song not present in the playlist.");
    //     }

    //     currentSong = song;
    //     SongDto songDto = new SongDto("Current Song Playing.", song.getSongName(), song.getAlbumName(), song.getFeaturedArtists());
    //     return songDto;

    // }

    @Override
    public SongDto playSong(String userId, String songToPlay) {
        
        User user = userRepository.findById(userId).get();
        Playlist activePlaylist = user.getActivePlaylist();

        if(activePlaylist != null){

            List<Song> songs = activePlaylist.getSongs();

            if(songs.size() == 0){
                throw new EmptyPlaylistException("There is no song in the playlist.");
            }

            Song currentSong = user.getCurrentSong();

            if(songToPlay.equalsIgnoreCase("next")){
                int index = songs.indexOf(currentSong); 
                if(index == songs.size() - 1){
                    index = -1;
                }
                Song nextSong = songs.get(index + 1);
                user.setCurrentSong(nextSong);
                userRepository.save(user);
                SongDto songDto = new SongDto("Current Song Playing.", nextSong.getSongName(), nextSong.getAlbumName(), nextSong.getFeaturedArtists());
                return songDto;
            }

            if(songToPlay.equalsIgnoreCase("back")){
                int index = songs.indexOf(currentSong); 
                if(index == 0){
                    index = songs.size();
                }
                Song prevSong = songs.get(index - 1);
                user.setCurrentSong(prevSong);
                userRepository.save(user);
                SongDto songDto = new SongDto("Current Song Playing.", prevSong.getSongName(), prevSong.getAlbumName(), prevSong.getFeaturedArtists());
                return songDto;
            }
            
            Song song = songs.stream().filter(s -> s.getId().equals(songToPlay)).findAny().orElse(null);

            if(song == null){
                throw new SongNotAvailableException("Song not present in the playlist.");
            }

            user.setCurrentSong(song);
            userRepository.save(user);
            SongDto songDto = new SongDto("Current Song Playing.", song.getSongName(), song.getAlbumName(), song.getFeaturedArtists());
            return songDto;

        }

        return null;
    }
    
    
}
