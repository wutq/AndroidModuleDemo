package com.wss.module.main.ui.page;

import android.widget.TextView;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.StrongRadioGroup;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import butterknife.BindView;

/**
 * Describe：可添加多View的RadioGroup
 * Created by 吴天强 on 2019/3/18.
 */
public class RadioGroupActivity extends BaseActionBarActivity {


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
        setCenterText("自定义RadioGroup");
        mainTab.setOnCheckedChangeListener((group, checkedId) -> {
        });
    }
}
