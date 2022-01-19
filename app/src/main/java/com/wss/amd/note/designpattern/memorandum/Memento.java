package com.wss.amd.note.designpattern.memorandum;

/**
 * Describe：备忘录
 * Created by 吴天强 on 2022/1/19.
 */
public class Memento {

    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

