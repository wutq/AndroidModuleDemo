package com.wss.amd.note.designpattern.interpreter;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/19.
 */
public class GameEnvironment {
    private AbstractExpression exp;


    public GameEnvironment() {
        //数据初始化
        exp = new NonTerminalExpression(new CampTerminalExpression(new String[]{"齐国", "吴国"}),
                new GradeTerminalExpression(50));
    }

    public void operation(GameUser user) {
        //调用相关表达式类的解释方法
        boolean interpret = exp.interpret(user);
        if (interpret) {
            System.out.println("恭喜来自" + user.getCamp() + "的" + user.getName() + "，您可以参加高级副本《鹊桥仙》");
        } else {
            System.out.println("恭喜来自" + user.getCamp() + "的" + user.getName() + "，您可以参加普通副本《捉泥鳅》");
        }
    }
}
