package com.wss.amd.note.designpattern.decorator;

import com.wss.amd.note.designpattern.decorator.base.Food;

/**
 * Describe：加青菜
 * Created by 吴天强 on 2022/1/18.
 */
public class Vegetables extends Condiment {

    private Food food;

    //这里很关键，需要传入具体的食物（大米、面条），当然也可以传入已经调好的(装饰过)食物
    public Vegetables(Food food) {
        this.food = food;
    }

    @Override
    public String getName() {
        //进行素菜装饰
        return food.getName() + ",加一份青菜";
    }

    @Override
    public double getPrice() {
        //进行价格装饰
        return food.getPrice() + 1;
    }
}
