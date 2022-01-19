package com.wss.amd.note.designpattern.strategy;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class NoodleStrategy implements Strategy {
    @Override
    public void eat(int count) {
        System.out.println("今天" + count + "人吃面条吧！！！");
    }
}
