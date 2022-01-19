package com.wss.amd.note.designpattern.decorator;

import com.wss.amd.note.designpattern.decorator.base.Food;

/**
 * Describe：土豆烧牛腩浇头
 * Created by 吴天强 on 2022/1/18.
 */
public class PotatoesBurnFireSirloin extends Condiment {

    private Food food;

    //这里很关键，需要传入具体的食物（大米、面条），当然也可以传入已经调好的(装饰过)食物
    public PotatoesBurnFireSirloin(Food food) {
        this.food = food;
    }

    @Override
    public String getName() {
        //进行土豆烧牛腩的装饰
        return food.getName() + ",加土豆烧牛腩";
    }

    @Override
    public double getPrice() {
        //进行价格装饰
        return food.getPrice() + 6;
    }
}
