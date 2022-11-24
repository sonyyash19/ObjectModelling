package com.crio.jukebox.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.crio.jukebox.dto.SongDto;
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

    // @Test
    // @DisplayName("Load Songs should load the sons successfully")
    // public void loadSongs(){
    //     //Arrange
    //     String filename = "songs.csv";
    //     List<Song> expectedSongs = new ArrayList<Song>(){
    //         {
    //             add(new Song("1","South of the Border","Pop","No.6 Collaborations Project","Ed Sheeran","Ed Sheeran#Cardi.B#Camilla Cabello"));
    //             add(new Song("2","I Dont'Care,Pop","No.6 Collaborations Project","Ed Sheeran","Ed Sheeran#Justin Bieber"));
    //             add(new Song("3","Remember The Name","Pop","No.6 Collaborations Project","Ed Sheeran","Ed Sheeran#Eminem#50Cent"));
    //             add(new Song("4","Way To Break My Heart","Pop","No.6 Collaborations Project","Ed Sheeran","Ed Sheeran#Skrillex"));
    //             add(new Song("5","Cross Me","Pop,No.6 Collaborations Project","Ed Sheeran","Ed Sheeran#Chance The Rapper#PnB Rock"));
    //             add(new Song("6","Give Life Back To Music","Electronic Dance Music","Random Access Memories","Daft Punk","Daft Punk#Nile Rodgers"));
    //             add(new Song("7","Instant Crush","Electronic Dance Music","Random Access Memories","Daft Punk","Daft Punk#Julian Casablancas"));
    //             add(new Song("8","Get Lucky","Electronic Dance Music","Random Access Memories","Daft Punk","Daft Punk#Nile Rodgers#Pharrell Williams"));
    //             add(new Song("9","Lose Yourself To Dance","Electronic Dance Music","Random Access Memories","Daft Punk","Daft Punk#Nile Rodgers#Pharrell William"));
    //             add(new Song("10","Giorgio by Moroder","Electronic Dance Music","Random Access Memories","Daft Punk","Daft Punk#Giorgio Moroder"));
    //             add(new Song("11","Something In the Way,Rock","Rock","Nevermind","Nirvana","Nirvana"));
    //             add(new Song("12","Lithium","Rock","Nevermind","Nirvana","Nirvana"));
    //             add(new Song("13","Come as You Are","Rock","Nevermind","Nirvana","Nirvana"));
    //             add(new Song("14","Stay Away","Rock","Nevermind","Nirvana","Nirvana"));
    //             add(new Song("15","Lounge Act","Rock","Nevermind","Nirvana","Nirvana"));
    //             add(new Song("16","BLOOD.","Hip-Hop","DAMN.","Kendrick Lamar","Kendrick Lamar"));
    //             add(new Song("17","GOD.","Hip-Hop","DAMN.","Kendrick Lamar","Kendrick Lamar"));
    //             add(new Song("18","PRIDE.","Hip-Hop","DAMN.","Kendrick Lamar","Kendrick Lamar"));
    //             add(new Song("19","YAH.","Hip-Hop","DAMN.","Kendrick Lamar","Kendrick Lamar"));
    //             add(new Song("20","DUCKWORTH.","Hip-Hop","DAMN.","Kendrick Lamar","Kendrick Lamar"));


    //         }
    //     };
    // }

    /*
    @Test
    @DisplayName("Play Song Should Return Playlist Is Empty When There Is No Song In The Playlist")
    public void playSong_ShouldReturnPlaylistIsEmptyException(){
         //Arrange
         Playlist playlist = new Playlist("1", "playlistName", new ArrayList<>());
         User user = new User("1", "name", Arrays.asList(playlist));
 
         when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
         // when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
 
         //Act and Assert
         assertThrows(EmptyPlaylistException.class, () -> songServiceImpl.playSong("1", "1", "Play"));
         verify(userRepositoryMock, times(1)).findById(anyString());
         // verify(playlistRepositoryMock, times(1)).findById(anyString());
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

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        //act and assert
        assertThrows(SongNotAvailableException.class, () -> songServiceImpl.playSong("1", "1", "10") );
        verify(userRepositoryMock, times(1)).findById(anyString());

    }
    
    @Test
    @DisplayName("Play Song Should Return the first song of the playlist when play is passed")
    public void playSong_ShouldReturnFirstSongOfPlaylist(){

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
        SongDto songFromPlaylist = new SongDto("Current Song Playing.", "songName1", "albumName1", "featuredArtists");

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistServiceMock.playPlaylist(anyString(), anyString())).thenReturn(songFromPlaylist);
        
        //Act
        SongDto song = songServiceImpl.playSong("1", "1", "play");

        assertEquals(songFromPlaylist.toString(), song.toString());
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

        // Song currentSong = new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists");

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        SongDto expectedOutput = new SongDto("Current Song Playing.", "songName2", "albumName2", "featuredArtists");

        //Act 
        SongDto actualOutput = songServiceImpl.playSong("1", "1", "next");

        //assert
        assertEquals(expectedOutput.toString(), actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
    }   */

    //Make the current song in the playlist to last song from the playlist of songs before running it
    // @Test
    // @DisplayName("Play Song should return back to the first song of the playlist when current song is the last song")
    // public void playSong_ShouldReturnTheNextSongOfThePlaylist_v2(){

    //     //Arrange
    //     List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
    //         {
    //             add(new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtists"));
    //             add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));
    //             add(new Song("1", "songName3", "genre3", "albumName3", "artist3", "featuredArtists"));
                
    //         }
    //     };
    //     Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
    //     User user = new User("1", "name", Arrays.asList(playlist));

    //     // Song currentSong = new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists");

    //     when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

    //     SongDto expectedOutput = new SongDto("Current Song Playing.", "songName1", "albumName1", "featuredArtists");

    //     //Act 
    //     SongDto actualOutput = songServiceImpl.playSong("1", "1", "next");

    //     //assert
    //     assertEquals(expectedOutput.toString(), actualOutput.toString());
    //     verify(userRepositoryMock, times(1)).findById(anyString());
    // }

    /* 
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

        // Song currentSong = new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists");

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        SongDto expectedOutput = new SongDto("Current Song Playing.", "songName3", "albumName3", "featuredArtists");

        //Act 
        SongDto actualOutput = songServiceImpl.playSong("1", "1", "back");

        //assert
        assertEquals(expectedOutput.toString(), actualOutput.toString());
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

        // Song currentSong = new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists");

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));

        SongDto expectedOutput = new SongDto("Current Song Playing.", "songName2", "albumName2", "featuredArtists");

        //Act 
        SongDto actualOutput = songServiceImpl.playSong("1", "1", "2");

        //assert
        assertEquals(expectedOutput.toString(), actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
    
    }
    */
    @Test
    @DisplayName("Play Song should return the previous song from the playlist when back is passed")
    public void playSongV1_ShouldReturnThePreviousSongOfThePlaylist(){

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
        user.setCurrentSong(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));

        // Song currentSong = new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists");

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        // when(user.getCurrentSong()).thenReturn(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtists"));

        User userV1 = new User("1", "name", Arrays.asList(playlist));
        userV1.setActivePlaylist(playlist);
        userV1.setCurrentSong(new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtists"));
        when(userRepositoryMock.save(any(User.class))).thenReturn(userV1);
        SongDto expectedOutput = new SongDto("Current Song Playing.", "songName1", "albumName1", "featuredArtists");

        //Act 
        SongDto actualOutput = songServiceImpl.playSong("1", "back");

        //assert
        assertEquals(expectedOutput.toString(), actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }
}
