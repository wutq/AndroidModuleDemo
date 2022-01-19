package com.wss.amd.note.designpattern.singleton;

/**
 * Describe：单例模式 - 嵌套类
 * Created by 吴天强 on 2022/1/14.
 */
public class NestedClass {
    private NestedClass() {
    }

    //主要是使用了 嵌套类可以访问外部类的静态属性和静态方法 的特性
    private static class Holder {
        private static NestedClass instance = new NestedClass();
    }

    public static NestedClass getInstance() {
        return Holder.instance;
    }
}
