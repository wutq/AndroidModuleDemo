package com.wss.module.market.main.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.market.bean.MarketInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：首页Presenter
 * Created by 吴天强 on 2018/10/19.
 */

public class MarketMainPresenter extends BasePresenter<MarketMainModule, IMarketMainView> {


    public void getData() {
        if (isViewAttached()) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        Thread.sleep(300);
                        List<MarketInfo> list = new ArrayList<>();
                        list.add(new MarketInfo(0, "联想ThinkPad 翼480（04CD）", "https://img10.360buyimg.com/mobilecms/s250x250_jfs/t22711/247/184038096/247446/b7dbcb19/5b286798Ncdb5c00e.jpg", "3999.00"));
                        list.add(new MarketInfo(0, "小米8 全面屏游戏智能手机", "https://img14.360buyimg.com/n7/jfs/t20509/311/508682869/290069/4d2c41e/5b0fcab1N217bcf3d.jpg", "2499.00"));
                        list.add(new MarketInfo(0, "母婴车车", "https://img14.360buyimg.com/n2/jfs/t9682/50/12021355/222280/25a2d659/59c372bbNa76076b9.jpg", "388.00"));
                        list.add(new MarketInfo(0, "纽伯瑞国际大奖小说全8册", "https://img14.360buyimg.com/n2/jfs/t24700/38/859671102/205515/9aa10d3d/5b7e75e7N1ea9a98e.jpg", "96.00"));
                        list.add(new MarketInfo(0, "三只松鼠巨型网红零食大礼包30包抖音", "https://img12.360buyimg.com/n2/jfs/t22789/25/1266822087/198554/baab6908/5b586392Nfc017771.jpg", "178.00"));
                        list.add(new MarketInfo(0, "陈粒：第四张创作专辑《玩》（京东专卖）", "https://img13.360buyimg.com/n3/jfs/t26320/135/1040252244/267213/868dfef3/5bc01445N84d634df.jpg!q80.jpg", "120"));
                        list.add(new MarketInfo(0, "燕京啤酒 330ml*24罐 特制啤酒", "https://img12.360buyimg.com/n2/s160x160_jfs/t1/836/11/4243/311829/5b9b03f5E56ff9777/f797222e042abcdf.jpg", "68.00"));
                        list.add(new MarketInfo(0, "天堂伞", "https://img11.360buyimg.com/babel/s180x180_jfs/t3208/128/5610451092/185712/7b14491a/5875e378N5360f782.jpg", "39.90"));
                        list.add(new MarketInfo(0, "万仟堂（Edenus）旅行茶具户外旅游茶具套装一壶一杯快客杯女", "https://img14.360buyimg.com/n2/jfs/t1/5365/5/4056/131744/5b9b0d84E079a9ea1/00491a9f45d53409.jpg!q90.jpg", "489.00"));

                        getView().dataList(list);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }

    @Override
    protected MarketMainModule createModule() {
        return new MarketMainModule();
    }
}
