package com.wss.amd.note.designpattern.responsibility;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：地理位置规则
 * Created by 吴天强 on 2022/1/18.
 */
public class LocationRuleHandler extends RuleHandler {
    private static final String LOCATION = "上海";

    @Override
    public void apply(@NotNull People people) {
        if (LOCATION.equalsIgnoreCase(people.getLocation())) {
            if (this.getSuccessor() != null) {
                this.getSuccessor().apply(people);
            }
        } else {
            throw new RuntimeException("该活动仅限上海地区参与");
        }
    }
}
