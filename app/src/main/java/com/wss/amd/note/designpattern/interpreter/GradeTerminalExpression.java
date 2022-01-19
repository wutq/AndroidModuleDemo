package com.wss.amd.note.designpattern.interpreter;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class GradeTerminalExpression implements AbstractExpression {

    private int grade;

    public GradeTerminalExpression(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean interpret(@NotNull GameUser info) {
        //对终结符表达式的处理
        return info.getGrade() < grade;
    }
}