package com.crio.jukebox.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.repositories.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("User Service Test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    @DisplayName("Create user should create the user.")
    public void create_ShouldReturnUser(){

        //Arrange
        User expectedUser = new User("1", "Yash");
        when(userRepositoryMock.save(any(User.class))).thenReturn(expectedUser);
        String expectedOutput = "1 Yash";

        // Act
        String actualUser = userServiceImpl.create("Yash");

        // Assert
        assertEquals(expectedOutput, actualUser);
        verify(userRepositoryMock, times(1)).save(any(User.class));
        
    }
    
}
