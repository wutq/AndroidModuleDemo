package com.wss.amd.note.designpattern.facade;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class LunchFood implements Food {
    @Override
    public void make() {
        System.out.println("开始制作午餐啦~~~");
    }
}
