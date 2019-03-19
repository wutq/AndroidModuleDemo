package com.wss.module.main.ui.page;

import android.widget.TextView;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.StrongRadioGroup;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import butterknife.BindView;

/**
 * Describe：
 * Created by 吴天强 on 2019/3/18.
 */

public class RadioGroupActivity extends ActionBarActivity {


    @BindView(R2.id.mg)
    StrongRadioGroup mainTab;

    @BindView(R2.id.tv_count)
    TextView tvCount;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_radio_group;
    }

    @Override
    protected void initView() {
        setTitleText("自定义RadioGroup");
        mainTab.setOnCheckedChangeListener(new StrongRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(StrongRadioGroup group, int checkedId) {

            }
        });  //检查文件权限
    }
}
