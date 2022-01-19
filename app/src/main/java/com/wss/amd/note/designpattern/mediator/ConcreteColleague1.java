package com.wss.amd.note.designpattern.mediator;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class ConcreteColleague1 extends Colleague {
    @Override
    public void receive() {
        System.out.println("具体同事类1收到请求。");
    }

    @Override
    public void send() {
        System.out.println("具体同事类1发出请求。");
        mediator.relay(this); //请中介者转发
    }
}
