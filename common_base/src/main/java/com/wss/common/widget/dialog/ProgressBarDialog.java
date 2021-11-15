package com.wss.common.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.utils.ValidUtils;
import com.wss.common.widget.NumberProgressBar;

/**
 * Describe：带进度条的加载框
 * Created by 吴天强 on 2021/5/10.
 */
public class ProgressBarDialog {
    private AppDialog progressDialog;
    private NumberProgressBar progressBar;
    private Builder builder;

    private ProgressBarDialog() {
    }

    private ProgressBarDialog(Builder builder) {
        this.builder = builder;
    }

    /**
     * 设置进度
     *
     * @param progress 进度值
     */
    public void setProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
        }
    }

    /**
     * 显示加载库
     */
    public void show() {
        View progressView = View.inflate(builder.context, R.layout.dialog_progressbar, null);
        progressBar = progressView.findViewById(R.id.number_progress);
        if (ValidUtils.isValid(builder.loadingText)) {
            ((TextView) progressView.findViewById(R.id.tv_loading_text)).setText(builder.loadingText);
        }
        progressBar.setProgress(0);
        progressDialog = new AppDialog.Builder(builder.context)
                .addAllDialogView(progressView)
                .setCancelable(builder.cancelable)
                .create();
        progressDialog.show();
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Describe：对话框建造者
     * Created by 吴天强 on 2018年9月27日
     */
    public static class Builder {
        Context context;
        /**
         * 加载Text
         */
        String loadingText;
        /**
         * 是否可以取消
         */
        boolean cancelable;

        /**
         * 初始化
         *
         * @param context 上下文
         */
        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置对话框内容
         *
         * @param loadingText 加载文案
         * @return Builder
         */
        public Builder setLoadingText(String loadingText) {
            this.loadingText = loadingText;
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
         * 创建对话框
         *
         * @return AppDialog
         */
        public ProgressBarDialog create() {
            return new ProgressBarDialog(this);
        }
    }
}
