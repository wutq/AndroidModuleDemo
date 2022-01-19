package com.wss.amd.note.designpattern.bridge;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class Rectangle extends Shape {
    private int x, y;

    public Rectangle(int x, int y, DrawApi drawApi) {
        super(drawApi);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        drawApi.draw(0, x, y);
    }
}
