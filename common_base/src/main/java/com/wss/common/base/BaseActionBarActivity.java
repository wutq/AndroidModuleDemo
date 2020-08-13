package com.wss.common.base;

import android.os.Bundle;
import android.view.View;

import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.ActionBar;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * Describe：所有带actionBar的Activity基类
 * Created by 吴天强 on 2018/10/18.
 */
public abstract class BaseActionBarActivity<P extends BasePresenter> extends BaseMvpActivity<P> {

    @BindView(R2.id.actionbar)
    ActionBar actionBar;

    @BindView(R2.id.action_bar_line)
    View actionBarLine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.setVisibility(View.VISIBLE);
        //沉浸式状态栏
        setImmersionBarColor(R.color.white);
    }


    /**
     * 设置左边文字
     *
     * @param text 文字
     */
    protected void setLeftText(String text) {
        setLeftText(text, null);
    }

    /**
     * @param text     文字
     * @param listener 事件监听
     */
    protected void setLeftText(String text, View.OnClickListener listener) {
        setLeftText(text, R.color.color_333333, listener);
    }

    /**
     * 设置左边文字
     *
     * @param text      文字
     * @param textColor 文字颜色
     * @param listener  事件监听
     */
    protected void setLeftText(String text, int textColor, View.OnClickListener listener) {
        if (actionBar != null) {
            if (textColor != 0) {
                actionBar.setLeftTextColor(getResources().getColor(textColor));
            }
            actionBar.setLeftText(text, listener);
        }
    }

    /**
     * 设置左边icon
     *
     * @param drawable icon resId
     * @param listener 事件监听
     */
    protected void setLeftIcon(int drawable, View.OnClickListener listener) {
        if (actionBar != null) {
            actionBar.setLeftIcon(drawable, listener);
        }
    }

    /**
     * 设置左边icon
     *
     * @param listener 事件监听
     */
    protected void setLeftIcon(View.OnClickListener listener) {
        setLeftIcon(R.drawable.ic_back_black, listener);
    }

    /**
     * 设置左边View
     *
     * @param view view
     */
    protected void setLeftView(View view) {
        if (actionBar != null) {
            actionBar.setLeftView(view);
        }
    }

    /**
     * 设置中间文字
     *
     * @param text text
     */
    protected void setCenterText(int text) {
        setCenterText(getString(text));
    }

    /**
     * 设置中间文字
     *
     * @param text text
     */
    protected void setCenterText(String text) {
        setCenterText(text, R.color.color_333333);
    }

    /**
     * 设置中间文字
     *
     * @param text      text
     * @param textColor 文字颜色
     */
    protected void setCenterText(String text, int textColor) {
        setCenterText(text, textColor, null);
    }

    /**
     * 设置中间文字
     *
     * @param text      text
     * @param textColor 文字颜色
     * @param listener  事件监听
     */
    protected void setCenterText(String text, int textColor, View.OnClickListener listener) {
        if (actionBar != null) {
            if (textColor != 0) {
                actionBar.setCenterTextColor(textColor);
            }
            //加粗
            actionBar.setCenterTextBold(true);
            actionBar.setCenterText(text, listener);
        }
    }

    /**
     * 设置中间View
     *
     * @param view view
     */
    protected void setCenterView(View view) {
        if (actionBar != null) {
            actionBar.setCenterView(view);
        }
    }

    /**
     * 设置右边文字
     *
     * @param resId    文字
     * @param listener 事件监听
     */
    protected void setRightText(int resId, View.OnClickListener listener) {
        setRightText(getString(resId), listener);
    }

    /**
     * 设置右边文字
     *
     * @param text     文字
     * @param listener 事件监听
     */
    protected void setRightText(String text, View.OnClickListener listener) {
        setRightText(text, R.color.color_333333, listener);
    }

    /**
     * 设置右边文字
     *
     * @param text      文字
     * @param textColor 文字颜色
     * @param listener  事件监听
     */
    protected void setRightText(String text, int textColor, View.OnClickListener listener) {
        if (actionBar != null) {
            if (textColor != 0) {
                actionBar.setRightTextColor(textColor);
            }
            actionBar.setRightText(text, listener);
        }
    }

    /**
     * 设置右边icon
     *
     * @param drawable 图片
     * @param listener 事件监听
     */
    protected void setRightIcon(int drawable, View.OnClickListener listener) {
        if (actionBar != null) {
            actionBar.setRightIcon(drawable, listener);
        }
    }

    /**
     * 设置右边View
     *
     * @param view view
     */
    protected void setRightView(View view) {
        if (actionBar != null) {
            actionBar.setRightView(view);
        }
    }

    /**
     * 返回顶部TitleBar
     *
     * @return ActionBar
     */
    protected ActionBar getTitleBar() {
        return actionBar;
    }

    /**
     * 设置ActionBar下面的横线
     *
     * @param show 是否显示
     */
    protected void showActionBarLine(boolean show) {
        actionBarLine.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置左边返回图标是否显示
     *
     * @param show 是否显示
     */
    public void showBackImg(boolean show) {
        actionBar.showBackImg(show);
    }
}
