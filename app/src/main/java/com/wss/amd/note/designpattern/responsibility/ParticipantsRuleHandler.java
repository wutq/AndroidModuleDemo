package com.wss.amd.note.designpattern.responsibility;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：参与人数规则
 * Created by 吴天强 on 2022/1/18.
 */
public class ParticipantsRuleHandler extends RuleHandler {
    private static final int MAX_PARTICIPANTS = 10;

    @Override
    public void apply(@NotNull People people) {
        if (people.getParticipants() <= MAX_PARTICIPANTS) {
            if (this.getSuccessor() != null) {
                this.getSuccessor().apply(people);
            }
        } else {
            throw new RuntimeException("该活动单次仅限" + MAX_PARTICIPANTS + "人参数");
        }
    }
}
