package com.wss.common.bean;

import com.contrarywind.interfaces.IPickerViewData;
import com.wss.common.base.bean.BaseBean;
import com.wss.common.utils.ValidUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：弹窗选择器数据Bean
 * Created by 吴天强 on 2020/4/21.
 */
@Getter
@Setter
public class SelectorData<T> extends BaseBean implements IPickerViewData {
    private String name;
    private T data;

    public SelectorData(String name, T data) {
        this.name = name;
        this.data = data;
    }

    @Override
    public String getPickerViewText() {
        return ValidUtils.isValid(name) ? name : "";
    }
}
