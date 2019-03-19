package com.wss.module.main.ui.page;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.MoneyUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.ObserverButton;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：于慢慢牌计算器
 * Created by 吴天强 on 2019/3/12.
 */

public class YumanmanActivity extends ActionBarActivity {

    @BindView(R2.id.edt_initial_value)
    EditText edtInitialValue;

    @BindView(R2.id.edt_count_value)
    EditText edtCountValue;

    @BindView(R2.id.edt_increment_value)
    EditText edtIncrementValue;

    @BindView(R2.id.tv_result)
    TextView tvResult;

    @BindView(R2.id.tv_result_detail)
    TextView tvResultDetail;

    @BindView(R2.id.ob_btn)
    ObserverButton button;

    @BindView(R2.id.root)
    View root;

    @BindView(R2.id.values)
    View values;


    //设置一个默认值
    private int recordVisibleRec = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_yumanman;
    }

    @Override
    protected void initView() {
        setTitleText("于慢慢计算器");
        button.observer(edtInitialValue, edtIncrementValue, edtCountValue);
    }


    @OnClick({R2.id.ob_btn, R2.id.tv_result})
    public void addValue(View v) {
        if (v.getId() == R.id.ob_btn) {
            Double a = Double.valueOf(edtInitialValue.getText().toString().trim());
            Integer b = Integer.valueOf(edtCountValue.getText().toString().trim());
            Double c = Double.valueOf(edtIncrementValue.getText().toString().trim());
            add(new Bean(a, b, c));
        } else {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, ((TextView) v).getText()));
            ToastUtils.showToast(mContext, "结果已复制到粘贴板");
        }
    }

    private void add(final Bean bean) {
        showLoading("正在疯狂计算···");
        new Thread(new Runnable() {
            @Override
            public void run() {
                double a = bean.getA();
                int b = bean.getB();
                double c = bean.getC();
                double sum = a;
                StringBuilder sb = new StringBuilder();
                String str;
                if (b > 1) {
                    for (int i = 0; i < b; i++) {
                        sb.append(a);
                        sb.append("+");
                        a = Double.valueOf(MoneyUtils.Algorithm.add(a, c));
                        sum = Double.valueOf(MoneyUtils.Algorithm.add(sum, a));
                    }
                    str = sb.toString();
                    str = str.substring(0, str.length() - 1);
                } else {
                    str = String.valueOf(a);
                }
                Message message = handler.obtainMessage();
                message.obj = new Bean(sum, str);
                handler.sendMessage(message);
            }

        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bean bean = (Bean) msg.obj;
            tvResult.setText(String.format("计算结果：%s", bean.getSum()));
            tvResultDetail.setText(String.format("计算详情：%s", bean.getStr()));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                }
            }, 500);
        }
    };

    private class Bean {
        double a;
        int b;
        double c;
        double sum;
        String str;

        public Bean(double sum, String str) {
            this.sum = sum;
            this.str = str;
        }

        public double getSum() {
            return sum;
        }

        public void setSum(double sum) {
            this.sum = sum;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        Bean(double a, int b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public double getA() {
            return a;
        }

        public void setA(double a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public double getC() {
            return c;
        }

        public void setC(double c) {
            this.c = c;
        }
    }
}
