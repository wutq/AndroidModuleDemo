package com.wss.amd.note.designpattern.observer;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public abstract class Observer {

    protected Subject subject;

    public abstract void update();
}
