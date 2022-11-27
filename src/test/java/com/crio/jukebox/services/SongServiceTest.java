package com.crio.jukebox.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Song Service Test")
@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    private ISongRepository songRepositorMock;

    @Mock
    private IPlaylistService playlistServiceMock;

    @InjectMocks
    private SongServiceImpl songServiceImpl;

    
    @Test
    @DisplayName("Play Song Should Return Playlist Is Empty When There Is No Song In The Playlist")
    public void playSong_ShouldReturnPlaylistIsEmptyException(){
         //Arrange
         Playlist playlist = new Playlist("1", "playlistName", new ArrayList<>());
         User user = new User("1", "name", Arrays.asList(playlist));
         user.setActivePlaylist(playlist);
 
         when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
 
         //Act and Assert
         assertThrows(EmptyPlaylistException.class, () -> songServiceImpl.playSong("1", "1"));
         verify(userRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Play Song Should Return Song Not Available in The Playlist when songId is not available in the playlist")
    public void playSong_ShouldReturnSongNotAvailableException(){

        //Arrange
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };

        Playlist playlist = new Playlist("1", "playlistName", songs);
        User user = new User("1", "username", Arrays.asList(playlist));
        user.setActivePlaylist(playlist);

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        //act and assert
        assertThrows(SongNotAvailableException.class, () -> songServiceImpl.playSong("1", "4") );
        verify(userRepositoryMock, times(1)).findById(anyString());

    }
    
    @Test
    @DisplayName("Play Song should return the next song from the playlist when next is passed")
    public void playSong_ShouldReturnTheNextSongOfThePlaylist(){

        //Arrange
        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtists"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));
                add(new Song("1", "songName3", "genre3", "albumName3", "artist3", "featuredArtists"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));
        user.setActivePlaylist(playlist);
        user.setCurrentSong(songsPresentInPlaylist.get(0));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        String expectedOutput = "Current Song Playing" + "\nSong - songName2" + "\nAlbum - albumName2" +
        "\nArtists - featuredArtists";

        //Act 
        String actualOutput = songServiceImpl.playSong("1", "next");

        //assert
        assertEquals(expectedOutput, actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
    }   

    //Make the current song in the playlist to last song from the playlist of songs before running it
    @Test
    @DisplayName("Play Song should return back to the first song of the playlist when current song is the last song")
    public void playSong_ShouldReturnTheNextSongOfThePlaylist_v2(){

        //Arrange
        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtists"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));
                add(new Song("1", "songName3", "genre3", "albumName3", "artist3", "featuredArtists"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));
        user.setActivePlaylist(playlist);
        user.setCurrentSong(songsPresentInPlaylist.get(2));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        String expectedOutput = "Current Song Playing" + "\nSong - songName1" + "\nAlbum - albumName1" +
        "\nArtists - featuredArtists";

        //Act 
        String actualOutput = songServiceImpl.playSong("1","next");

        //assert
        assertEquals(expectedOutput, actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
    }

    
    @Test
    @DisplayName("Play Song should return the previous song from the playlist when back is passed")
    public void playSong_ShouldReturnThePreviousSongOfThePlaylist(){

        //Arrange
        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtists"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));
                add(new Song("1", "songName3", "genre3", "albumName3", "artist3", "featuredArtists"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));
        user.setActivePlaylist(playlist);
        user.setCurrentSong(songsPresentInPlaylist.get(1));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        String expectedOutput = "Current Song Playing" + "\nSong - songName1" + "\nAlbum - albumName1" +
        "\nArtists - featuredArtists";

        //Act 
        String actualOutput = songServiceImpl.playSong("1", "back");

        //assert
        assertEquals(expectedOutput, actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Play Song should return the specific song from the playlist when songId is passed")
    public void playSong_ShouldReturnTheaSpecificSongOfThePlaylist(){

        //Arrange
        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtists"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));
                add(new Song("1", "songName3", "genre3", "albumName3", "artist3", "featuredArtists"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));
        user.setActivePlaylist(playlist);

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        String expectedOutput = "Current Song Playing" + "\nSong - songName1" + "\nAlbum - albumName1" +
        "\nArtists - featuredArtists";

        //Act 
        String actualOutput = songServiceImpl.playSong("1", "3");

        //assert
        assertEquals(expectedOutput, actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
    
    }
}
