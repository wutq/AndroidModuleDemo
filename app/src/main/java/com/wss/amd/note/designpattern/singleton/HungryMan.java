package com.wss.amd.note.designpattern.singleton;

/**
 * Describe：单例模式 - 饿汉模式
 * Created by 吴天强 on 2022/1/14.
 */
public class HungryMan {
    private HungryMan() {
    }

    // 创建私有静态实例，意味着这个类第一次使用的时候就会进行创建
    private static HungryMan instance = new HungryMan();

    public static HungryMan getInstance() {
        return instance;
    }
}
