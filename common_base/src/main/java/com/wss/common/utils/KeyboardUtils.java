package com.wss.common.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：键盘辅助类
 * Created by 吴天强 on 2017/11/2.
 */
public class KeyboardUtils {

    /**
     * 显示键盘
     *
     * @param view view
     */
    public static void showKeyboard(@NotNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view view
     */
    public static void hideKeyboard(@NotNull View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}