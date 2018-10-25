package com.wss.module.main.ui.selector.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.main.bean.Province;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/10/24.
 */

public interface ISelectorView extends IBaseView {

    void dataList(List<Province> options1Items, List<List<String>> options2Items, List<List<List<String>>> options3Items);

    void userList(List<String> userList, List<String> userFrom, List<String> userDes);

}
