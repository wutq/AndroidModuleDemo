package com.wss.common.widget.scaleImg;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.orhanobut.logger.Logger;
import com.wss.common.base.R;
import com.wss.common.utils.PermissionsUtils;
import com.wss.common.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Describe：图片查看器
 * Created by 吴天强 on 2018年11月16日17:02:05
 */

@SuppressLint("StaticFieldLeak")
public class ImageViewer {

    private static final byte URLS = 0;
    private static final byte FILES = 1;
    private byte mStatus;

    private Activity mActivity;

    //图片下载器
    private ImageDownloader mImageDownloader = new ImageDownloader() {
        @Override
        public File downLoad(String url, Activity activity) {
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
        }
    };

    private List<String> mUrls = new ArrayList<>();
    private List<File> mFiles = new ArrayList<>();
    private List<File> mDownloadFiles = new ArrayList<>();

    private int mSelectedPosition;

    private Dialog mDialog;

    private ImageView imDelete;
    private ImageView imDownload;
    private TextView tvImageCount;
    private ViewPager mViewPager;

    private List<View> mViews;
    private ImageViewerAdapter mAdapter;

    private OnDeleteItemListener mListener;
    private int mStartPosition = 0;//图片起始位置
    private static ImageViewer completeImageView;

    public static ImageViewer create(Activity activity) {
        if (completeImageView == null) {
            completeImageView = new ImageViewer(activity);
        }
        return completeImageView;
    }

    private ImageViewer(Activity activity) {
        mActivity = activity;
    }

    /**
     * 设置图片下载器
     *
     * @param imageDownloader 图片下载器
     * @return ImageViewer
     */
    public ImageViewer setImageDownloader(ImageDownloader imageDownloader) {
        mImageDownloader = imageDownloader;
        return this;
    }

    /**
     * 加载网络图片
     *
     * @param urls 图片
     * @return ImageViewer
     */
    public ImageViewer setUrls(List<String> urls) {
        mUrls.addAll(urls);
        mStatus = URLS;
        return this;
    }

    /**
     * 加载网络图片
     *
     * @param url 图片
     * @return ImageViewer
     */
    public ImageViewer setUrl(String url) {
        List<String> urls = new ArrayList<>();
        urls.add(url);
        return setUrls(urls);
    }

    /**
     * 设置图片起始位置
     *
     * @param position 起始位置
     * @return ImageViewer
     */
    public ImageViewer setPosition(int position) {
        this.mStartPosition = position;
        return this;
    }

    /**
     * 加载本地图片
     *
     * @param files 图片
     * @return ImageViewer
     */
    public ImageViewer setFiles(List<File> files) {
        mFiles.addAll(files);
        mStatus = FILES;
        return this;
    }

    /**
     * 加载本地图片
     *
     * @param file 图片
     * @return ImageViewer
     */
    public ImageViewer setFiles(File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        return setFiles(files);
    }

    /**
     * 删除图片监听
     *
     * @param listener listener
     * @return ImageViewer
     */
    public ImageViewer setOnDeleteItemListener(OnDeleteItemListener listener) {
        mListener = listener;
        return this;
    }

    private void init() {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(mActivity, R.layout.dialog_scale_image, null);
        ImageView close = relativeLayout.findViewById(R.id.scale_image_close);
        imDelete = relativeLayout.findViewById(R.id.scale_image_delete);
        imDownload = relativeLayout.findViewById(R.id.scale_image_save);
        tvImageCount = relativeLayout.findViewById(R.id.scale_image_count);
        mViewPager = relativeLayout.findViewById(R.id.scale_image_view_pager);
        mDialog = new Dialog(mActivity, R.style.DialogFullscreen);
        mDialog.setContentView(relativeLayout);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = mViews.size();
                mFiles.remove(mSelectedPosition);
                if (mListener != null) {
                    mListener.onDelete(mSelectedPosition);
                }
                mViewPager.removeView(mViews.remove(mSelectedPosition));
                if (mSelectedPosition != size) {
                    int position = mSelectedPosition + 1;
                    String text = position + "/" + mViews.size();
                    tvImageCount.setText(text);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        imDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionsUtils.checkPermissions(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    try {
                        MediaStore.Images.Media.insertImage(mActivity.getContentResolver(),
                                mDownloadFiles.get(mSelectedPosition).getAbsolutePath(),
                                mDownloadFiles.get(mSelectedPosition).getName(), null);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    ToastUtils.showToast(mActivity, "图片保存成功");
                }
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mSelectedPosition = position;
                String text = ++position + "/" + mViews.size();
                tvImageCount.setText(text);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        if (mStatus == URLS) {
            imDelete.setVisibility(View.GONE);
        } else {
            imDownload.setVisibility(View.GONE);
        }
    }

    public void build() {
        if (mImageDownloader == null) {
            return;
        }
        init();
        mDialog.show();
        mViews = new ArrayList<>();
        mAdapter = new ImageViewerAdapter(mViews, mDialog);
        if (mStatus == URLS) {
            for (final String url : mUrls) {
                final SubsamplingScaleImageView scaleImageView = new SubsamplingScaleImageView(mActivity);
                scaleImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

                IOThread.getSingleThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        final File downLoadFile;
                        try {
                            downLoadFile = mImageDownloader.downLoad(url, mActivity);
                            mDownloadFiles.add(downLoadFile);
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    scaleImageView.setImage(ImageSource.uri(Uri.fromFile(downLoadFile)));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Logger.e("加载图片异常" + e.getMessage());
                        }
                    }
                });
                mViews.add(scaleImageView);
            }
            mViewPager.setAdapter(mAdapter);
        } else if (mStatus == FILES) {
            for (File file : mFiles) {
                final SubsamplingScaleImageView scaleImageView = new SubsamplingScaleImageView(mActivity);
                scaleImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                scaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)));
                mViews.add(scaleImageView);
            }
            mViewPager.setAdapter(mAdapter);
        }
        mViewPager.setCurrentItem(mStartPosition);
        tvImageCount.setText(String.format("%s/%s", mStartPosition + 1, mUrls.size()));
    }

    public interface OnDeleteItemListener {
        void onDelete(int position);
    }

}