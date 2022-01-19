package com.wss.amd.note.designpattern.adapter.obj;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class SteamedBunsAdapter implements Buns {

    private Dumplings dumplings;

    public SteamedBunsAdapter(Dumplings dumplings) {
        this.dumplings = dumplings;
    }

    @Override
    public void bunsFilling() {
        //包子的内部实现其实是饺子馅儿
        dumplings.dumplingsFilling();
    }

    @Override
    public void steam() {
        dumplings.steam();
    }
}
