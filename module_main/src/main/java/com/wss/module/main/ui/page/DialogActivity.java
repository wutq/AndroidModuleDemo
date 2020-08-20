package com.wss.module.main.ui.page;

import android.view.View;

import com.hjq.permissions.Permission;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.PermissionsUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.common.widget.dialog.DialogType;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import butterknife.OnClick;

/**
 * Describe：对话框
 * Created by 吴天强 on 2018/10/26.
 */
public class DialogActivity extends BaseActionBarActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_dialog;
    }

    @Override
    protected void initView() {
        setCenterText("对话框");
        PermissionsUtils.checkPermissions(this, Permission.WRITE_EXTERNAL_STORAGE).subscribe();
    }

    @OnClick({R2.id.btn_01, R2.id.btn_02, R2.id.btn_04, R2.id.btn_05, R2.id.btn_06, R2.id.btn_07})
    public void onBtnClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_01) {
            new AppDialog.Builder(context)
                    .setContent("我是默认的")
                    .create()
                    .show();
        } else if (id == R.id.btn_02) {
            new AppDialog.Builder(context)
                    .setDialogType(DialogType.INPUT)
                    .setTitle("输入文字")
                    .setInputHintText("I am placeholder!")
                    .setInputHintTextColor(ContextCompat.getColor(context, R.color.red))
                    .setInputDefaultText("我是带入的文本框默认的值")
                    .setInputTextColor(ContextCompat.getColor(context, R.color.color_333333))
                    .setLeftButton("再见")
                    .setRightButton("OVER", ToastUtils::show)
                    .create()
                    .show();
        } else if (id == R.id.btn_04) {
            new AppDialog.Builder(context)
                    .setTitle("单个按钮")
                    .setContent("这是一段没有意义的文字")
                    .setSingleButton(val -> ToastUtils.show("消失啦"))
                    .create()
                    .show();
        } else if (id == R.id.btn_05) {
            new AppDialog.Builder(context)
                    .setDialogType(DialogType.NO_TITLE)
                    .setContent("我没有title,点我也没啥用")
                    .create()
                    .show();

        } else if (id == R.id.btn_06) {
            final List<String> list = new ArrayList<>();
            list.add("相机");
            list.add("相册");
            new AppDialog.Builder(context)
                    .setDialogType(DialogType.BOTTOM_IN)
                    .setTitle("顶部弹出多选项")
                    .setBottomItems(list, (position, val) -> ToastUtils.show(val))
                    .setBottomCancelText("再见")
                    .create()
                    .show();

        } else if (id == R.id.btn_07) {
            new AppDialog.Builder(context)
                    .setTitle("警告")
                    .setContent("你要么回家，要么挨打")
                    .setLeftButton("挨打", val -> ToastUtils.show("成，那屁股撅起来"))
                    .setRightButton("回家", val -> ToastUtils.show("诶，这就对喽"))
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(false)
                    .create()
                    .show();
        }
    }
}
