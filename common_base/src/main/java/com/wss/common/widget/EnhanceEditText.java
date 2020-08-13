package com.wss.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.utils.KeyboardUtils;
import com.wss.common.utils.ValidUtils;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe 增强版输入框，左右带图标
 * Created by 吴天强 on 2020/4/20.
 */
public class EnhanceEditText extends LinearLayout {
    /**
     * 无操作
     */
    public static final int ACTION_NONE = 0;
    /**
     * 搜索事件
     */
    public static final int ACTION_SEARCH = 1;
    /**
     * 下一步
     */
    public static final int ACTION_NEXT = 2;
    /**
     * 完成
     */
    public static final int ACTION_DONE = 3;

    /**
     * 输入文本
     */
    public static final int INPUT_TEXT = 0;
    /**
     * 输入密码
     */
    public static final int INPUT_PASSWORD = 1;

    /**
     * 输入手机
     */
    public static final int INPUT_PHONE = 2;
    /**
     * 输入数字
     */
    public static final int INPUT_NUMBER = 3;


    @BindView(R2.id.ll_edt_view)
    LinearLayout llEdtView;

    @BindView(R2.id.iftv_left)
    IconFontTextView iftvLeft;

    @BindView(R2.id.edt_text)
    EditText editText;

    @BindView(R2.id.iftv_clean)
    IconFontTextView iftvClean;

    @BindView(R2.id.ll_clean_view)
    View cleanView;

    private int textColor = 0xFF333333;
    private int hintColor = 0XFF999999;
    private int leftIconColor = 0XFF999999;
    private int cleanIconColor = 0XFF999999;
    private String leftIcon;
    private String cleanIcon;
    private String hintText;
    private int textSize = 14;
    /**
     * 背景资源
     */
    private int bgResource = R.drawable.bg_of_color_f5_4radius;
    /**
     * 是否显示清除按钮 默认true
     */
    private boolean showCleanIcon = true;
    /**
     * 是否现在左侧icon 默认true
     */
    private boolean showLeftIcon = true;
    /**
     * 是否显示背景 默认true
     */
    private boolean showBackground = true;
    /**
     * 键盘最后执行的Action
     */
    private int action = ACTION_NONE;
    /**
     * 输入框类型
     */
    private int inputType = INPUT_TEXT;
    private int maxLength;
    private int maxLines;

    private OnTextChangeListener onTextChangeListener;
    private OnActionClickListener onActionClickListener;


    public EnhanceEditText(Context context) {
        this(context, null);
    }

