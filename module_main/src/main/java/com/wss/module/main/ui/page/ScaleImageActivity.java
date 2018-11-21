package com.wss.module.main.ui.page;

import android.view.View;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.net.HttpUtils;
import com.wss.common.net.NetConfig;
import com.wss.common.net.callback.OnResultStringCallBack;
import com.wss.common.utils.Utils;
import com.wss.common.widget.scaleImg.ImageViewer;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Describe：放大图片查看器
 * Created by 吴天强 on 2018/11/16.
 */

public class ScaleImageActivity extends ActionBarActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_scale_image;
    }

    @Override
    protected void initView() {
        setTitleText("图片查看器");
    }


    @OnClick({R2.id.btn_01, R2.id.btn_02})
    public void onViewClicked(View v) {
        if (v.getId() == R.id.btn_01) {
            List<String> urls = new ArrayList<>();
            urls.add("http://www.17qq.com/img_qqtouxiang/76490995.jpeg");
            urls.add("http://tupian.qqjay.com/u/2017/1201/2_161641_2.jpg");
            urls.add("http://img5.imgtn.bdimg.com/it/u=2149604370,3833220711&fm=11&gp=0.jpg");
            urls.add("http://img21.mtime.cn/CMS/Gallery/2011/11/09/124534.19687965_900.jpg");
            urls.add("http://wx4.sinaimg.cn/large/3d414762ly1fjvsvapzxuj20dw0dw76q.jpg");
            ImageViewer.create(this)
                    .setUrls(urls)
                    .build();
        } else if (v.getId() == R.id.btn_02) {

            HttpUtils.getInstance(mContext)
                    .setBaseUrl(NetConfig.Url.MY_SERVICE_URL)
                    .getRequest("/app/checkUpdate.json?versionCode=" + Utils.getVersionCode(), new

                            OnResultStringCallBack() {
                                @Override
                                public void onSuccess(boolean success, int code, String msg, Object tag, String response) {

                                }

                                @Override
                                public void onFailure(Object tag, Exception e) {

                                }

                                @Override
                                public void onCompleted() {

                                }
                            });

        }

    }
}
