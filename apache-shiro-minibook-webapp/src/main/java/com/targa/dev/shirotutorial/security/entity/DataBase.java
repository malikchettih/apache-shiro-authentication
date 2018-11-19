package com.targa.dev.shirotutorial.security.entity;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    static {

        Role adminRole = new Role(1, "admin");
        Role userRole = new Role(2, "user");
        User admin = new User(1, "admin", "12345", adminRole);
        User user = new User(1, "user", "12345", userRole);

        DataBase.roles = new ArrayList<>();
        DataBase.roles.add(adminRole);
        DataBase.roles.add(userRole);

        DataBase.users = new ArrayList<>();
        DataBase.users.add(admin);
        DataBase.users.add(user);

    }

    static List<Role> roles;
    static List<User> users;

    public static List<User> getUsers(){
        return DataBase.users;
    }

    public static List<Role> getRoles(){
        return DataBase.roles;
    }
 }
