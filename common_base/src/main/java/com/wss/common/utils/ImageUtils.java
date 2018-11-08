package com.wss.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wss.common.adapter.BannerImgAdapter;
import com.wss.common.base.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Describe：图片工具类
 * Created by 吴天强 on 2018/10/17.
 */

public class ImageUtils {


    /**
     * 加载网络图片
     *
     * @param url       url
     * @param imageView imageView
     * @param imageView transformation 转换器
     */
    public static void loadImage(ImageView imageView, String url, Transformation transformation) {
        Picasso.get()
                .load(url)
//                .placeholder(R.drawable.bg_load_failed)
                .error(R.drawable.bg_load_failed)
                .transform(transformation)
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImage(ImageView imageView, String url) {
        Picasso.get()
                .load(url)
//                .placeholder(R.drawable.bg_load_failed)
                .error(R.drawable.bg_load_failed)
                .into(imageView);
    }

    /**
     * 加载只有一张图的Banner
     *
     * @param banner   banner
     * @param imgUrl   imgUrl
     * @param listener listener
     */
    public static void loadBanner(ConvenientBanner banner, List<String> imgUrl, OnItemClickListener listener) {
        banner.setPages(new BannerImgAdapter(), imgUrl)
                .setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(listener)
                .startTurning();
    }

    /**
     * 压缩图片
     *
     * @param filePath 图片地址
     * @return File
     */
    @Nullable
    public static File compressImage(String filePath) {
        if (!FileUtils.isExistExternalStore()) {
            return null;
        }
        int quality = 100;
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = readPictureDegree(filePath);//获取相片拍摄角度
        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = rotateBitmap(bm, degree);
        }
        File outputFile = null;
        try {
            outputFile = new File(FileUtils.getTempPath(), "temp_" + DateUtils.getCurrentTimeStamp() + ".jpg");
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bm.recycle();
        }
        return outputFile;
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 获取照片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

}
