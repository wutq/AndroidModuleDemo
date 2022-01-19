package com.wss.amd.note.designpattern.strategy;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class NoonEatWhat {

    private Strategy strategy;

    public NoonEatWhat(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeEat(int cont) {
        strategy.eat(cont);
    }
}
