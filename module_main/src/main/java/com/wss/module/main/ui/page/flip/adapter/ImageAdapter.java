package com.wss.module.main.ui.page.flip.adapter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.widget.ImageView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.utils.ImageUtils;
import com.wss.module.main.R;
import com.wss.module.main.ui.page.flip.CardFlipActivity;
import com.wss.module.main.ui.page.flip.helper.ImagePiece;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * Describe：适配器
 * Created by 吴天强 on 2021/2/8.
 */
public class ImageAdapter extends BaseListAdapter<ImagePiece> {
    /**
     * 点击事件
     */
    private OnClickListener clickListener;
    /**
     * 是否是某曾
     */
    private boolean isMantle;


    public ImageAdapter(Context context, List<ImagePiece> mData, OnClickListener clickListener) {
        super(context, mData, R.layout.main_item_of_card_flip, null);
        this.clickListener = clickListener;
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull ImagePiece data) {
        ImageView imageView = holder.findViewById(R.id.image);
        if (isMantle) {
            imageView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.theme));
            imageView.setOnClickListener(v -> clickListener.onClick(layoutPosition, imageView));
        } else {
            ImageUtils.loadImage(imageView, data.getBitmap());
            if (data.isAnim()) {
                AnimatorSet inAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.translat_anim_in);
                float scale = getContext().getResources().getDisplayMetrics().density * CardFlipActivity.DISTANCE;
                imageView.setCameraDistance(scale);
                inAnimator.setTarget(imageView);
                inAnimator.start();
                //开始动画了就把属性设置为false 防止下次翻转动画的时候又执行一遍
                data.setAnim(false);
            }
        }
    }

    public void setMantle(boolean mantle) {
        isMantle = mantle;
    }

    public interface OnClickListener {
        void onClick(int position, ImageView imageView);
    }
}