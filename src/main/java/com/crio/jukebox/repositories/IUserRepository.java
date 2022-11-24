package com.crio.jukebox.repositories;

import java.util.Optional;
import com.crio.jukebox.entities.User;

public interface IUserRepository extends CRUDRepository<User, String>{
    public Optional<User> findByName(String name);
}
