package com.wss.amd.note.designpattern.responsibility;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public abstract class RuleHandler {

    private RuleHandler successor;


    public RuleHandler getSuccessor() {
        return successor;
    }

    public void setSuccessor(RuleHandler successor) {
        this.successor = successor;
    }


    public abstract void apply(People people);
}
