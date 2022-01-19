package com.wss.amd.note.designpattern.state;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

/**
 * Describe：隐身
 * Created by 吴天强 on 2022/1/18.
 */
public class InvisibleState implements State {
    @Override
    public void doAction(@NotNull QQUser qqUser) {
        System.out.println("此时QQ用户隐身啦~");
        qqUser.setState(this);

    }

    @NonNull
    @Override
    public String toString() {
        return "InvisibleState";
    }
}
