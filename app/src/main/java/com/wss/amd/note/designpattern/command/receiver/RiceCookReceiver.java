package com.wss.amd.note.designpattern.command.receiver;

/**
 * Describe：米饭厨子
 * Created by 吴天强 on 2022/1/19.
 */
public class RiceCookReceiver implements CookReceiver {


    @Override
    public void cooking() {
        System.out.println("米饭厨子开始做米饭~");
    }
}
