package com.wss.module.main.ui.selector.mvp;

import com.alibaba.fastjson.JSON;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Constants;
import com.wss.common.net.callback.OnResultCallBack;
import com.wss.module.main.bean.Province;
import com.wss.module.main.ui.selector.mvp.contract.SelectContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：选择器Presenter
 * Created by 吴天强 on 2018/10/24.
 */

public class SelectorPresenter extends BasePresenter<SelectContract.Module, SelectContract.View> implements SelectContract.Presenter {


    @Override
    public void getAddressList() {
        if (isViewAttached()) {
            showLoading();
            getModule().getAddress(getContext(), new OnResultCallBack<String>() {
                @Override
                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {
                    List<Province> provinceList = JSON.parseArray(response, Province.class);
                    List<List<String>> options2Items = new ArrayList<>();
                    List<List<List<String>>> options3Items = new ArrayList<>();

                    for (int i = 0; i < provinceList.size(); i++) {//遍历省份
                        List<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
                        List<List<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                        for (int c = 0; c < provinceList.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                            String CityName = provinceList.get(i).getCity().get(c).getName();
                            CityList.add(CityName);//添加城市
                            List<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                            //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                            if (provinceList.get(i).getCity().get(c).getArea() == null
                                    || provinceList.get(i).getCity().get(c).getArea().size() == 0) {
                                City_AreaList.add("");
                            } else {
                                City_AreaList.addAll(provinceList.get(i).getCity().get(c).getArea());
                            }
                            Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                        }
                        options2Items.add(CityList);
                        options3Items.add(Province_AreaList);

                    }
                    getView().addressList(provinceList, options2Items, options3Items);
                }

                @Override
                public void onFailure(Object tag, Exception e) {
                    getView().onError(tag, Constants.ERROR_MESSAGE);
                }

                @Override
                public void onCompleted() {
                    dismissLoading();
                }
            });
        }
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
        useDes.add("秀气很");
        useDes.add("啧啧啧");
        useDes.add("胡子拉碴");
        useDes.add("历史第一人");
        getView().userList(userlist, userFrom, useDes);
    }

    @Override
    protected SelectContract.Module createModule() {
        return new SelectorModule();
    }

    @Override
    public void start() {
        getAddressList();
        getUserList();
    }
}
