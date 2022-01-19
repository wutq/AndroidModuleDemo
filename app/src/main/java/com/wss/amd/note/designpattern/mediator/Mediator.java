package com.wss.amd.note.designpattern.mediator;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public interface Mediator {
    void register(Colleague colleague);

    void relay(Colleague cl); //转发
}
