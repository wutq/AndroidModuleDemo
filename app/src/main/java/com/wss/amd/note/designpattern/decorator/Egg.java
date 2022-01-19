package com.wss.amd.note.designpattern.decorator;

import com.wss.amd.note.designpattern.decorator.base.Food;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class Egg extends Condiment {
    private Food food;

    //这里很关键，需要传入具体的食物（大米、面条），当然也可以传入已经调好的(装饰过)食物
    public Egg(Food food) {
        this.food = food;
    }

    @Override
    public String getName() {
        //加个鸡蛋装饰
        return food.getName() + ",加个鸡蛋";
    }

    @Override
    public double getPrice() {
        //价格装饰
        return food.getPrice() + 2;
    }
}
