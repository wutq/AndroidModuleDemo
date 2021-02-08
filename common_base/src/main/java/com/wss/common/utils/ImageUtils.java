package com.wss.common.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.wss.common.adapter.BannerImgAdapter;
import com.wss.common.base.R;
import com.wss.common.manage.BlurTransformation;
import com.wss.common.manage.GlideRoundTransform;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * Describe：图片工具类
 * Created by 吴天强 on 2018/10/17.
 */
public class ImageUtils {


    /**
     * 加载手机图片
     * file
     *
     * @param imageView imageView
     * @param file      file
     */
    public static void loadImage(@NotNull ImageView imageView, File file) {
        loadImage(imageView, file, R.color.color_999999);
    }

    /**
     * 加载手机图片
     *
     * @param imageView imageView
     * @param file      file
     * @param error     加载失败占位图
     */
    public static void loadImage(@NotNull ImageView imageView, File file, @DrawableRes int error) {
        Glide.with(imageView.getContext())
                .load(file)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_999999)
                        .error(error)
                        .fallback(error))
                .into(imageView);
    }

    /**
     * 加载手机图片
     *
     * @param imageView imageView
     */
    public static void loadImage(@NotNull ImageView imageView, Bitmap bitmap) {
        Glide.with(imageView.getContext())
                .load(bitmap)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_999999))
                .into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param drawable  drawable
     * @param imageView imageView
     */
    public static void loadImage(@NotNull ImageView imageView, @DrawableRes int drawable) {
        loadImage(imageView, drawable, R.color.color_999999);
    }

    /**
     * 加载本地图片
     *
     * @param drawable  drawable
     * @param imageView imageView
     * @param error     加载失败占位图
     */
    public static void loadImage(@NotNull ImageView imageView, int drawable, @DrawableRes int error) {
        Glide.with(imageView.getContext())
                .load(drawable)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_999999)
                        .error(error)
                        .fallback(error))
                .into(imageView);
    }

    /**
     * 加载网络图片
     *
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImage(ImageView imageView, String url) {
        loadImage(imageView, url, R.color.color_999999);
    }

    /**
     * 加载网络图片
     *
     * @param url       url
     * @param imageView imageView
     * @param error     加载失败占位图
     */
    public static void loadImage(@NotNull ImageView imageView, String url, @DrawableRes int error) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_999999)
                        .error(error)
                        .fallback(error))
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param url       url
     * @param imageView imageView
     */
    public static void loadImageCircle(ImageView imageView, String url) {
        loadImageCircle(imageView, url, R.color.color_999999, R.color.color_999999);
    }

    /**
     * 加载圆形图片
     *
     * @param url       url
     * @param imageView imageView
     * @param error     加载失败占位图
     */
    public static void loadImageCircle(@NotNull ImageView imageView, String url, @DrawableRes int placeholder, @DrawableRes int error) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(placeholder)
                        .error(error)
                        .fallback(error))
                .into(imageView);
    }


    /**
     * 加载圆角图片
     *
     * @param imageView ImageView
     * @param url       URL
     * @param dp        圆角角度
     */
    public static void loadImageCircleBead(@NotNull ImageView imageView, String url, int dp) {
        loadImageCircleBead(imageView, url, dp, R.color.color_999999);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView ImageView
     * @param url       URL
     * @param dp        圆角角度
     * @param error     加载失败占位图
     */
    public static void loadImageCircleBead(@NotNull ImageView imageView, String url, int dp, @DrawableRes int error) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_999999)
                        .error(error)
                        .transform(new GlideRoundTransform(imageView.getContext(), dp)))
                .into(imageView);
    }

    public static void loadImageTest(@NotNull ImageView imageView, String url, int dp) {

        //设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(dp);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        options.placeholder(R.color.color_999999);
        Glide.with(imageView.getContext()).load(url).apply(options).into(imageView);

    }

    /**
     * 加载圆角图片
     *
     * @param imageView ImageView
     * @param file      file
     * @param dp        圆角角度
     */
    public static void loadImageCircleBead(@NotNull ImageView imageView, File file, int dp) {
        loadImageCircleBead(imageView, file, dp, R.color.color_999999);
    }

    /**
     * 加载圆角图片
     *
     * @param imageView ImageView
     * @param file      file
     * @param dp        圆角角度
     * @param error     加载失败占位图
     */
    public static void loadImageCircleBead(@NotNull ImageView imageView, File file, int dp, @DrawableRes int error) {
        Glide.with(imageView.getContext())
                .load(file)
                .apply(new RequestOptions()
                        .placeholder(R.color.color_999999)
                        .error(error)
                        .transform(new GlideRoundTransform(imageView.getContext(), dp)))
                .into(imageView);
    }


    /**
     * 加载高斯模糊图
     *
     * @param imageView imageView
     * @param drawable  drawable
     */
    public static void loadImageBlur(ImageView imageView, int drawable) {
        Glide.with(imageView)
                .load(drawable)
                .apply(RequestOptions.bitmapTransform(
                        new BlurTransformation(imageView.getContext(), 5f))
                        .error(R.color.theme))
                .into(imageView);
    }

    /**
     * 加载高斯模糊图
     *
     * @param imageView imageView
     * @param drawable  drawable
     * @param error     加载失败占位图
     */
    public static void loadImageBlur(ImageView imageView, @DrawableRes int drawable, @DrawableRes int error) {
        Glide.with(imageView)
                .load(drawable)
                .apply(RequestOptions.bitmapTransform(
                        new BlurTransformation(imageView.getContext(), 5f))
                        .placeholder(R.color.color_999999)
                        .error(error))
                .into(imageView);
    }

    /**
     * 加载高斯模糊图
     *
     * @param imageView imageView
     * @param url       url
     */
    public static void loadImageBlur(ImageView imageView, String url) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.bitmapTransform(
                        new BlurTransformation(imageView.getContext()))
                        .error(R.color.theme))
                .into(imageView);
    }

    /**
     * 加载高斯模糊图
     *
     * @param imageView imageView
     * @param url       url
     * @param error     加载失败占位图
     */
    public static void loadImageBlur(ImageView imageView, String url, @DrawableRes int error) {
        Glide.with(imageView)
                .load(url)
                .apply(RequestOptions.bitmapTransform(
                        new BlurTransformation(imageView.getContext()))
                        .placeholder(R.color.color_999999)
                        .error(error))
                .into(imageView);
    }


    /**
     * 加载圆形头像
     *
     * @param imageView imageView
     * @param url       图片链接
     */
    public static void loadCircleHeader(ImageView imageView, String url) {
        loadImageCircle(imageView, url, R.drawable.header_load_default, R.drawable.header_load_default);
    }

    /**
     * 加载只有一张图的Banner
     *
     * @param banner   banner控件
     * @param imgUrl   banner图片集合
     * @param listener listener
     */
    public static <T> void loadBanner(ConvenientBanner<T> banner, List<T> imgUrl, OnItemClickListener listener) {
        banner.setPages(new BannerImgAdapter(), imgUrl)
                .setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(listener)
                .startTurning();
    }

    /**
     * 加载只有一张图的Banner
     *
     * @param banner   banner控件
     * @param imgUrl   banner图片集合
     * @param listener listener
     */
    public static <T> void loadBanner(ConvenientBanner<T> banner, List<T> imgUrl, boolean circle, OnItemClickListener listener) {
        banner.setPages(new BannerImgAdapter(circle), imgUrl)
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
        int quality = 100;//获取一定尺寸的图片
        Bitmap bm = getSmallBitmap(filePath);
        //获取相片拍摄角度
        int degree = readPictureDegree(filePath);
        if (degree != 0) {
            //旋转照片角度，防止头像横着显示
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
     *
     * @param filePath 图片地址
     * @return Bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //只解析图片边沿，获取宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算缩放比
     *
     * @param options   options
     * @param reqWidth  宽
     * @param reqHeight 高
     * @return 缩放比
     */
    public static int calculateInSampleSize(@NotNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

    /**
     * 获取照片角度
     *
     * @param path 图片地址
     * @return int
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
     * @param bitmap  bitmap
     * @param degress 旋转角度
     * @return Bitmap
     */
    @Contract("null, _ -> null")
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return null;
    }

    /**
     * 把View转成Bitmap 注意：改view必须是已经显示到页面上的
     *
     * @return Bitmap
     */
    public static Bitmap viewConversionBitmap(@NotNull View view, String color) {

        int w = view.getWidth();
        int h = view.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, w, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.parseColor(color));
        view.layout(0, 0, w, h);
        view.draw(c);
        return bmp;

    }

    /**
     * 把View转成Bitmap 注意：改view必须是已经显示到页面上的
     *
     * @return Bitmap
     */
    public static Bitmap viewConversionBitmap(@NotNull ScrollView scrollView, String color) {

        int height = 20;
        //正确获取ScrollView
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            height += scrollView.getChildAt(i).getHeight();
        }

        int w = scrollView.getWidth();

        Bitmap bmp = Bitmap.createBitmap(w, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.parseColor(color));
        scrollView.layout(0, 0, w, height);
        scrollView.draw(c);
        return bmp;

    }

    /**
     * 把Bitmap转成图片
     *
     * @param bitmap    bitmap
     * @param imageName 文件名称
     * @return 图片的本地地址
     */
    @org.jetbrains.annotations.Nullable
    public static String bitmapSaveToImage(Bitmap bitmap, String imageName) {
        FileOutputStream b = null;
        String filePath = FileUtils.getImagePath() + File.separator + imageName + ".jpg";
        try {
            b = new FileOutputStream(filePath);
            // 把数据写入文件
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 添加图片到系统相册
     *
     * @param activity activity
     * @param filePath 图片路径
     * @param fileName 图片名称
     */
    public static boolean addImageToAlbum(@NotNull Activity activity, String filePath, String fileName) {
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(), filePath, fileName, null);
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory().getPath())));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 生成个人名片海报图片
     *
     * @param activity   activity
     * @param scrollView ScrollView
     */
    public static boolean generatePoster(Activity activity, ScrollView scrollView) {
        String fileName = String.valueOf(DateUtils.getCurrentTimeStamp());
        String filePath = ImageUtils.bitmapSaveToImage(ImageUtils.viewConversionBitmap(scrollView, "#F5F5F5"), fileName);
        if (ValidUtils.isValid(filePath)) {
            return addImageToAlbum(activity, filePath, fileName);
        }
        return false;
    }

    /**
     * 生成个人名片海报图片
     *
     * @param activity   activity
     * @param scrollView ScrollView
     */
    public static String generatePosterPath(Activity activity, ScrollView scrollView) {
        return ImageUtils.bitmapSaveToImage(ImageUtils.viewConversionBitmap(scrollView, "#F5F5F5"), String.valueOf(DateUtils.getCurrentTimeStamp()));
    }

    /**
     * Bitmap转二进制流
     *
     * @param bitmap bitmap
     * @return byte[]
     */
    @NotNull
    public static byte[] getBitmapByte(@NotNull Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     *
     * @param bitmap bitmap
     * @param maxKb  最大大小
     * @return 字节数组
     */
    @NotNull
    public static byte[] bitmapBytes(@NotNull Bitmap bitmap, int maxKb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxKb && options != 10) {
            output.reset(); //清空output
            //这里压缩options%，把压缩后的数据存放到output中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);
            options -= 10;
        }
        return output.toByteArray();
    }

    /**
     * 获取海报的图片二进制流
     *
     * @param layoutView 海报view
     * @return byte[]
     */
    @NotNull
    public static byte[] getPosterByte(View layoutView) {
        return getBitmapByte(ImageUtils.viewConversionBitmap(layoutView, "#F5F5F5"));
    }

    /**
     * 获取微信分享图片的缩略图
     *
     * @param image 图片流
     * @return 图片流
     */
    @NotNull
    public static byte[] getWXShareThumbImage(byte[] image) {
        return bitmapBytes(getBitmapByBytes(image, 200, 300), 32);
    }

    /**
     * 根据图片生成缩略图
     *
     * @param bytes     图片流
     * @param maxHeight 最大高度
     * @param maxWidth  最大宽度
     * @return Bitmap
     */
    @NotNull
    public static Bitmap getBitmapByBytes(byte[] bytes, int maxHeight, int maxWidth) {
        //对于图片的二次采样,主要得到图片的宽与高
        int width = 0;
        int height = 0;
        //默认缩放为1
        int sampleSize = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //仅仅解码边缘区域
        options.inJustDecodeBounds = true;
        //如果指定了inJustDecodeBounds，decodeByteArray将返回为空
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        //得到宽与高
        height = options.outHeight;
        width = options.outWidth;

        //图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
        while ((height / sampleSize > maxHeight) || (width / sampleSize > maxWidth)) {
            sampleSize *= 2;
        }
        //不再只加载图片实际边缘
        options.inJustDecodeBounds = false;
        //并且制定缩放比例
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }

}
