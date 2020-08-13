package com.wss.common.view.gallery;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.orhanobut.logger.Logger;
import com.wss.common.base.BaseFullScreenActivity;
import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.bean.Event;
import com.wss.common.constants.Dic;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.common.widget.ActionBar;
import com.wss.common.widget.IconFontTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe：图片画廊
 * Created by 吴天强 on 2020/4/29.
 */
@SuppressWarnings("unchecked")
public class ImageGalleryActivity extends BaseFullScreenActivity {

    @BindView(R2.id.rl_title_bar)
    View titleBarLayout;

    @BindView(R2.id.actionbar)
    ActionBar actionBar;

    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    @BindView(R2.id.tv_img_count)
    TextView tvImgCount;

    @BindView(R2.id.tv_describe)
    TextView tvDescribe;

    private Unbinder butterKnifeBind;
    private Activity activity;

    /***
     * 图片集合
     */
    private List<ImageGallery> imageList = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();

    private int currentPosition;

    private ImageViewerAdapter mAdapter;
    /**
     * 是否是本地图片
     */
    private boolean isLocalImage;
    /**
     * 是否全屏显示
     */
    private boolean isFullScreen;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isFullScreen = getIntent().getBooleanExtra(Dic.FULL_SCREEN, false);
        if (isFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);
        activity = this;
        butterKnifeBind = ButterKnife.bind(this);
        Collection<? extends ImageGallery> serializableExtra = (Collection<? extends ImageGallery>) getIntent().getSerializableExtra(Dic.IMAGE_LIST);
        if (serializableExtra != null) {
            imageList.addAll(serializableExtra);
        } else {
            ToastUtils.show(context, getString(R.string.please_setting_image));
            finish();
        }
        isLocalImage = getIntent().getBooleanExtra(Dic.IMAGE_LOCAL, false);
        currentPosition = getIntent().getIntExtra(Dic.IMAGE_POSITION, 0);
        loadImage();
        mAdapter = new ImageViewerAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(currentPosition);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                setImageChangeText();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (!isFullScreen) {
            titleBarLayout.setVisibility(View.VISIBLE);
            tvImgCount.setVisibility(View.GONE);
            IconFontTextView rightIcon = new IconFontTextView(context);
            rightIcon.setTextColor(getResources().getColor(R.color.color_333333));
            rightIcon.setTextSize(14);
            rightIcon.setValue(getString(R.string.icon_delete));
            rightIcon.setOnClickListener(v -> {
                //删除照片发个通知出去 带上删除的position
                ToastUtils.show(context, getString(R.string.delete_success));
                EventBusUtils.sendEvent(new Event(EventAction.IMAGE_GALLERY_DELETE, currentPosition));
                imageList.remove(currentPosition);
                int size = imageList.size();
                if (size < 1) {
                    finish();
                    return;
                }
                mViewPager.removeView(mViews.remove(currentPosition));
                mAdapter.notifyDataSetChanged();
                setImageChangeText();
            });
            actionBar.setRightView(rightIcon);
            actionBar.setLeftIcon(R.drawable.ic_back_black, v -> {

            });
            actionBar.setCenterTextColor(R.color.color_333333);
            //加粗
            actionBar.setCenterTextBold(true);
            actionBar.showBackImg(true);
        }
        setImageChangeText();
    }

    private void setImageChangeText() {
        if (isFullScreen) {
            tvImgCount.setText(String.format("%s/%s", currentPosition + 1, imageList.size()));
        } else {
            actionBar.setCenterText(String.format("%s/%s", currentPosition + 1, imageList.size()));
        }
        tvDescribe.setText(imageList.get(currentPosition).getDescribe());
    }

    private void loadImage() {
        for (ImageGallery imageGallery : imageList) {
            SubsamplingScaleImageView scaleImageView = new SubsamplingScaleImageView(context);
            scaleImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if (isFullScreen) {
                scaleImageView.setOnClickListener(v -> finish());
            }

            if (isLocalImage) {
                scaleImageView.setImage(ImageSource.uri(Uri.fromFile(new File(imageGallery.getUrl()))));
            } else {
                if (!ValidUtils.isValid(imageGallery.getUrl())) {
                    return;
                }
                IOThread.getSingleThread().execute(() -> {
                    File downLoadFile;
                    try {
                        downLoadFile = mImageDownloader.downLoad(imageGallery.getUrl(), activity);
                        //如果图片路径不为空则开始执行
                        if (ValidUtils.isValid(downLoadFile)) {
                            activity.runOnUiThread(() -> scaleImageView.setImage(ImageSource.uri(Uri.fromFile(downLoadFile))));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Logger.e("加载图片异常" + e.getMessage());
                    }
                });
            }
            mViews.add(scaleImageView);
        }

    }

    /**
     * 图片下载器
     */
    private ImageDownloader mImageDownloader = (url, activity) -> {
        File file = null;
        try {
            file = Glide.with(activity)
                    .load(url)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Logger.e("下载图片异常" + e.getMessage());
        }
        return file;
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (butterKnifeBind != null) {
            butterKnifeBind.unbind();
        }
    }
}
