package com.wss.amd.note.designpattern.command.receiver;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class NoodleCookeReceiver implements CookReceiver {
    @Override
    public void cooking() {
        System.out.println("面条厨子开始做面条了~~~");
    }
}
