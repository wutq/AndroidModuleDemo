package com.wss.amd.note.designpattern.mediator;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class ConcreteColleague2 extends Colleague {

    @Override
    public void receive() {
        System.out.println("具体同事类2收到请求。");
    }

    @Override
    public void send() {
        System.out.println("具体同事类2发出请求。");
        mediator.relay(this); //请中介者转发
    }
}
