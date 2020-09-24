package com.wss.module.main.ui.page.selector.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：自定义事件选择
 * Created by 吴天强 on 2020/9/21.
 */
@Getter
@Setter
public class DateItem extends BaseBean implements IPickerViewData {

    private String showText;
    private String date;


    @Override
    public String getPickerViewText() {
        return showText;
    }
}
