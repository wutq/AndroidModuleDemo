package com.wss.amd.note.designpattern.state;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

/**
 * Describe：上线
 * Created by 吴天强 on 2022/1/18.
 */
public class OnLineState implements State {
    @Override
    public void doAction(@NotNull QQUser qqUser) {
        System.out.println("此时QQ用户在线了~");
        qqUser.setState(this);
    }

    @NonNull
    @Override
    public String toString() {
        return "OnLineState";
    }
}
