package com.wss.amd.note.designpattern.bridge.pen;

import com.wss.amd.note.designpattern.bridge.DrawApi;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class GreenPen implements DrawApi {
    @Override
    public void draw(int radius, int x, int y) {
        System.out.println("使用绿笔画图，radius：" + radius + ",x:" + x + ",y:" + y);
    }
}
