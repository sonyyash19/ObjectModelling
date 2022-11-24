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
import com.crio.jukebox.dto.ModifyPlaylistDto;
import com.crio.jukebox.dto.SongDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongAlreadyPresentInPlaylistException;
import com.crio.jukebox.exceptions.SongNotAvailableException;
import com.crio.jukebox.exceptions.SongNotPresentInPlaylistException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Playlist Service Test")
@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private IPlaylistRepository playlistRepositoryMock;

    @Mock
    private ISongRepository songRepositorMock;

    @Mock
    private IUserRepository userRepositoryMock;

    @InjectMocks
    private PlaylistServiceImpl playlistServiceImpl;

    @Test
    @DisplayName("1. Song Not Available exception from the create playlist")
    public void createPlaylist_ShouldReturnSongNotAvailableInThePool(){

        // Arrange
        String userId = "1";
        String playlistName = "Playlist";
        List<String> songId = Arrays.asList(new String[] {"1", "2", "3"});

        when(songRepositorMock.findById(anyString())).thenReturn(Optional.empty());
        

        //Assert
        assertThrows(SongNotAvailableException.class, () -> playlistServiceImpl.create(userId, playlistName, songId));
        verify(songRepositorMock, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("2. Create playlist with multiple songs should return playlist id.")
    public void createPlaylistWithMultipleSongs_ShouldReturnPlaylistId(){

        // Arrange
        String expectedId = "2";
        List<String> songId = Arrays.asList(new String[] {"1", "2", "3"}); 
                
        List<Song> songs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        List<Playlist> playlists = new ArrayList<Playlist>(){
            {
                add(new Playlist("1", "playlistName1", songs));

            }
        };

        Playlist newplaylist = new Playlist("2", "playlistName", songs);

        User user = new User("1", "name", playlists);
        user.addPlaylist(newplaylist);

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(songRepositorMock.findById(anyString())).thenReturn(Optional.of(songs.get(0)));
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(newplaylist);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);
        

        //Act
        String actualplaylistId = playlistServiceImpl.create("1", "playlistName2",songId);

        //Assert
        assertEquals(expectedId, actualplaylistId);
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(songRepositorMock, times(3)).findById(anyString());
        verify(playlistRepositoryMock, times(1)).save(any(Playlist.class));
        verify(userRepositoryMock, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("3. Delete playlist should throw playlist not found exception.")
    public void deletePlaylist_ShouldReturnPlaylistNotFound(){
        //Arrange
        User user = new User("1", "Name");
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.empty());

        //Act and assert
        assertThrows(PlaylistNotFoundException.class, () -> playlistServiceImpl.delete("1", "1"));
        verify(playlistRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("4. Delete playlist should delete the playlist successfully.")
    public void deletePlaylist_ShouldReturnDeletedSuccessfully(){
        //Arrange
        List<Playlist> playlists = new ArrayList<Playlist>(){
            {
                add(new Playlist("1", "playlistName"));
                add(new Playlist("2", "playlistName2"));
            }
        };
        User user = new User("1", "Name", playlists);
        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlists.get(0)), Optional.of(playlists.get(1)));

        User userNew = new User("1","Name",Arrays.asList(playlists.get(0)));
        when(userRepositoryMock.save(any(User.class))).thenReturn(userNew);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(playlists.get(0));

        //Act
        String actualOuput = playlistServiceImpl.delete("1", "2");

        //Assert
        assertEquals("Deleted Successfully", actualOuput);
        verify(playlistRepositoryMock, times(1)).findById(anyString());
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(playlistRepositoryMock, times(1)).save(any(Playlist.class));

    }

    @Test
    @DisplayName("5. Add Song to playlist should throw song already present exception")
    public void  addSongToPlaylist_ShouldReturnSongAlreadyPresentExcaption(){

        //Arrange
        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));

        List<String> songId = Arrays.asList(new String[] {"1", "5", "4"});
        Song songInRepo = new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist");
        when(songRepositorMock.findById(anyString())).thenReturn(Optional.of(songInRepo));

        //act and assert
        assertThrows(SongAlreadyPresentInPlaylistException.class, () -> playlistServiceImpl.addSongToPlaylist("1", "1", songId));
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(playlistRepositoryMock, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("6. Add Song to playlist should return song not available exception")
    public void  addSongToPlaylist_ShouldReturnSongNotAvailableException(){

        //Arrange
        List<String> songId = Arrays.asList(new String[] {"1", "5", "6"}); 

        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));

        when(songRepositorMock.findById(anyString())).thenReturn(Optional.empty());


        //act and assert
        assertThrows(SongNotAvailableException.class, () -> playlistServiceImpl.addSongToPlaylist("1", "1", songId));
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(playlistRepositoryMock, times(1)).findById(anyString());
        verify(songRepositorMock, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("7. Add Song to playlist should return ModifyPlaylistDto")
    public void  addSongToPlaylist_ShouldReturnModifyPlaylistDto(){

        //Arrange
        List<String> songId = Arrays.asList(new String[] {"4", "5", "6"}); 

        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));

        List<Song> newSongs = new ArrayList<Song>(){
            {
                add(new Song("4", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("5", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("6", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        List<Song> allSongs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                add(new Song("4", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("5", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("6", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        Playlist expectedPlaylist = new Playlist("1", "playlistName", allSongs);

        for(Song song: newSongs){
            when(songRepositorMock.findById(anyString())).thenReturn(Optional.of(song));
        }
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(expectedPlaylist);
        ModifyPlaylistDto expectedOutput = new ModifyPlaylistDto("1", "playlistName", songId);


        //Act
        ModifyPlaylistDto actualOutput = playlistServiceImpl.addSongToPlaylist("1", "1", songId);


        //act and assert
        assertEquals(expectedOutput.toString(), actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(playlistRepositoryMock, times(1)).findById(anyString());
        verify(songRepositorMock, times(3)).findById(anyString());
        verify(playlistRepositoryMock,times(1)).save(any(Playlist.class));

    }

    @Test
    @DisplayName("8. Delete Song from playlist should throw song not present in playlist exception")
    public void  deleteSongFromPlaylist_ShouldReturnSongNotPresentInPlaylistException(){

        //Arrange
        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist)); 

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));

        List<Song> test = new ArrayList<Song>(){
            {
                add(new Song("5", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("4", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                
            }
        };
        for(Song song: test){
            when(songRepositorMock.findById(anyString())).thenReturn(Optional.of(song));
        }
        List<String> songId = Arrays.asList(new String[] {"5", "4"}); 

        //act and assert
        assertThrows(SongNotPresentInPlaylistException.class, () -> playlistServiceImpl.deleteSongFromPlaylist("1", "1", songId));
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(playlistRepositoryMock, times(1)).findById(anyString());
        verify(songRepositorMock, times(2)).findById(anyString());

    }

    @Test
    @DisplayName("9. Delete Song from playlist should return ModifyPlaylistDto")
    public void  deleteSongFromPlaylist_ShouldReturnModifyPlaylistDto(){

        //Arrange
        List<String> songId = Arrays.asList(new String[] {"2"}); 

        List<Song> songsPresentInPlaylist = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("2", "songName2", "genre2", "albumName2", "artist2", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
                
            }
        };
        Playlist playlist = new Playlist("1", "playlistName", songsPresentInPlaylist);
        User user = new User("1", "name", Arrays.asList(playlist));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
        List<Song> allSongs = new ArrayList<Song>(){
            {
                add(new Song("1", "songName1", "genre1", "albumName1", "artist1", "featuredArtist"));
                add(new Song("3", "songName3", "genre3", "albumName3", "artist3", "featuredArtist"));
            }
        };
        Playlist expectedPlaylist = new Playlist("1", "playlistName", allSongs);
        when(playlistRepositoryMock.save(any(Playlist.class))).thenReturn(expectedPlaylist);

        // Song song3 = new Song("3", "songName2", "genre2", "albumName2", "artist2", "featuredArtist");
        Song song2 = new Song("2", "songName1", "genre1", "albumName1", "artist1", "featuredArtist");

        when(songRepositorMock.findById(anyString())).thenReturn(Optional.of(song2));
        // when(songRepositorMock.findById(anyString())).thenReturn(Optional.of(song3));


        ModifyPlaylistDto expectedOutput = new ModifyPlaylistDto("1", "playlistName", songId);

        //Act
        ModifyPlaylistDto actualOutput = playlistServiceImpl.deleteSongFromPlaylist("1", "1", songId);


        //act and assert
        assertEquals(expectedOutput.toString(), actualOutput.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
        verify(playlistRepositoryMock, times(1)).findById(anyString());
        verify(playlistRepositoryMock,times(1)).save(any(Playlist.class));
        verify(songRepositorMock, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("10. Playlist should throw playlist is emplty exception")
    public void playPlaylist_ShouldThrowPlaylistIsEmptyException(){

        //Arrange
        Playlist playlist = new Playlist("1", "playlistName", new ArrayList<>());
        User user = new User("1", "name", Arrays.asList(playlist));

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        // when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));

        //Act and Assert
        assertThrows(EmptyPlaylistException.class, () -> playlistServiceImpl.playPlaylist("1", "1"));
        verify(userRepositoryMock, times(1)).findById(anyString());
        // verify(playlistRepositoryMock, times(1)).findById(anyString());

    }

    @Test
    @DisplayName("11. Playlist should return 1st Song of the playlist")
    public void playPlaylist_ShouldReturnFirstSongOFthePlaylist(){

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

        when(userRepositoryMock.findById(anyString())).thenReturn(Optional.of(user));
        // when(playlistRepositoryMock.findById(anyString())).thenReturn(Optional.of(playlist));
        // Song expectedSong = new Song("3", "songName1", "genre1", "albumName1", "artist1", "featuredArtist");
        SongDto expectedSong = new SongDto("Current Song Playing.", "songName1", "albumName1", "featuredArtists");
        //Act
        SongDto song = playlistServiceImpl.playPlaylist("1", "1");

        assertEquals(expectedSong.toString(), song.toString());
        verify(userRepositoryMock, times(1)).findById(anyString());
        // verify(playlistRepositoryMock, times(1)).findById(anyString());

    }

    

    

}
