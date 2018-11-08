package com.wss.module.main.ui.main.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.wss.common.base.BaseMvpFragment;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.manage.BlurTransformation;
import com.wss.common.manage.CircleTransformation;
import com.wss.common.utils.ActivityToActivity;
import com.wss.common.utils.ImageUtils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.hortab.OrderListActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：个人中心
 * Created by 吴天强 on 2018/10/17.
 */

public class UserFragment extends BaseMvpFragment {

    public static final String URL = "https://b-ssl.duitang.com/uploads/item/201601/16/20160116173115_vTfWm.jpeg";

    @BindView(R2.id.iv_bg)
    ImageView ivBg;

    @BindView(R2.id.iv_head)
    ImageView ivHead;

    @Override
    protected void initView() {
        ImageUtils.loadImage(ivBg, URL, new BlurTransformation(mContext));
        ImageUtils.loadImage(ivHead, URL, new CircleTransformation());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_user;
    }


    @OnClick({R2.id.miv_order, R2.id.miv_check})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.miv_order) {
            ActivityToActivity.toActivity(mContext, OrderListActivity.class);
        } else if (i == R.id.miv_check) {
            showLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    new AppDialog(mContext)
                            .setContent("当前已是最新版本")
                            .setSingleButton("确定")
                            .show();
                }
            }, 2000);

        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