    public EnhanceEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnhanceEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ButterKnife.bind(this, View.inflate(getContext(), R.layout.layout_search, this));
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EnhanceEditText);
        textColor = a.getColor(R.styleable.EnhanceEditText_eTextColor, textColor);
        hintColor = a.getColor(R.styleable.EnhanceEditText_eHintTextColor, hintColor);
        leftIcon = a.getString(R.styleable.EnhanceEditText_eLeftIconFont);
        leftIconColor = a.getColor(R.styleable.EnhanceEditText_eLeftIconColor, leftIconColor);
        cleanIcon = a.getString(R.styleable.EnhanceEditText_eCleanIconFont);
        cleanIconColor = a.getColor(R.styleable.EnhanceEditText_eCleanIconColor, cleanIconColor);
        hintText = a.getString(R.styleable.EnhanceEditText_eHintText);
        textSize = a.getDimensionPixelSize(R.styleable.EnhanceEditText_eTextSize, textSize);
        showCleanIcon = a.getBoolean(R.styleable.EnhanceEditText_eShowClean, showCleanIcon);
        bgResource = a.getResourceId(R.styleable.EnhanceEditText_eBg, bgResource);
        showLeftIcon = a.getBoolean(R.styleable.EnhanceEditText_eShowLeft, showLeftIcon);
        showBackground = a.getBoolean(R.styleable.EnhanceEditText_eShowBg, showBackground);
        action = a.getInt(R.styleable.EnhanceEditText_eAction, action);
        inputType = a.getInt(R.styleable.EnhanceEditText_eInputType, inputType);
        maxLength = a.getInt(R.styleable.EnhanceEditText_eMaxLength, maxLength);
        maxLines = a.getInt(R.styleable.EnhanceEditText_eMaxLines, maxLines);
        a.recycle();
        initView();
    }

    private void initView() {
        setGravity(Gravity.CENTER_VERTICAL);
        if (showBackground) {
            setBackgroundResource(bgResource);
        }
        if (showLeftIcon && ValidUtils.isValid(leftIcon)) {
            iftvLeft.setValue(leftIcon);
            iftvLeft.setTextColor(leftIconColor);
        } else {
            iftvLeft.setVisibility(VISIBLE);
        }
        if (showCleanIcon && ValidUtils.isValid(cleanIcon)) {
            iftvClean.setValue(cleanIcon);
            iftvClean.setTextColor(cleanIconColor);
        }
        editText.setHint(hintText);
        editText.setTextSize(textSize);
        editText.setTextColor(textColor);
        editText.setHintTextColor(hintColor);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.beforeTextChanged(s, start, count, after);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.onTextChanged(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onTextChangeListener != null) {
                    onTextChangeListener.afterTextChanged(s);
                }
                if (showCleanIcon) {
                    cleanView.setVisibility(s.toString().length() > 0 ? VISIBLE : GONE);
                }
            }
        });
        setInputFiller();
        setOnEditorAction();
    }

    /**
     * 设置输入框的一些属性
     */
    private void setInputFiller() {
        switch (inputType) {
            case INPUT_PASSWORD:
                editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case INPUT_PHONE:
                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;
            case INPUT_NUMBER:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case INPUT_TEXT:
            default:
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
        }
        if (maxLength > 0) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if (maxLines > 0) {
            editText.setMaxLines(maxLines);
        }
    }

    /**
     * 设置输入框的执行事件
     */
    private void setOnEditorAction() {
        switch (action) {
            case ACTION_SEARCH:
                editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                break;
            case ACTION_NEXT:
                editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                break;
            case ACTION_DONE:
                editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                break;
            case ACTION_NONE:
            default:
                editText.setImeOptions(EditorInfo.IME_ACTION_NONE);
                break;
        }

        editText.setOnEditorActionListener((v, actionId, event) -> {
            KeyboardUtils.hideKeyboard(v);
            if (onActionClickListener == null) {
                return false;
            }
            switch (actionId) {
                case EditorInfo.IME_ACTION_SEARCH:
                    //搜索
                    onActionClickListener.onEditorAction(ACTION_SEARCH);
                    break;
                case EditorInfo.IME_ACTION_NEXT:
                    //下一步
                    onActionClickListener.onEditorAction(ACTION_NEXT);
                    break;
                case EditorInfo.IME_ACTION_DONE:
                    //完成
                    onActionClickListener.onEditorAction(ACTION_DONE);
                    break;
                case EditorInfo.IME_ACTION_NONE:
                default:
                    //无操作
                    onActionClickListener.onEditorAction(ACTION_NONE);
                    break;
            }
            return true;
        });
    }

    @OnClick(R2.id.ll_clean_view)
    public void onClean() {
        getEditText().setText("");
    }


    /**
     * 返回输入框字符
     *
     * @return String
     */
    public String getText() {
        return getEditText().getText().toString().trim();
    }

    /**
     * 清除搜索框内容
     */
    public void clearSearchText() {
        onClean();
    }

    /**
     * 返回输入框View
     *
     * @return EditText
     */
    public EditText getEditText() {
        return editText;
    }

    /**
     * 设置搜索按钮点击回调
     *
     * @param onActionClickListener OnEditActionCallback
     */
    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    /**
     * 设置文本输入变化监听
     *
     * @param textChangeListener listener
     */
    public void setTextChangeListener(OnTextChangeListener textChangeListener) {
        this.onTextChangeListener = textChangeListener;
    }

    /**
     * 设置文本
     *
     * @param text 文本
     */
    public void setText(CharSequence text) {
        editText.setText(text);
    }

    /**
     * 设置输入框光标位置
     *
     * @param position 位置
     */
    public void setSelection(int position) {
        editText.setSelection(position);
    }

    /**
     * 文本输入变化监听
     */
    public interface OnTextChangeListener {
        /**
         * 输入前
         *
         * @param s     s
         * @param start start
         * @param count count
         * @param after after
         */
        void beforeTextChanged(CharSequence s, int start, int count, int after);

        /**
         * 变化
         *
         * @param s      s
         * @param start  start
         * @param before before
         * @param count  count
         */
        void onTextChanged(CharSequence s, int start, int before, int count);

        /**
         * 变化后
         *
         * @param s 文本
         */
        void afterTextChanged(Editable s);
    }

    /**
     * 键盘搜索按钮点击
     */
    public interface OnActionClickListener {
        /**
         * 按钮被点击
         *
         * @param action 事件ID
         */
        void onEditorAction(int action);
    }
}
