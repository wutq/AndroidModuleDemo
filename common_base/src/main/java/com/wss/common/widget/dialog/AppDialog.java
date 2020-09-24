package com.wss.common.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.utils.KeyboardUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.utils.ValidUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Describe：自定义对话框
 * Created by 吴天强 on 2018年9月27日18:13:09
 */
public class AppDialog {

    private static final int MAX_ITEM = 7;

    /**
     * 对话框的顶级父类
     */
    @BindView(R2.id.dialog_layout)
    LinearLayout dialogLayout;

    /**
     * 中间显示的Dialog 父View
     */
    @BindView(R2.id.ll_center)
    LinearLayout llCenter;
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.tv_content)
    TextView tvContent;
    @BindView(R2.id.edt_input)
    EditText edtInput;

    @BindView(R2.id.btn_left)
    Button btnLeft;
    @BindView(R2.id.btn_right)
    Button btnRight;
    @BindView(R2.id.btn_line)
    View btnLine;

    /**
     * 中间弹出对话框的Content View
     */
    @BindView(R2.id.ll_content_layout)
    LinearLayout contentLayout;
    @BindView(R2.id.center_scroll_view)
    ScrollView centerScrollView;

    /**
     * 底部弹出的Dialog 父View
     */
    @BindView(R2.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R2.id.ll_context)
    LinearLayout llContext;
    @BindView(R2.id.bottom_scroll_view)
    ScrollView bottomScrollView;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;

    private Dialog dialog;
    private Unbinder butterKnifeBinder;
    private Builder builder;

    private AppDialog() {

    }

    private AppDialog(Builder builder) {
        this.builder = builder;
    }

    /**
     * 显示对话框
     */
    public void show() {
        initView();
        dialog.show();
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
        if (butterKnifeBinder != null) {
            butterKnifeBinder.unbind();
        }
    }

    /**
     * 初始化View
     */
    private void initView() {
        View dialogView = View.inflate(builder.context, R.layout.dialog_app, null);
        butterKnifeBinder = ButterKnife.bind(this, dialogView);
        int themeResId = R.style.DialogStyle;
        if (builder.type == DialogType.BOTTOM_IN) {
            themeResId = R.style.ActionSheetDialogStyle;
            dialogView.setMinimumWidth(PxUtils.getScreenWidth(builder.context));
        }
        dialog = new Dialog(builder.context, themeResId);
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        if (window != null) {
            if (builder.type == DialogType.BOTTOM_IN) {
                window.getAttributes().gravity = Gravity.BOTTOM;
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.x = 0;
                lp.y = 0;
                window.setAttributes(lp);
            } else {
                window.getAttributes().gravity = Gravity.CENTER;
            }
        }
        dialog.setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        dialog.setCancelable(builder.cancelable);
        dialog.setOnDismissListener(builder.dialogDismissListener);
        switch (builder.type) {
            case DialogType.INPUT:
                //带文本框输入
                setInputSetting();
                break;
            case DialogType.NO_TITLE:
                tvTitle.setVisibility(View.GONE);
                setCommonSetting();
                break;
            case DialogType.BOTTOM_IN:
                //底部弹出对话框
                setBottomSetting();
                break;
            case DialogType.REPLACE_ALL:
                //替换全部View
                dialogLayout.removeAllViews();
                dialogLayout.addView(builder.allDialogView);
                break;
            case DialogType.REPLACE_CONTENT:
                //替换Content View
                contentLayout.removeAllViews();
                contentLayout.addView(builder.contentView);
                break;
            case DialogType.REPLACE_BOTTOM:
                //替换底部View
                llBottom.setVisibility(View.VISIBLE);
                llBottom.removeAllViews();
                llBottom.addView(builder.bottomView);
                break;
            case DialogType.DEFAULT:
                //默认对话框
            default:
                setCommonSetting();
                break;
        }
    }


    /**
     * 常规对话框相关设置
     */
    private void setCommonSetting() {
        llCenter.setVisibility(View.VISIBLE);
        tvContent.setTextSize(builder.contentTextSize);
        btnLeft.setTextSize(builder.leftButtonTextSize);
        btnRight.setTextSize(builder.rightButtonTextSize);
        tvContent.setTextColor(builder.contentTextColor);
        btnLeft.setTextColor(builder.leftButtonTextColor);
        btnRight.setTextColor(builder.rightButtonTextColor);
        if (ValidUtils.isValid(builder.title)) {
            tvTitle.setText(builder.title);
        }
        if (ValidUtils.isValid(builder.letButtonText)) {
            btnLeft.setText(builder.letButtonText);
        }
        if (ValidUtils.isValid(builder.rightButtonText)) {
            btnRight.setText(builder.rightButtonText);
        }
        tvContent.setText(builder.content);
        centerScrollView.post(() -> {
            int height = centerScrollView.getHeight();
            int max = PxUtils.getScreenHeight(builder.context) * 3 / 5;
            if (height > max) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bottomScrollView.getLayoutParams();
                params.height = max;
                centerScrollView.setLayoutParams(params);
            }
        });
        if (builder.isSingleButton) {
            //单按钮
            btnLeft.setVisibility(View.GONE);
            btnLine.setVisibility(View.GONE);
            btnRight.setBackgroundResource(R.drawable.corners_white_gray_selecter);
        }
    }


    /**
     * 文本框输入类型相关设置
     */
    private void setInputSetting() {
        setCommonSetting();
        edtInput.setVisibility(View.VISIBLE);
        tvContent.setVisibility(View.GONE);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        edtInput.setTextSize(builder.inputTextSize);
        edtInput.setTextColor(builder.inputTextColor);
        edtInput.setHintTextColor(builder.inputHintTextColor);
        edtInput.setInputType(builder.inputType);
        if (builder.textWatcher != null) {
            edtInput.addTextChangedListener(builder.textWatcher);
        }
        if (ValidUtils.isValid(builder.inputHintText)) {
            edtInput.setHint(builder.inputHintText);
        }
        if (ValidUtils.isValid(builder.inputDefaultText)) {
            edtInput.setText(builder.inputDefaultText);
            edtInput.setSelection(builder.inputDefaultText.length());
        }
    }

    /**
     * 底部弹出类型相关设置
     */
    private void setBottomSetting() {
        llBottom.setVisibility(View.VISIBLE);
        tvCancel.setTextSize(builder.bottomCancelTextSize);
        tvCancel.setTextColor(builder.bottomCancelTextColor);
        if (ValidUtils.isValid(builder.bottomTitleText)) {
            tvCancel.setText(builder.bottomCancelText);
        }
        int size = ValidUtils.isValid(builder.bottomItems) ? builder.bottomItems.size() : 0;
        setItemScrollViewHeight(size);
        llContext.removeAllViews();
        for (int i = 0; i < size; i++) {
            View v = View.inflate(builder.context, R.layout.layout_item_of_dialog_bottom_in, null);
            TextView item = v.findViewById(R.id.tv_text);
            item.setText(builder.bottomItems.get(i));
            item.setTag(i);
            item.setTextColor(builder.bottomItemTextColor);
            item.setTextSize(builder.bottomItemTextSize);
            item.setOnClickListener(v1 -> {
                if (builder.itemClickListener != null) {
                    builder.itemClickListener.onItemClick((int) v1.getTag(), ((TextView) v1).getText().toString());
                }
                dismiss();
            });
            llContext.addView(v);
        }
    }

    @OnClick({R2.id.btn_left, R2.id.btn_right, R2.id.tv_cancel})
    public void onClick(@NonNull View v) {
        if (v.getId() == R.id.btn_left) {
            onButtonClick(builder.letButtonListener);
        } else if (v.getId() == R.id.btn_right) {
            onButtonClick(builder.rightButtonListener);
        } else if (v.getId() == R.id.tv_cancel) {
            dismiss();
        }
    }


    /**
     * 给点击事件设置数据
     *
     * @param listener listener
     */
    private void onButtonClick(OnButtonClickListener listener) {
        if (builder.type == DialogType.INPUT) {
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
     * 设置底部弹出带条目的ScrollView的高度
     *
     * @param size 条目数量
     */
    private void setItemScrollViewHeight(int size) {
        // 添加条目过多的时候控制高度
        if (size >= MAX_ITEM) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bottomScrollView.getLayoutParams();
            params.height = PxUtils.getScreenHeight(builder.context) / 2;
            bottomScrollView.setLayoutParams(params);
        }
    }


    /**
     * 相机的Item
     *
     * @param context context
     * @return List
     */
    @NotNull
    public static List<String> getPhotoItem(@NotNull Context context) {
        List<String> items = new ArrayList<>();
        items.add(context.getString(R.string.camera));
        items.add(context.getString(R.string.album));
        return items;
    }

    /**
     * Describe：对话框建造者
     * Created by 吴天强 on 2018年9月27日
     */
    public static class Builder {
        Context context;
        /**
         * 对话框类型
         */
        @DialogType.Type
        int type = DialogType.DEFAULT;

        /**
         * 文本框输入类型
         */
        int inputType = InputType.TYPE_CLASS_TEXT;
        /**
         * 对话框Title
         */
        String title;
        /**
         * 显示内容文字颜色
         */
        int contentTextColor = Color.BLACK;
        /**
         * 显示内容文字大小
         */
        float contentTextSize = 16;
        /**
         * 对话框内容
         */
        String content;
        /**
         * 左按钮文字
         */
        String letButtonText;
        /**
         * 右按钮文字
         */
        String rightButtonText;
        /**
         * 左按钮事件
         */
        OnButtonClickListener letButtonListener;
        /**
         * 右按钮事件
         */
        OnButtonClickListener rightButtonListener;

        /**
         * 左按钮文字颜色
         */
        int leftButtonTextColor = Color.GRAY;
        /**
         * 右按钮文字颜色
         */
        int rightButtonTextColor = Color.parseColor("#0099FF");
        /**
         * 左按钮文字大小
         */
        float leftButtonTextSize = 18;
        /**
         * 右按钮文字大小
         */
        float rightButtonTextSize = 18;

        /**
         * 底部弹出多条目内容
         */
        List<String> bottomItems;
        /**
         * 底部多条目点击事件
         */
        OnItemClickListener itemClickListener;
        /**
         * 底部弹出对话框标题文字
         */
        String bottomTitleText;
        /**
         * 底部弹出对话框取消按钮文字
         */
        String bottomCancelText;
        /**
         * 底部弹出对话框取消按钮文字颜色
         */
        int bottomCancelTextColor = Color.GRAY;
        /**
         * 底部弹出对话框Item文字颜色
         */
        int bottomItemTextColor = Color.BLACK;
        /**
         * 底部弹出对话框取消按钮文字大小
         */
        float bottomCancelTextSize = 18;
        /**
         * 底部弹出对话框Item文字大小
         */
        float bottomItemTextSize = 16;

        /**
         * 输入框默认文字
         */
        String inputDefaultText;
        /**
         * 输入框hint文字
         */
        String inputHintText;
        /**
         * 输入框文字颜色
         */
        int inputTextColor = Color.BLACK;
        /**
         * 输入框Hint文字颜色
         */
        int inputHintTextColor = Color.GRAY;
        /**
         * 输入框文字大小
         */
        float inputTextSize = 16;
        /**
         * 输入框输入监听
         */
        TextWatcher textWatcher;
        /**
         * 替换整个Dialog内容的View
         */
        View allDialogView;
        /**
         * 替换中间部分View
         */
        View contentView;
        /**
         * 替换底部弹出View
         */
        View bottomView;

        /**
         * 是否单按钮
         */
        boolean isSingleButton;

        /**
         * 是否可以取消
         */
        boolean cancelable = true;

        /**
         * 点击对话框周围是否可取消
         */
        boolean canceledOnTouchOutside = true;

        /**
         * 对话框消失监听
         */
        DialogInterface.OnDismissListener dialogDismissListener;

        /**
         * 初始化
         *
         * @param context 上下文
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置对话框类型
         *
         * @param type DialogType
         * @return Builder
         */
        public Builder setDialogType(@DialogType.Type int type) {
            this.type = type;
            return this;
        }

        /**
         * 设置文本框输入类型
         *
         * @param inputType 输入类型
         * @return Builder
         */
        public Builder setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        /**
         * 设置对话框标题
         *
         * @param title 标题
         * @return Builder
         */
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * 设置对话框内容
         *
         * @param content 内容
         * @return Builder
         */
        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * 设置显示内容文字颜色
         *
         * @param color 颜色
         * @return Builder
         */
        public Builder setContentTextColor(@ColorInt int color) {
            this.contentTextColor = color;
            return this;
        }

        /**
         * 设置显示内容文字大小
         *
         * @param contentTextSize 大小
         * @return Builder
         */
        public Builder setContentTextSize(float contentTextSize) {
            this.contentTextSize = contentTextSize;
            return this;
        }
        ////////////////////////////////////////左按钮设置////////////////////////////////////////////

        /**
         * 设置左按钮
         *
         * @param text 显示文字
         * @return Builder
         */
        public Builder setLeftButton(String text) {
            return setLeftButton(text, null);
        }

        /**
         * 设置左按钮
         *
         * @param listener 监听事件
         * @return Builder
         */
        public Builder setLeftButton(OnButtonClickListener listener) {
            return setLeftButton(null, listener);
        }

        /**
         * 设置左按钮
         *
         * @param text     显示文字
         * @param listener 监听事件
         * @return Builder
         */
        public Builder setLeftButton(String text, OnButtonClickListener listener) {
            this.letButtonText = text;
            this.letButtonListener = listener;
            return this;
        }

        /**
         * 设置左按钮文字颜色
         *
         * @param color 颜色
         * @return Builder
         */
        public Builder setLeftButtonTextColor(@ColorInt int color) {
            this.leftButtonTextColor = color;
            return this;
        }

        /**
         * 设置左按钮文字大小
         *
         * @param size 大小
         * @return Builder
         */
        public Builder setLeftButtonTextSize(float size) {
            this.leftButtonTextSize = size;
            return this;
        }

        ////////////////////////////////////////右按钮设置////////////////////////////////////////////

        /**
         * 设置右按钮
         *
         * @param text 显示文字
         * @return Builder
         */
        public Builder setRightButton(String text) {
            return setRightButton(text, null);
        }

        /**
         * 设置右按钮
         *
         * @param listener 监听事件
         * @return Builder
         */
        public Builder setRightButton(OnButtonClickListener listener) {
            return setRightButton(null, listener);
        }

        /**
         * 设置右按钮
         *
         * @param text     显示文字
         * @param listener 监听事件
         * @return Builder
         */
        public Builder setRightButton(String text, OnButtonClickListener listener) {
            this.rightButtonText = text;
            this.rightButtonListener = listener;
            return this;
        }

        /**
         * 设置右按钮文字颜色
         *
         * @param color 颜色
         * @return Builder
         */
        public Builder setRightButtonTextColor(@ColorInt int color) {
            this.rightButtonTextColor = color;
            return this;
        }

        /**
         * 设置右按钮文字大小
         *
         * @param size 大小
         * @return Builder
         */
        public Builder setRightButtonTextSize(float size) {
            this.rightButtonTextSize = size;
            return this;
        }
        ////////////////////////////////////////单按钮设置////////////////////////////////////////////

        /**
         * 单按钮设置
         *
         * @param text 显示文字
         * @return Builder
         */
        public Builder setSingleButton(String text) {
            return setSingleButton(text, null);
        }

        /**
         * 单按钮设置
         *
         * @param listener 事件监听
         * @return Builder
         */
        public Builder setSingleButton(OnButtonClickListener listener) {
            return setSingleButton(null, listener);
        }

        /**
         * 单按钮设置
         *
         * @param text     显示文字
         * @param listener 事件监听
         * @return Builder
         */
        public Builder setSingleButton(String text, OnButtonClickListener listener) {
            this.isSingleButton = true;
            return setRightButton(text, listener);
        }

        /**
         * 设置单按钮文字颜色
         *
         * @param color 颜色
         * @return Builder
         */
        public Builder setSingleButtonTextColor(@ColorInt int color) {
            return setRightButtonTextColor(color);
        }

        /**
         * 设置单按钮文字大小
         *
         * @param size 大小
         * @return Builder
         */
        public Builder setSingleButtonTextSize(float size) {
            return setRightButtonTextSize(size);
        }

        ////////////////////////////////////////多条目设置////////////////////////////////////////////

        /**
         * 当type = 底部弹出的时候，设置弹出条目及监听
         *
         * @param items    弹出条目
         * @param listener 监听事件
         * @return Builder
         */
        public Builder setBottomItems(List<String> items, OnItemClickListener listener) {
            this.bottomItems = items;
            this.itemClickListener = listener;
            return this;
        }

        /**
         * 设置底部弹出对话框取消按钮文字
         *
         * @param text 显示文字
         * @return Builder
         */
        public Builder setBottomCancelText(String text) {
            this.bottomCancelText = text;
            return this;
        }

        /**
         * 设置底部弹出对话框标题文字
         *
         * @param text 显示文字
         * @return Builder
         */
        public Builder setBottomTitleText(String text) {
            this.bottomTitleText = text;
            return this;
        }

        /**
         * 设置底部弹出对话框取消按钮文字颜色
         *
         * @param color 颜色
         * @return Builder
         */
        public Builder setBottomCancelTextColor(@ColorInt int color) {
            this.bottomCancelTextColor = color;
            return this;
        }

        /**
         * 设置底部弹出对话框Item文字颜色
         *
         * @param color 颜色
         * @return Builder
         */
        public Builder setBottomItemTextColor(@ColorInt int color) {
            this.bottomItemTextColor = color;
            return this;
        }

        /**
         * 设置底部弹出对话框取消按钮文字大小
         *
         * @param size 大小
         * @return Builder
         */
        public Builder setBottomCancelTextSize(float size) {
            this.bottomCancelTextSize = size;
            return this;
        }

        /**
         * 设置底部弹出对话框item文字大小
         *
         * @param size 大小
         * @return Builder
         */
        public Builder setBottomItemTextSize(float size) {
            this.bottomItemTextSize = size;
            return this;
        }

        ////////////////////////////////////////输入框设置////////////////////////////////////////////

        /**
         * 设置输入框默认文字
         *
         * @param text 默认文字
         * @return Builder
         */
        public Builder setInputDefaultText(String text) {
            this.inputDefaultText = text;
            return this;
        }

        /**
         * 设置Hint文字
         *
         * @param text Hint 文字
         * @return Builder
         */
        public Builder setInputHintText(String text) {
            this.inputHintText = text;
            return this;
        }

        /**
         * 设置输入框文字颜色
         *
         * @param color 文字颜色
         * @return Builder
         */
        public Builder setInputTextColor(@ColorInt int color) {
            this.inputTextColor = color;
            return this;
        }

        /**
         * 设置输入框Hint文字颜色
         *
         * @param color Hint文字颜色
         * @return Builder
         */
        public Builder setInputHintTextColor(@ColorInt int color) {
            this.inputHintTextColor = color;
            return this;
        }

        /**
         * 设置输入框文字大小
         *
         * @param size 大小
         * @return Builder
         */
        public Builder setInputTextSize(float size) {
            this.inputTextSize = size;
            return this;
        }

        /**
         * 设置输入框输入监听
         *
         * @param textWatcher 监听
         * @return Builder
         */
        public Builder setTextWatcher(TextWatcher textWatcher) {
            this.textWatcher = textWatcher;
            return this;
        }
        ////////////////////////////////////////其他设置////////////////////////////////////////////

        /**
         * 替换整个Dialog 内容
         *
         * @param view view
         * @return Builder
         */
        public Builder addAllDialogView(View view) {
            this.allDialogView = view;
            this.type = DialogType.REPLACE_ALL;
            return this;
        }

        /**
         * 替换中间部分View
         *
         * @param view view
         * @return Builder
         */
        public Builder addContentView(View view) {
            this.contentView = view;
            this.type = DialogType.REPLACE_CONTENT;
            return this;
        }

        /**
         * 替换底部弹出的View
         *
         * @param view view
         * @return Builder
         */
        public Builder setBottomView(View view) {
            this.bottomView = view;
            this.type = DialogType.REPLACE_BOTTOM;
            return this;
        }

        /**
         * 是否可取消
         *
         * @param cancelable 可取消
         * @return Builder
         */
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        /**
         * 点击对话框周围是否可取消
         *
         * @param canceledOnTouchOutside 可取消
         * @return Builder
         */
        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        /**
         * 对话框消失监听
         *
         * @param dialogDismissListener 监听
         * @return Builder
         */
        public Builder setDialogDismissListener(DialogInterface.OnDismissListener dialogDismissListener) {
            this.dialogDismissListener = dialogDismissListener;
            return this;
        }

        /**
         * 创建对话框
         *
         * @return AppDialog
         */
        public AppDialog create() {
            return new AppDialog(this);
        }
    }


}