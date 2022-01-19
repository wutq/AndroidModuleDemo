package com.wss.amd.note.designpattern.decorator.base;

/**
 * Describe：定义普通米饭
 * Created by 吴天强 on 2022/1/18.
 */
public class Rice extends Food {
    @Override
    public String getName() {
        return "白米饭";
    }

    @Override
    public double getPrice() {
        return 6;
    }
}
