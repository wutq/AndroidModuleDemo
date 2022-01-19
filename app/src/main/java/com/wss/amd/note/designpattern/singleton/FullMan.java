package com.wss.amd.note.designpattern.singleton;

/**
 * Describe：单例模式 - 饱汉模式
 * Created by 吴天强 on 2022/1/14.
 */
public class FullMan {
    private FullMan() {
    }

    // 和饿汉模式相比，这边不需要先实例化出来，注意这里的 volatile，它是必须的
    private static volatile FullMan instance = null;

    public static FullMan getInstance() {
        if (instance == null) {
            // 加锁
            synchronized (FullMan.class) {
                // 这一次判断也是必须的，不然会有并发问题
                if (instance == null) {
                    instance = new FullMan();
                }
            }
        }
        return instance;
    }
}