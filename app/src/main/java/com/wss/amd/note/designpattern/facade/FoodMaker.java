package com.wss.amd.note.designpattern.facade;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/18.
 */
public class FoodMaker {

    private BreakfastFood breakfastFood;
    private LunchFood lunchFood;
    private DinnerFood dinnerFood;

    public FoodMaker() {
        breakfastFood = new BreakfastFood();
        lunchFood = new LunchFood();
        dinnerFood = new DinnerFood();
    }
    //下面定义一堆方法，具体应该调用什么方法，有这个门面来决定

    public void makeBreakfast() {
        breakfastFood.make();
    }

    public void makeLunch() {
        lunchFood.make();
    }

    public void makeDinner() {
        dinnerFood.make();
    }
}
