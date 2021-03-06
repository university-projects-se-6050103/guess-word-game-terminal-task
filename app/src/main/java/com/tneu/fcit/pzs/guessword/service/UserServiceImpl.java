package com.tneu.fcit.pzs.guessword.service;

import com.tneu.fcit.pzs.guessword.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yp on 02.11.16.
 */
public class UserServiceImpl implements UserService {

    public static final String USER_DB = "USER_DB";
    private static UserServiceImpl instance = new UserServiceImpl();

    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void save(User user) {
        Map<String, User> userMap = all();
        userMap.put(user.getNick(), user);
        try {
            new ObjectOutputStream(new FileOutputStream(USER_DB))
                    .writeObject(userMap);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public Map<String, User> all() {
        Map<String, User> userMap;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(USER_DB));
            userMap = (Map<String, User>) objectInputStream.readObject();
        } catch (Exception e) {
            System.err.println("Error reading USER_DB");
            System.err.println(e);
            userMap = new HashMap<>();
        }
        return userMap;
    }

    @Override
    public User check(String nick, String password) {
        User user = all().get(nick);
        return user != null && user.isPasswordCorrect(password) ? user : null;
    }
}
