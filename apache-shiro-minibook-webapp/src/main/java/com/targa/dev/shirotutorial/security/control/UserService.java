package com.targa.dev.shirotutorial.security.control;

import com.targa.dev.shirotutorial.security.entity.DataBase;
import com.targa.dev.shirotutorial.security.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    public List<User> findAll(){
        return DataBase.getUsers();
    }

    public User findById(long id){
        Optional<User> matchingUser = DataBase.getUsers().stream().filter(
            user -> user.getId() == id
        ).findFirst();

        return matchingUser.orElse(null);
    }

    public User findByUsername(String username){
        Optional<User> matchingUser = DataBase.getUsers().stream().filter(
                user -> user.getUsername().equals(username)
        ).findFirst();

        return matchingUser.orElse(null);
    }

    public User save(User user) {
        return user;
    }

    public void delete(long id) {
    }

}
