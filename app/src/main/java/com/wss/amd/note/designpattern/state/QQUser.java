package com.wss.amd.note.designpattern.state;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：QQ用户
 * Created by 吴天强 on 2022/1/18.
 */
@Getter
@Setter
public class QQUser {
    private State state;
    private String name;

    public QQUser(String name) {
        this.name = name;
    }
}
