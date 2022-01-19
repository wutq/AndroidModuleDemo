package com.wss.amd.note.designpattern.memorandum;

/**
 * Describe：管理者
 * Created by 吴天强 on 2022/1/19.
 */
public class Caretaker {

    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
