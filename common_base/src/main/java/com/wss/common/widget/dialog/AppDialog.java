package com.wss.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.utils.KeyboardUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.widget.CountClickView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Describe：自定义对话框
 * Created by 吴天强 on 2018年9月27日18:13:09
 */
public class AppDialog {


    @BindView(R2.id.dialog_layout)
    LinearLayout dialogLayout;//对话框的顶级父类

    @BindView(R2.id.ll_center)
    LinearLayout llCenter;//中间显示的Dialog 父View
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.edt_input)
    EditText edtInput;
    @BindView(R2.id.iv_minus)
    ImageView ivMinus;
    @BindView(R2.id.edt_count)
    EditText edtCount;
    @BindView(R2.id.iv_plus)
    ImageView ivPlus;
    @BindView(R2.id.ll_count_view)
    LinearLayout llCountView;
    @BindView(R2.id.btn_left)
    Button btnLeft;
    @BindView(R2.id.btn_right)
    Button btnRight;
    @BindView(R2.id.btn_line)
    View btnLine;
    @BindView(R2.id.ll_content_layout)
    LinearLayout contentLayout;//中间弹出对话框的View


    @BindView(R2.id.ll_bottom)
    LinearLayout llBottom;//底部弹出的Dialog 父View
    @BindView(R2.id.tv_bottom_title)
    TextView tvBottomTitle;
    @BindView(R2.id.ll_context)
    LinearLayout llContext;
    @BindView(R2.id.sLayout_content)
    ScrollView sLayoutContent;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;

    @DialogType.Type
    private int type;
    private int maxCount = CountClickView.MAX_COUNT;
    private int minCount = CountClickView.MIN_COUNT;
    private Dialog dialog;
    private Context mContext;

    private OnButtonClickListener leftListener;
    private OnButtonClickListener rightListener;
    private OnItemClickListener itemClickListener;
    private String title;//标题

    public AppDialog(Context context) {
        this(context, DialogType.DEFAULT);
    }

    public AppDialog(Context context, @DialogType.Type int type) {
        this.mContext = context;
        this.type = type;
        View dialogView = View.inflate(mContext, R.layout.dialog_app, null);
        ButterKnife.bind(this, dialogView);
        int themeResId = R.style.DialogStyle;
        if (type == DialogType.BOTTOM_IN) {
            themeResId = R.style.ActionSheetDialogStyle;
            dialogView.setMinimumWidth(PxUtils.getScreenWidth(mContext));
        }
        dialog = new Dialog(mContext, themeResId);
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        if (window != null) {
            if (type == DialogType.BOTTOM_IN) {
                window.getAttributes().gravity = Gravity.BOTTOM;
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.x = 0;
                lp.y = 0;
                window.setAttributes(lp);
            } else {
                window.getAttributes().gravity = Gravity.CENTER;
            }
        }
        dialog.setCanceledOnTouchOutside(false);
    }

    private void initView() {
        switch (type) {
            case DialogType.DEFAULT:
                setTitleText();
                break;
            case DialogType.INPUT:
                setTitleText();
                edtInput.setVisibility(View.VISIBLE);
                tvContent.setVisibility(View.GONE);
                llCountView.setVisibility(View.GONE);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
            case DialogType.COUNT:
                setTitleText();
                edtInput.setVisibility(View.GONE);
                tvContent.setVisibility(View.GONE);
                llCountView.setVisibility(View.VISIBLE);
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
            case DialogType.NO_TITLE:
                tvTitle.setVisibility(View.GONE);
                break;
            case DialogType.BOTTOM_IN:
                //底部弹出对话框
                tvBottomTitle.setText(title);
                llCenter.setVisibility(View.GONE);
                llBottom.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void setTitleText() {
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
    }


    /**
     * 设置Title
     */
    public AppDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 设置显示内容
     */
    public AppDialog setContent(String content) {
        tvContent.setText(content);
        return this;
    }


    /**
     * 加减输入模式下 设置的数字
     *
     * @param minCount minCount
     * @param maxCount maxCount
     * @param current  current
     */
    public AppDialog setNumber(int minCount, int maxCount, int current) {
        this.minCount = minCount;
        this.maxCount = maxCount;
        edtCount.setText(String.valueOf(current));
        edtCount.setSelection(edtCount.getText().length());
        edtCount.addTextChangedListener(textWatcher);
        judgeTheViews(current);
        return this;
    }

    /**
     * 显示对话框
     */
    public void show() {
        initView();
        dialog.show();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 给左边按钮设置文字 事件
     *
     * @param text     文字
     * @param listener 事件
     * @return AppDialog
     */
    public AppDialog setLeftButton(String text, OnButtonClickListener listener) {
        this.leftListener = listener;
        if (!TextUtils.isEmpty(text)) {
            btnLeft.setText(text);
        }
        return this;
    }

    /**
     * 给左边按钮设置文字 事件
     *
     * @param listener 事件
     * @return AppDialog
     */
    public AppDialog setLeftButton(OnButtonClickListener listener) {
        return setLeftButton(null, listener);
    }

    /**
     * 给左边按钮设置文字 事件
     *
     * @param text text
     * @return AppDialog
     */
    public AppDialog setLeftButton(String text) {
        return setLeftButton(text, null);
    }


    /**
     * 给右边按钮设置文字 事件
     *
     * @param text     文字
     * @param listener 事件
     * @return AppDialog
     */
    public AppDialog setRightButton(String text, OnButtonClickListener listener) {
        this.rightListener = listener;
        if (!TextUtils.isEmpty(text)) {
            btnRight.setText(text);
        }
        return this;
    }

    /**
     * 给右边按钮设置文字 事件
     *
     * @param listener 事件
     * @return AppDialog
     */
    public AppDialog setRightButton(OnButtonClickListener listener) {
        return setRightButton(null, listener);
    }

    /**
     * 给右边按钮设置文字 事件
     *
     * @param text text
     * @return AppDialog
     */
    public AppDialog setRightButton(String text) {
        return setRightButton(text, null);
    }

    /**
     * 左按钮文字颜色
     *
     * @param color color
     * @return AppDialog
     */
    public AppDialog setLeftButtonTextColor(int color) {
        btnLeft.setTextSize(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 右按钮文字大小
     *
     * @param color color
     * @return AppDialog
     */
    public AppDialog setRightButtonTextColor(int color) {
        btnRight.setTextSize(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 单按钮文字大小
     *
     * @param color color
     * @return AppDialog
     */
    public AppDialog setSingleButtonTextColor(int color) {
        return setRightButtonTextColor(color);
    }


    /**
     * 单按钮
     *
     * @param text     text
     * @param listener listener
     * @return AppDialog
     */
    public AppDialog setSingleButton(String text, OnButtonClickListener listener) {
        this.rightListener = listener;
        if (!TextUtils.isEmpty(text)) {
            btnRight.setText(text);
        }
        btnLeft.setVisibility(View.GONE);
        btnLine.setVisibility(View.GONE);
        btnRight.setBackgroundResource(R.drawable.corners_white_gray_selecter);
        return this;
    }

    /**
     * 单按钮
     *
     * @param listener listener
     * @return AppDialog
     */
    public AppDialog setSingleButton(OnButtonClickListener listener) {
        return setSingleButton(null, listener);
    }

    /**
     * 单按钮
     *
     * @param text listener
     * @return AppDialog
     */
    public AppDialog setSingleButton(String text) {
        return setSingleButton(text, null);
    }

    /**
     * 单按钮
     *
     * @return AppDialog
     */
    public AppDialog setSingleButton() {
        return setSingleButton(null, null);
    }

    /**
     * 设置底部弹出对话框的条目
     *
     * @param items    条目名称
     * @param listener listener
     * @return AppDialog
     */
    public AppDialog setBottomItems(List<String> items, OnItemClickListener listener) {
        this.itemClickListener = listener;
        setItems(items);
        return this;
    }

    /**
     * 设置底部弹出对话框的取消文字
     *
     * @param text text
     * @return AppDialog
     */
    public AppDialog setBottomCancelText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvCancel.setText(text);
        }
        return this;
    }

    /**
     * 设置底部弹出对话框的取消文字颜色
     *
     * @param color text
     * @return AppDialog
     */
    public AppDialog setBottomCancelTextColor(int color) {
        tvCancel.setTextColor(mContext.getResources().getColor(color));
        return this;
    }


    /**
     * 给整个Dialog添加View
     *
     * @param view view
     * @return AppDialog
     */
    public AppDialog addDialogView(View view) {
        dialogLayout.removeAllViews();
        dialogLayout.addView(view);
        return this;
    }

    /**
     * 中间弹出对话框添加View
     *
     * @param view view
     * @return AppDialog
     */
    public AppDialog addContentView(View view) {
        contentLayout.removeAllViews();
        contentLayout.addView(view);
        return this;
    }

    /**
     * 底部弹出对话框添加View
     *
     * @param view     view
     * @param itemSize 条目数量
     * @return AppDialog
     */
    public AppDialog addItemView(View view, int itemSize) {
        llContext.removeAllViews();
        llContext.addView(view);
        setItemScrollViewHeight(itemSize);
        return this;
    }


    @OnClick({R2.id.btn_left, R2.id.btn_right, R2.id.minus, R2.id.plus, R2.id.tv_cancel})
    public void onClick(View v) {
        if (v.getId() == R.id.btn_left) {
            onButtonClick(leftListener);
        } else if (v.getId() == R.id.btn_right) {
            onButtonClick(rightListener);
        } else if (v.getId() == R.id.minus) {
            //减少
            int count = getCountEdtValue();
            if (getCountEdtValue() > minCount) {
                edtCount.setText(String.valueOf(--count));
            }
            judgeTheViews(count);
        } else if (v.getId() == R.id.plus) {
            //增加
            int count = getCountEdtValue();
            if (count < maxCount) {
                edtCount.setText(String.valueOf(++count));
            }
            judgeTheViews(count);
        } else if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }
    }

    /**
     * 加减模式下点击结束结算值
     */
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

    /**
     * 给点击事件设置数据
     *
     * @param listener listener
     */
    private void onButtonClick(OnButtonClickListener listener) {

        if (type == DialogType.COUNT) {
            if (getCountEdtValue() >= minCount) {
                if (listener != null) {
                    listener.onClick(String.valueOf(getCountEdtValue()));
                }
            }
            KeyboardUtils.hideKeyboard(edtCount);
        } else if (type == DialogType.INPUT) {
            if (listener != null) {
                listener.onClick(edtInput.getText().toString().trim());
            }
            KeyboardUtils.hideKeyboard(edtInput);
        } else {
            if (listener != null) {
                listener.onClick(tvContent.getText().toString());
            }
        }
        dismiss();
    }

    /**
     * 底部弹出对话框添加条目
     *
     * @param items items
     */
    private void setItems(List<String> items) {
        if (items != null && items.size() > 0) {
            int size = items.size();
            setItemScrollViewHeight(size);
            // 循环添加条目
            for (int i = 0; i <= size - 1; i++) {
                View v = View.inflate(mContext, R.layout.layout_item_of_dialog_bottom_in, null);
                TextView item = v.findViewById(R.id.tv_text);
                item.setText(items.get(i));
                item.setTag(i);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick((int) v.getTag());
                        }
                        dismiss();
                    }
                });
                llContext.addView(v);
            }
        }
    }

    /**
     * 设置底部弹出带条目的ScrollView的高度
     *
     * @param size 条目数量
     */
    private void setItemScrollViewHeight(int size) {
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sLayoutContent.getLayoutParams();
            params.height = PxUtils.getScreenHeight(mContext) / 2;
            sLayoutContent.setLayoutParams(params);
        }
    }

    /**
     * 得到 加减模式下 输入框的值
     *
     * @return int
     */
    private int getCountEdtValue() {
        int count = 0;
        String text = edtCount.getText().toString().trim();
        if (!TextUtils.isEmpty(text)) {
            count = Integer.parseInt(text);
        }
        return count;
    }

    /**
     * 加减模式下输入框的监听
     */
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

    /**
     * 按钮被点击事件回调
     */
    public interface OnButtonClickListener {
        void onClick(String val);
    }

    /**
     * 底部弹出对话框条目被点击
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}