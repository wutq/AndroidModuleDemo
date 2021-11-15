package com.wss.module.user.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.BaseApplication;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.bean.Event;
import com.wss.common.bean.User;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.EventAction;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.Utils;
import com.wss.common.widget.MultipleItemView;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.module.user.R;
import com.wss.module.user.R2;
import com.wss.module.user.ui.about.AboutActivity;
import com.wss.module.user.ui.account.LoginActivity;
import com.wss.module.user.ui.main.mvp.UserPresenter;
import com.wss.module.user.ui.main.mvp.contract.UserContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：个人中心
 * Created by 吴天强 on 2018/10/17.
 */
@Route(path = ARouterConfig.USER_MAIN_FRAGMENT)
public class UserFragment extends BaseMvpFragment<UserPresenter> implements UserContract.View {

    private static final String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597207810717&di=4498f60a4b64fb7a436943bb420c5e1e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F5bb3acc1916253d3228ab37879d910bfcbe1a7ce1df00a-ld0gg1_fw658";

    @BindView(R2.id.iv_bg)
    ImageView ivBg;

    @BindView(R2.id.iv_head)
    ImageView ivHead;

    @BindView(R2.id.tv_login_out)
    TextView tvLoginOut;

    @BindView(R2.id.ll_logged)
    LinearLayout llLogged;

    @BindView(R2.id.tv_login)
    TextView tvLogin;

    @BindView(R2.id.tv_name)
    TextView tvName;

    @BindView(R2.id.tv_email)
    TextView tvEmail;

    @BindView(R2.id.miv_check)
    MultipleItemView mivVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.user_fragment_user;
    }

    @Override
    protected void initView() {
        ImageUtils.loadImageBlur(ivBg, URL);
        ImageUtils.loadImageCircle(ivHead, URL);
        refreshPage();
        mivVersion.setRightText(String.format("当前版本：V%s", Utils.getVersionName()));
    }

    private void refreshPage() {
        if (BaseApplication.i().isLogged()) {
            User user = BaseApplication.i().getUser();
            llLogged.setVisibility(View.VISIBLE);
            tvLoginOut.setVisibility(View.VISIBLE);
            tvLogin.setVisibility(View.GONE);
            tvName.setText(user.getUsername());
            tvEmail.setText(TextUtils.isEmpty(user.getEmail()) ? "暂无邮箱" : user.getEmail());
        } else {
            llLogged.setVisibility(View.GONE);
            tvLogin.setVisibility(View.VISIBLE);
            tvLoginOut.setVisibility(View.GONE);
        }
    }


    @OnClick({R2.id.miv_order, R2.id.miv_check, R2.id.tv_login,
            R2.id.miv_collection, R2.id.tv_login_out, R2.id.miv_about})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_login) {
            //去登录
            ActivityToActivity.toActivity(context, LoginActivity.class);
        } else if (i == R.id.miv_collection) {
            //我的收藏

        } else if (i == R.id.miv_order) {
            //我的订单
        } else if (i == R.id.miv_about) {
            //关于
            ActivityToActivity.toActivity(context, AboutActivity.class);
        } else if (i == R.id.miv_check) {
            getPresenter().checkUpdate();
        } else if (i == R.id.tv_login_out) {
            new AppDialog.Builder(context)
                    .setContent("是否确定退出？")
                    .setRightButton(val -> {
                        BaseApplication.i().setUser(null);
                        refreshPage();
                    })
                    .create()
                    .show();
        }
    }

    /**
     * 检查登录
     *
     * @return boolean
     */
    private boolean checkLogin() {
        if (BaseApplication.i().isLogged()) {
            return true;
        }
        new AppDialog.Builder(context)
                .setContent("请您先登录")
                .setRightButton(val -> ActivityToActivity.toActivity(context, LoginActivity.class))
                .create()
                .show();
        return false;
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (EventAction.EVENT_LOGIN_SUCCESS.equals(event.getAction()) ||
                EventAction.EVENT_REGISTER_SUCCESS.equals(event.getAction())) {
            //登录成功、注册成功刷新页面
            refreshPage();
        }
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }
}
