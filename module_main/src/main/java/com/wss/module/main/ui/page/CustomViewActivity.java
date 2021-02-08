package com.wss.module.main.ui.page;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.KeyboardUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.CountClickView;
import com.wss.common.widget.ObserverButton;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.view.CircleProgressBar;
import com.wss.module.main.ui.view.FaceScanFloatView;
import com.wss.module.main.ui.view.SwitchButton;
import com.wss.module.main.ui.view.VerificationCodeView;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：自定义View展示页
 * Created by 吴天强 on 2020/8/27.
 */
@SuppressLint("ClickableViewAccessibility")
public class CustomViewActivity extends BaseActionBarActivity {

    public static final float MAX_STEP = 10000F;

    @BindView(R2.id.vc_code)
    VerificationCodeView vcCode;

    @BindView(R2.id.tv_vc_code)
    TextView tvVcCode;

    @BindView(R2.id.cpb_1)
    CircleProgressBar cpb01;

    @BindView(R2.id.cpb_2)
    CircleProgressBar cpb02;

    @BindView(R2.id.sb_01)
    SwitchButton switchButton;

    @BindView(R2.id.ccv_cust)
    CountClickView ccvCust;

    @BindView(R2.id.edt_name)
    EditText edtName;

    @BindView(R2.id.edt_pwd)
    EditText edtPwd;

    @BindView(R2.id.ob_btn)
    ObserverButton obBtn;

    @BindView(R2.id.circle_view)
    FaceScanFloatView floatView;

    @BindView(R2.id.iv_loading)
    ImageView ivLoading;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_custom_view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        setCenterText("自定义View展览区");

        vcCode.addOnCodeChangeListener(code -> tvVcCode.setText(String.format("验证码是:%s", code)));
        tvVcCode.setText(String.format("验证码是：%s", vcCode.getText()));

        cpb01.setMaxValue(MAX_STEP);

        switchButton.setOnCheckedChangeListener(
                (view, isChecked) -> ToastUtils.show("选中状态：" + isChecked));

        //自定义背景图
        ccvCust.setButtonRes(R.drawable.main_add_count_view, R.drawable.main_minus_count_view);
        ccvCust.setBtnParentBg(R.color.white);
        ccvCust.setBtnSize(20, 20);
        ccvCust.setCountViewAttr(R.color.transparent, 0, 4, 4);
        ccvCust.setAfterClickListener((view, value) -> Logger.e("result:" + value));

        obBtn.observer(edtName, edtPwd);

        floatView.startAnimator();
        floatView.setTipsTextColor(R.color.theme);
    }

    /**
     * bitmap转二进制流
     *
     * @param bitmap bitmap
     * @return byte
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    @OnClick({R2.id.btn_click,
            R2.id.btn_success})
    public void onClick(@NotNull View view) {
        if (view.getId() == R.id.btn_click) {
            //重置环形进度条的数值
            int step = new Random().nextInt((int) MAX_STEP);
            cpb01.setValue(step);
            float i = (step / MAX_STEP) * 100L;
            cpb02.setValue(i);

        } else if (view.getId() == R.id.btn_success) {
            //设置圆圈浮层 成功View
            floatView.isComplete("认证成功");
            ss();
            getByte();
        }
    }


    private void getByte() {
        Bitmap bitmap = ((BitmapDrawable) getDrawable(R.drawable.aa)).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //参数100表示不压缩
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] bytes = bos.toByteArray();
        Logger.e(Arrays.toString(bytes));
        String s = Base64.encodeToString(bytes, Base64.DEFAULT);
        Logger.e(s);
    }


    @OnClick(R2.id.ob_btn)
    public void onObBtnClick(View view) {
        KeyboardUtils.hideKeyboard(view);
        ToastUtils.show("哈哈哈");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void ss() {
        ivLoading.animate()
                .rotationBy(-360)
                .setDuration(350)
                .setInterpolator(new LinearInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // do nothing.
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivLoading.setRotation(0);
                        ivLoading.animate()
                                .rotationBy(-360)
                                .setDuration(350)
                                .setListener(this)
                                .start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        // do nothing.
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                        // do nothing.
                    }
                })
                .start();
    }
}
