package com.wss.amd.note.designpattern.bridge;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class Circle extends Shape {
    private int radius;

    public Circle(int radius, DrawApi drawApi) {
        super(drawApi);
        this.radius = radius;
    }

    @Override
    public void draw() {
        drawApi.draw(radius, 0, 0);
    }
}
