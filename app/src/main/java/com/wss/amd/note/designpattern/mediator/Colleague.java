package com.wss.amd.note.designpattern.mediator;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public abstract class Colleague {
    protected Mediator mediator;

    public void setMedium(Mediator mediator) {
        this.mediator = mediator;
    }

    public abstract void receive();

    public abstract void send();
}
