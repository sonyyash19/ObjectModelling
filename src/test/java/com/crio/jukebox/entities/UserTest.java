package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User Test")
public class UserTest {

    @Test
    @DisplayName("1. Check if playlist exist should throw playlist not found exception if playlist is not found")
    public void checkIfPlaylistExist_ShouldThrowPlayListNotFoundException_GivenPlaylist(){

        //Arrange
        String id = "1";
        String name = "Yash";
        List<Playlist> playlists = new ArrayList<Playlist>(){
            {
                add (new Playlist("1","Gym Songs"));
                add (new Playlist("2","Pop Songs"));
            }
        };

        Playlist playlist = new Playlist("3","Atif Songs");
        User user = new User(id, name, playlists);

        //Act 
        Assertions.assertThrows(PlaylistNotFoundException.class,() -> user.checkIfPlaylistExists(playlist));
    }

    @Test
    @DisplayName("2. Check if playlist exist should return a true if playlist is found")
    public void checkIfPlaylistExist_ShouldReturnTrue_GivenPlaylist(){

        //Arrange
        String id = "1";
        String name = "Yash";
        List<Playlist> playlists = new ArrayList<Playlist>(){
            {
                add (new Playlist("1","Gym Songs"));
                add (new Playlist("2","Pop Songs"));
            }
        };

        Playlist playlist = new Playlist("1","Gym Songs");
        User user = new User(id, name, playlists);

        //Act 
        Assertions.assertTrue(user.checkIfPlaylistExists(playlist));
    }

    @Test
    @DisplayName("3. If playlist doesnot exist delete playlist should throw playlist not found exception")
    public void deletePlaylist_ShouldThrowPlayListNotFoundException(){

        //Arrange
        String id = "1";
        String name = "Yash";
        List<Playlist> playlists = new ArrayList<Playlist>(){
            {
                add (new Playlist("1","Gym Songs"));
                add (new Playlist("2","Pop Songs"));
            }
        };

        Playlist playlist = new Playlist("3","kailash Songs");
        User user = new User(id, name, playlists);

        //Act 
        Assertions.assertThrows(PlaylistNotFoundException.class,() ->  user.deletePlaylist(playlist));
    }

    @Test
    @DisplayName("4. Playlist Should be deleted if found")
    public void deletePlaylist_ShouldDeletePlaylist_GivenPlaylist(){

        //Arrange
        String id = "1";
        String name = "Yash";
        List<Playlist> playlists = new ArrayList<Playlist>(){
            {
                add (new Playlist("1","Gym Songs"));
                add (new Playlist("2","Pop Songs"));
            }
        };

        Playlist playlist = new Playlist("1","Gym Songs");
        User user = new User(id, name, playlists);
        user.deletePlaylist(playlist);
        int expectedOutput = 1;

        //Act 
        Assertions.assertEquals(expectedOutput, user.getPlaylist().size());
    }
    
}
