package com.wss.common.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wss.common.base.R;


/**
 * Describe：Toast
 * Created by 吴天强 on 2017/11/2.
 */

public class ToastUtils {

    public static void show(Context context, int strings) {
        show(context, context.getString(strings));
    }

    public static void show(Context context, String text) {
//        show(context, title, Gravity.BOTTOM);
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义显示Toast弹出提示框
     *
     * @param s       s
     * @param gravity 弹出位置
     */

    public static void show(Context context, CharSequence s, int gravity) {
        Toast toast = new Toast(context);
        View toastView = View.inflate(context, R.layout.toast, null);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        TextView textView = toastView.findViewById(R.id.tv_message);
        textView.setText(s);
        toast.setGravity(gravity, 0, 100);
        toast.show();
    }
}
