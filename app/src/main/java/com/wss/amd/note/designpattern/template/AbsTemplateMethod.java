package com.wss.amd.note.designpattern.template;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public abstract class AbsTemplateMethod {


    public void templateMethod() {
        init();
        ready();
        end();
    }

    protected void init() {
        System.out.println("基类已经实现init");
    }

    protected abstract void ready();

    protected void end() {
    }


}
