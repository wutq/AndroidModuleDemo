package com.wss.amd.note.designpattern.template;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class TemplateMethod extends AbsTemplateMethod {
    @Override
    protected void ready() {
        System.out.println("子类必须实现的抽象方法");
    }

    @Override
    protected void end() {
        super.end();
        System.out.println("子类可覆写的父类已经实现的方法");
    }
}
