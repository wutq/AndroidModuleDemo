package com.wss.amd.note.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class Subject {
    private List<Observer> observerList = new ArrayList<>();

    private int state;

    public void attach(Observer observer) {
        observerList.add(observer);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        //数据已变更，通知更新
        notifyAllObservers();
    }


    private void notifyAllObservers() {
        for (Observer observer : observerList) {
            observer.update();
        }
    }
}


