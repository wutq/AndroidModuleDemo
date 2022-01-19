package com.wss.amd.note.designpattern.interpreter;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe： 游戏角色
 * Created by 吴天强 on 2022/1/19.
 */
@Getter
@Setter
public class GameUser {
    private String name;
    private String grader;
    private int grade;
    private String camp;
    private boolean participated;

    public GameUser(String name, int grade, String camp) {
        this.name = name;
        this.grade = grade;
        this.camp = camp;
    }
}
