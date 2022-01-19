package com.wss.amd.note.designpattern.flyweight;

import java.util.WeakHashMap;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class Flyweight {

    private WeakHashMap<Integer, User> weakHashMap = new WeakHashMap<>();

    public User getUser(int index) {
        User user = weakHashMap.get(index);
        if (user == null) {
            user = new User();
            weakHashMap.put(index, user);
        }
        return user;
    }
}
