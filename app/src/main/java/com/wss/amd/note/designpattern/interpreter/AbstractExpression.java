package com.wss.amd.note.designpattern.interpreter;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public interface AbstractExpression {
    boolean interpret(GameUser gameUser);
}
