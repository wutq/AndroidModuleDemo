package com.wss.module.main.ui.im;

import android.widget.ImageView;
import android.widget.TextView;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.IMMessage;

import butterknife.BindView;

/**
 * Describe：聊天
 * Created by 吴天强 on 2018/10/30.
 */

public class IMUserInfoActivity extends ActionBarActivity {

    @BindView(R2.id.iv_head)
    ImageView ivHead;
    @BindView(R2.id.tv_name)
    TextView tvName;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_im_user_info;
    }

    @Override
    protected void initView() {
        setTitleText("个人信息");
        IMMessage message = (IMMessage) getIntent().getSerializableExtra("IMUserInfoActivity");
        ivHead.setBackgroundResource(message.getIcon());
        tvName.setText(String.format("姓名：%s", message.getName()));
    }

}
