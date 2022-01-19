package com.wss.amd.note.designpattern.responsibility;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：新用户规则
 * Created by 吴天强 on 2022/1/18.
 */
public class NewUserRuleHandler extends RuleHandler {


    @Override
    public void apply(@NotNull People people) {
        if (people.isNewUser()) {
            if (this.getSuccessor() != null) {
                this.getSuccessor().apply(people);
            }
        } else {
            throw new RuntimeException("该活动仅限新用户参与");
        }
    }
}
