package com.wss.amd.note.designpattern.bridge;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public abstract class Shape {

    protected DrawApi drawApi;

    public Shape(DrawApi drawApi) {
        this.drawApi = drawApi;
    }

    public abstract void draw();
}
