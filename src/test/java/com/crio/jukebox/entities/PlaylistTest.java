package com.crio.jukebox.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.exceptions.SongAlreadyPresentInPlaylistException;
import com.crio.jukebox.exceptions.SongNotPresentInPlaylistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Playlist Test")
public class PlaylistTest {

    @Test
    @DisplayName("1. If song already exist in the playlist throw Song Already Present In The Playlist Exception.")
    public void checkIfSongExistInThePlaylist_ShouldThrowAlreadyPresentInThePlaylist_GivenSong(){
        //Arrange
        String id = "1";
        String playlistName = "Gym Songs";
        String featuredArtist = "Nam1#Name2#Name3";
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", featuredArtist));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };

        List<Song> addedSongs = new ArrayList<Song>(){
            {
                add(new Song("4", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };
        Playlist playlist = new Playlist(id, playlistName, songs);

        //Act
        Assertions.assertThrows(SongAlreadyPresentInPlaylistException.class, () -> playlist.checkIfSongAlreadyPresent(addedSongs));

    }

    @Test
    @DisplayName("2. If song does not exist in the playlist return true.")
    public void checkIfSongExistInThePlaylist_ShouldReturnTrueIfSongNotFound_GivenSong(){
        //Arrange
        String id = "1";
        String playlistName = "Gym Songs";
        String featuredArtist = "Nam1#Name2#Name3";
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", featuredArtist));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };

        List<Song> addedSongs = new ArrayList<Song>(){
            {
                add(new Song("4", "songName4", "genre4", "albumName4", "artist4", featuredArtist));
                add(new Song("5", "songName5", "genre5", "albumName5", "artist5", featuredArtist));
                
            }
        };
        Playlist playlist = new Playlist(id, playlistName, songs);

        //Act
        Assertions.assertTrue(playlist.checkIfSongAlreadyPresent(addedSongs));

    }

    @Test
    @DisplayName("3. If song does not exist in the playlist throw Song Not Present In The Playlist Exception.")
    public void checkIfSongNotExistInThePlaylist_ShouldThrowSongNotPresentInThePlaylist_GivenSong(){
        //Arrange
        String id = "1";
        String playlistName = "Gym Songs";
        String featuredArtist = "Nam1#Name2#Name3";
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", featuredArtist));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };

        List<Song> deleteSongs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("5", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };
        Playlist playlist = new Playlist(id, playlistName, songs);

        //Act
        Assertions.assertThrows(SongNotPresentInPlaylistException.class, () -> playlist.checkIfSongNotPresent(deleteSongs));

    }

    @Test
    @DisplayName("4. If song exist in the playlist return false.")
    public void checkIfSongNotExistInThePlaylist_ShouldReturnFalseIfSongFound_GivenSong(){
        //Arrange
        String id = "1";
        String playlistName = "Gym Songs";
        String featuredArtist = "Nam1#Name2#Name3";
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", featuredArtist));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };

        List<Song> deleteSongs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName4", "genre4", "albumName4", "artist4", featuredArtist));
                add(new Song("3", "songName5", "genre5", "albumName5", "artist5", featuredArtist));
                
            }
        };
        Playlist playlist = new Playlist(id, playlistName, songs);

        //Act
        Assertions.assertFalse(playlist.checkIfSongNotPresent(deleteSongs));
    }

    @Test
    @DisplayName("5. Delete song if song is present in the playlist")
    public void deletSong_SongPresentInPlaylist_GivenSong(){
        String id = "1";
        String playlistName = "Gym Songs";
        String featuredArtist = "Nam1#Name2#Name3";
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", featuredArtist));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };

        List<Song> deleteSongs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName4", "genre4", "albumName4", "artist4", featuredArtist));
                
            }
        };
        Playlist playlist = new Playlist(id, playlistName, songs);
        playlist.deleteSong(deleteSongs);
        int expectedCount = 2;

        //Act
        assertEquals(expectedCount, playlist.getSongs().size());
    }

    @Test
    @DisplayName("6. Delete song should throw song not found exceptin if song is not found in the playlist")
    public void deletSong_ShouldThrowAnExceptionIfSongNotFoundInThePlaylist_GivenSong(){
        String id = "1";
        String playlistName = "Gym Songs";
        String featuredArtist = "Nam1#Name2#Name3";
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", featuredArtist));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", featuredArtist));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", featuredArtist));
                
            }
        };

        List<Song> deleteSongs = new ArrayList<Song>(){
            {
                add(new Song("4", "songName4", "genre4", "albumName4", "artist4", featuredArtist));
                
            }
        };
        Playlist playlist = new Playlist(id, playlistName, songs);
        //Act
        assertThrows(SongNotPresentInPlaylistException.class, () -> playlist.deleteSong(deleteSongs));
    }
    
}
