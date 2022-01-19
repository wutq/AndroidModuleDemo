package com.wss.amd.note.designpattern.proxy;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
@Getter
@Setter
public class User {

    private String name;
    private String password;
    private int age;
    private String dec;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
