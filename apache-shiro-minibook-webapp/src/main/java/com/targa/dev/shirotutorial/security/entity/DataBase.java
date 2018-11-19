package com.targa.dev.shirotutorial.security.entity;

import java.util.ArrayList;
import java.util.List;

public class DataBase {

    static {

        Role adminRole = new Role(1, "admin");
        Role userRole = new Role(2, "user");
        User admin = new User(1, "admin", "12345", adminRole);
        User user1 = new User(1, "user1", "12345", userRole);
        User user2 = new User(1, "user2", "12345", userRole);
        User user3 = new User(1, "user3", "12345", userRole);
        User user4 = new User(1, "user4", "12345", userRole);
        
        DataBase.roles = new ArrayList<>();
        DataBase.roles.add(adminRole);
        DataBase.roles.add(userRole);

        DataBase.users = new ArrayList<>();
        DataBase.users.add(admin);
        DataBase.users.add(user1);
        DataBase.users.add(user2);
        DataBase.users.add(user3);
        DataBase.users.add(user4);

        DataBase.licensedUsers = new ArrayList<>();
        DataBase.licensedUsers.add(admin);
        DataBase.licensedUsers.add(user1);

    }

    static List<Role> roles;
    static List<User> users;
    static List<User> licensedUsers;
    
    public static List<User> getUsers(){
        return DataBase.users;
    }

    public static List<Role> getRoles(){
        return DataBase.roles;
    }

    public static List<User> getLicensedUsers(){
        return DataBase.licensedUsers;
    }

}
