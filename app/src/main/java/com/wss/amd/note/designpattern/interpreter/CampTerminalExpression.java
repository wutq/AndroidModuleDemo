package com.wss.amd.note.designpattern.interpreter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class CampTerminalExpression implements AbstractExpression {

    private List<String> list = new ArrayList<>();

    public CampTerminalExpression(String[] resource) {
        this.list.addAll(Arrays.asList(resource));
    }

    @Override
    public boolean interpret(@NotNull GameUser info) {
        //对终结符表达式的处理
        return !list.contains(info.getCamp());
    }
}