package com.wss.module.main.ui.page.selector.mvp;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.ui.page.selector.mvp.contract.SelectContract;
import com.wss.module.main.ui.page.selector.mvp.model.SelectorModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：选择器Presenter
 * Created by 吴天强 on 2018/10/24.
 */
public class SelectorPresenter extends BasePresenter<SelectorModule, SelectContract.View> implements SelectContract.Presenter {

    @Override
    public void getAddressList() {
        getModel().getAddress(getContext()).subscribe(
                provinceList -> {
                    List<List<String>> options2Items = new ArrayList<>();
                    List<List<List<String>>> options3Items = new ArrayList<>();
                    for (int i = 0; i < provinceList.size(); i++) {//遍历省份
                        List<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
                        List<List<String>> provinceAreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                        for (int c = 0; c < provinceList.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                            String cityName = provinceList.get(i).getCity().get(c).getName();
                            cityList.add(cityName);//添加城市
                            List<String> cityAreaList = new ArrayList<>();//该城市的所有地区列表

                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (provinceList.get(i).getCity().get(c).getArea() == null
                                    || provinceList.get(i).getCity().get(c).getArea().size() == 0) {
                                cityAreaList.add("");
                            } else {
                                cityAreaList.addAll(provinceList.get(i).getCity().get(c).getArea());
                            }
                            provinceAreaList.add(cityAreaList);//添加该省所有地区数据
                        }
                        options2Items.add(cityList);
                        options3Items.add(provinceAreaList);

                    }
                    getView().refreshAddressList(provinceList, options2Items, options3Items);
                });
    }

    @Override
    public void getUserList() {
        List<String> userlist = new ArrayList<>();
        userlist.add("秦始皇");
        userlist.add("汉武帝");
        userlist.add("光武帝");
        userlist.add("隋文帝");
        userlist.add("唐太宗");
        userlist.add("唐玄宗");
        userlist.add("宋太祖");
        userlist.add("明太祖");
        userlist.add("周武王");
        userlist.add("赵灵王");
        List<String> userFrom = new ArrayList<>();
        userFrom.add("秦国");
        userFrom.add("唐帝国");
        userFrom.add("宋帝国");
        userFrom.add("明帝国");
        userFrom.add("赵国");
        userFrom.add("大汉");
        List<String> useDes = new ArrayList<>();
        useDes.add("老男人");
        useDes.add("酸秀才");
        useDes.add("武举人");
        useDes.add("抠脚大汉");
        useDes.add("历史第一人");
        getView().refreshNonLinkageList(userlist, userFrom, useDes);
    }

    @Override
    protected SelectorModule createModule() {
        return new SelectorModule(getLifecycleOwner());
    }

    @Override
    public void start() {
        getAddressList();
        getUserList();
    }
}
