package com.wss.amd.note.designpattern.memorandum;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：发起人
 * Created by 吴天强 on 2022/1/19.
 */
public class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Memento createMemento() {
        return new Memento(this.getState());
    }

    public void restoreMemento(@NotNull Memento m) {
        this.setState(m.getState());
    }
}
