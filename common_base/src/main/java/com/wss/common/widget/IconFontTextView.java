package com.wss.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.wss.common.base.R;
import com.wss.common.utils.ValidUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Describe：使用ali IconFont的TextView
 * Created by 吴天强 on 2019/7/9.
 */
public class IconFontTextView extends AppCompatTextView {

    private Context context;
    private String fontFile = "iconfont/iconfont.ttf";
    private String defaultText;//默认文字
    private String checkedText;//选择之后文字

    public IconFontTextView(Context context) {
        this(context, null);
    }

    public IconFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IconFontTextView);
            defaultText = a.getString(R.styleable.IconFontTextView_value);
            checkedText = a.getString(R.styleable.IconFontTextView_valueChecked);
            setFontFile(a.getString(R.styleable.IconFontTextView_fontFile));
            a.recycle();
        }
        setTextState();
        setTypeFace();
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setTextState();
    }

    /***
     * 设置显示Value
     */
    public void setValue(String defaultText) {
        this.defaultText = defaultText;
        setTextState();
    }

    /***
     * 设置显示Value
     * @param  defaultText 默认显示
     * @param checkedText 选中显示
     */
    public void setValue(String defaultText, String checkedText) {
        this.defaultText = defaultText;
        this.checkedText = checkedText;
        setTextState();
    }


    /**
     * 设置字体库
     */
    public void setFontFile(String typeFacePath) {
        if (ValidUtils.isValid(typeFacePath)) {
            fontFile = typeFacePath;
        }
        setTypeFace();
    }

    private void setTextState() {
        if (ValidUtils.isValid(checkedText)) {
            setText(isSelected() ? checkedText : defaultText);
        } else {
            setText(defaultText);
        }
    }

    private void setTypeFace() {
        try {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontFile);
            setTypeface(typeface);
        } catch (Throwable ignored) {
        }
    }
}
