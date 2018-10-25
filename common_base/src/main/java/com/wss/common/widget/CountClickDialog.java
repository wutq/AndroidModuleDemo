package com.wss.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.utils.TextUtils;
import com.wss.common.base.R;
import com.wss.common.base.R2;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Describe：加减对话框
 * Created by 吴天强 on 2018年9月27日18:13:09
 */
public class CountClickDialog extends Dialog {

    private Context context;
    private TextView tvTitle;
    private ImageView ivMinus;
    private ImageView ivPlus;
    private EditText edtCount;

    private int maxCount = CountClickView.MAX_COUNT;
    private int minCount = CountClickView.MIN_COUNT;

    private OnConfirmClickListener listener;

    public CountClickDialog(final Context context) {
        super(context, R.style.DialogStyle);
        this.context = context;
        View dialogView = View.inflate(context, R.layout.dialog_count_click, null);
        tvTitle = dialogView.findViewById(R.id.tv_title);
        ivMinus = dialogView.findViewById(R.id.iv_minus);
        ivPlus = dialogView.findViewById(R.id.iv_plus);
        edtCount = dialogView.findViewById(R.id.edt_count);
        ButterKnife.bind(this, dialogView);

        setContentView(dialogView);
        Window window = getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.CENTER;
        }
        setCanceledOnTouchOutside(false);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s.toString().trim())) {
                edtCount.removeTextChangedListener(textWatcher);
                int text = Integer.parseInt(s.toString());
                if (text < minCount) {
                    edtCount.setText(String.valueOf(minCount));
                } else if (text > maxCount) {
                    edtCount.setText(String.valueOf(maxCount));
                }
                edtCount.setSelection(edtCount.getText().length());
                edtCount.addTextChangedListener(textWatcher);
                judgeTheViews(Integer.parseInt(edtCount.getText().toString().trim()));
            } else {
                //删光了 减 按钮不可点击
                ivMinus.setImageResource(R.drawable.input_minus_disabled);
            }
        }
    };

    public void setTitle(String title) {
        tvTitle.setText(title);
    }


    public void showDialog() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            public void run() {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }, 300);
        show();
    }

    public void setNumber(int minCount, int maxCount, int current) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        edtCount.setText(String.valueOf(current));
        edtCount.setSelection(edtCount.getText().length());
        edtCount.addTextChangedListener(textWatcher);
        judgeTheViews(current);
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        this.listener = listener;
    }

    @OnClick({R2.id.btn_cancel, R2.id.btn_confirm, R2.id.minus, R2.id.plus})
    public void onClick(View v) {
        int count = 0;
        String text = edtCount.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            count = Integer.parseInt(text);
        }

        if (v.getId() == R.id.btn_cancel) {
            dismiss();
        } else if (v.getId() == R.id.btn_confirm) {
            if (count >= minCount) {
                if (listener != null) {
                    listener.onClick(count);
                }
            }
            dismiss();
        } else if (v.getId() == R.id.minus) {
            //减少
            if (count > minCount) {
                edtCount.setText(String.valueOf(--count));
            }
            judgeTheViews(count);
        } else if (v.getId() == R.id.plus) {
            //增加
            if (count < maxCount) {
                edtCount.setText(String.valueOf(++count));
            }
            judgeTheViews(count);
        }
    }

    private void judgeTheViews(int count) {
        if (count == minCount) {
            ivMinus.setImageResource(R.drawable.input_minus_disabled);
        } else {
            ivMinus.setImageResource(R.drawable.input_minus_default);
        }
        if (count == maxCount) {
            ivPlus.setImageResource(R.drawable.input_add_disabled);
        } else {
            ivPlus.setImageResource(R.drawable.input_add_default);
        }
    }

    public interface OnConfirmClickListener {
        void onClick(int val);

    }

}