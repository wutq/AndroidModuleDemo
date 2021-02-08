package com.wss.module.main.ui.page.flip.helper;

import android.graphics.Bitmap;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：图片切割实体类
 * Created by 吴天强 on 2021/2/8.
 */
@Getter
@Setter
public class ImagePiece {
    private int index;
    private Bitmap bitmap;
    private boolean isAnim;
}
