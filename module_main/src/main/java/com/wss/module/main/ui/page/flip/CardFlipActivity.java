package com.wss.module.main.ui.page.flip;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.flip.adapter.ImageAdapter;
import com.wss.module.main.ui.page.flip.helper.ImagePiece;
import com.wss.module.main.ui.page.flip.helper.ImageSplitter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：卡片翻转
 * Created by 吴天强 on 2021/2/8.
 */
public class CardFlipActivity extends BaseActionBarActivity {

    public static final int DISTANCE = 16000;

    @BindView(R2.id.recycle_view_image)
    RecyclerView recyclerViewImage;

    @BindView(R2.id.recycle_view_mantle)
    RecyclerView recyclerViewMantle;

    private List<ImagePiece> imageList = new ArrayList<>();
    private ImageAdapter imageAdapter;
    /**
     * 是否正在执行动画
     */
    private boolean isAnimator = false;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_card_flip;
    }

    @Override
    protected void initView() {
        setCenterText("卡片翻转");

        Bitmap bitmap = ((BitmapDrawable) getContext().getDrawable(R.drawable.bb)).getBitmap();
        imageList.clear();
        imageList.addAll(ImageSplitter.split(bitmap, 5, 6));

        recyclerViewImage.setLayoutManager(new GridLayoutManager(context, 5));
        imageAdapter = new ImageAdapter(context, imageList, null);
        recyclerViewImage.setAdapter(imageAdapter);

        recyclerViewMantle.setLayoutManager(new GridLayoutManager(context, 5));
        //蒙层的适配器啥也没做只是为了适配底层图片格子
        ImageAdapter mantleAdapter = new ImageAdapter(context, imageList, (position, imageView) -> {
            if (isAnimator) {
                return;
            }
            AnimatorSet outAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.translat_anim_out);
            float scale = getResources().getDisplayMetrics().density * DISTANCE;
            imageView.setCameraDistance(scale);
            outAnimator.setTarget(imageView);
            outAnimator.start();
            outAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    //结束动画
                    imageView.setAlpha(0.0f);
                    isAnimator = false;
                }
            });
            isAnimator = true;
            imageList.get(position).setAnim(true);
            imageAdapter.notifyDataSetChanged();
        });
        mantleAdapter.setMantle(true);
        recyclerViewMantle.setAdapter(mantleAdapter);
    }
}
