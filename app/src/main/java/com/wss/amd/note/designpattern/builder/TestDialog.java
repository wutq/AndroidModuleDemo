package com.wss.amd.note.designpattern.builder;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wss.amd.R;

/**
 * Describe：
 * Created by 吴天强 on 2022/1/17.
 */
public class TestDialog {

    private Builder builder;

    private TestDialog(Builder builder) {
        this.builder = builder;
    }


    public void show() {
        View dialogView = View.inflate(builder.context, R.layout.dialog_app, null);
        ((TextView) dialogView.findViewById(R.id.tv_content)).setText(builder.content);
        Dialog dialog = new Dialog(builder.context, R.style.DialogStyle);
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.CENTER;
        }
        dialog.setCancelable(builder.canCancel);
        dialog.show();
    }

    public static class Builder {

        private Context context;
        private String content;
        private boolean canCancel;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setCanCancel(boolean canCancel) {
            this.canCancel = canCancel;
            return this;
        }


        public TestDialog build() {
            return new TestDialog(this);
        }

    }
}
