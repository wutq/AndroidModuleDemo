package com.wss.module.main.ui.page.flip.helper;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：图片切割
 * Created by 吴天强 on 2021/2/8.
 */
public class ImageSplitter {

    /**
     * 图片切割
     *
     * @param bitmap 导入图片
     * @param x      x轴切割
     * @param y      y轴切割
     * @return
     */
    public static List<ImagePiece> split(Bitmap bitmap, int x, int y) {
        List<ImagePiece> pieces = new ArrayList<ImagePiece>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / x;
        int pieceHeight = height / y;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                ImagePiece image = new ImagePiece();
                image.setIndex(j + i * x);
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                Bitmap bitmap1 = Bitmap.createBitmap(bitmap, xValue, yValue, pieceWidth, pieceHeight);
                image.setBitmap(bitmap1);
                pieces.add(image);
            }
        }
        return pieces;
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        Bitmap newbm = null;
        if (bm != null) {
            // 获得图片的宽高
            int width = bm.getWidth();
            int height = bm.getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        }
        return newbm;
    }
}
