package com.wss.module.user.ui.main.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.bean.AppInfo;
import com.wss.common.bean.Event;
import com.wss.common.bean.User;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.constants.EventAction;
import com.wss.common.manage.UpdateManager;
import com.wss.common.net.Api;
import com.wss.common.utils.ActivityToActivity;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.UserInfoUtils;
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

    public static final String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542110989472&di=e206dfdad3d1197025ddc03bca0b013c&imgtype=0&src=http%3A%2F%2Fwww.pig66.com%2Fuploadfile%2F2017%2F1209%2F20171209121323978.png";

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


    @Override
    protected void initView() {

        ImageUtils.loadImageBlur(ivBg, URL);
        ImageUtils.loadImageCircle(ivHead, URL);
        displayInfo();
    }

    private void displayInfo() {
        User user = UserInfoUtils.getUser();
        if (UserInfoUtils.isLogged()) {
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

    @Override
    protected int getLayoutId() {
        return R.layout.user_fragment_user;
    }


    @OnClick({R2.id.miv_order, R2.id.miv_check, R2.id.tv_login,
            R2.id.miv_collection, R2.id.tv_login_out, R2.id.miv_about})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.tv_login) {
            //去登录
            ActivityToActivity.toActivity(mContext, LoginActivity.class);
        } else if (i == R.id.miv_collection) {
            //我的收藏
            if (checkLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.WAN_COLLECTION);
            }
        } else if (i == R.id.miv_order) {
            //我的订单
            if (checkLogin()) {
                ActivityToActivity.toActivity(ARouterConfig.MAIN_ORDER_LIST);
            }
        } else if (i == R.id.miv_about) {
            //关于
            ActivityToActivity.toActivity(mContext, AboutActivity.class);

        } else if (i == R.id.miv_check) {
            presenter.checkUpdate();

        } else if (i == R.id.tv_login_out) {
            new AppDialog(mContext)
                    .setContent("是否确定退出？")
                    .setRightButton(new AppDialog.OnButtonClickListener() {
                        @Override
                        public void onClick(String val) {
                            //退出登录
                            UserInfoUtils.cleanUser();
                            displayInfo();
                        }
                    })
                    .show();
        }
    }


    private boolean checkLogin() {
        if (!UserInfoUtils.isLogged()) {
            new AppDialog(mContext)
                    .setContent("请您先登录")
                    .setRightButton(new AppDialog.OnButtonClickListener() {
                        @Override
                        public void onClick(String val) {
                            ActivityToActivity.toActivity(mContext, LoginActivity.class);
                        }
                    })
                    .show();
            return false;
        }
        return true;
    }

    @Override
    public void onEventBus(Event event) {
        super.onEventBus(event);
        if (TextUtils.equals(event.getAction(), EventAction.EVENT_LOGIN_SUCCESS)) {
            displayInfo();
        }
    }

    @Override
    protected boolean regEvent() {
        return true;
    }


    @Override
    public void needUpdate(AppInfo appInfo) {
        String context = "版本更新！";
        if (appInfo != null && !TextUtils.isEmpty(appInfo.getDescribe())) {
            context = appInfo.getDescribe();
        }
        new AppDialog(mContext)
                .setTitle("提示更新")
                .setContent(context)
                .setRightButton("更新", new AppDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(String val) {
                        UpdateManager.getInstance(mContext).download(Api.DOWNLOAD_APK);
                    }
                })
                .show();
    }

    @Override
    public void isLastVersion() {
        new AppDialog(mContext)
                .setContent("当前已是最新版本")
                .setSingleButton("确定")
                .show();
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter();
    }
}
