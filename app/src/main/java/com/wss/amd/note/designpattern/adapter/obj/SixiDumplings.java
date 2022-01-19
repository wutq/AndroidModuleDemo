package com.wss.amd.note.designpattern.adapter.obj;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class SixiDumplings implements Dumplings {
    @Override
    public void dumplingsFilling() {
        System.out.println("四喜水饺馅儿");
    }

    @Override
    public void steam() {
        System.out.println("上锅蒸");
    }
}
