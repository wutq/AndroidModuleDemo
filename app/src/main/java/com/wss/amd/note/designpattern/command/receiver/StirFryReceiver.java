package com.wss.amd.note.designpattern.command.receiver;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class StirFryReceiver implements CookReceiver {
    @Override
    public void cooking() {
        System.out.println("炒菜厨子开始炒菜了~");
    }
}
