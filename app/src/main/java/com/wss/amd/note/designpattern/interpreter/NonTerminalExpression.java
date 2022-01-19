package com.wss.amd.note.designpattern.interpreter;

/**
 * Describe：非终结表达式
 * Created by 吴天强 on 2022/1/19.
 */
public class NonTerminalExpression implements AbstractExpression {
    private AbstractExpression expression1;
    private AbstractExpression expression2;

    public NonTerminalExpression(AbstractExpression expression1, AbstractExpression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public boolean interpret(GameUser info) {
        //非对终结符表达式的处理
        return expression1.interpret(info) && expression2.interpret(info);
    }
}

