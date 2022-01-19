package com.wss.amd.note.designpattern.adapter.classs;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class Dc5VAdapter extends Ac220V implements Dc5V {
    @Override
    public int dc5v() {
        int ac220v = outPut220();
        return ac220v / 44;
    }
}
