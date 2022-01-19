package com.wss.amd.note.designpattern.decorator.base;

/**
 * Describe：定义普通面条
 * Created by 吴天强 on 2022/1/18.
 */
public class Noodle extends Food {
    @Override
    public String getName() {
        return "清汤面";
    }

    @Override
    public double getPrice() {
        return 5;
    }
}
