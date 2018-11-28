package com.wss.module.market.ui.goods.detail.fragment;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.wss.common.adapter.BannerImgAdapter;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.widget.CountClickView;
import com.wss.common.widget.SlideLayout;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.ui.goods.detail.GoodsDetailActivity;
import com.wss.module.market.ui.goods.detail.adapter.GoodsCommentAdapter;
import com.wss.module.market.ui.goods.detail.adapter.RecommendGoodsAdapter;
import com.wss.module.market.ui.goods.detail.mvp.GoodsDetailPresenter;
import com.wss.module.market.ui.goods.detail.mvp.contract.GoodsDetailContract;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：商品详情 主Fragment 滑动可查看图片详情 规格等
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsInfoMainFragment extends BaseMvpFragment<GoodsDetailPresenter> implements
        SlideLayout.OnSlideDetailsListener, GoodsDetailContract.View {

    @BindView(R2.id.vp_item_goods_img)
    ConvenientBanner<String> vpItemGoodsImg;

    @BindView(R2.id.tv_goods_name)
    TextView tvGoodsName;

    @BindView(R2.id.tv_goods_price)
    TextView tvGoodsPrice;

    @BindView(R2.id.tv_old_price)
    TextView tvOldPrice;

    @BindView(R2.id.tv_comment_count)
    TextView tvCommentCount;

    @BindView(R2.id.tv_praise_rate)
    TextView tvPraiseRate;

    @BindView(R2.id.tv_empty_comment)
    TextView tvEmptyComment;

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;//评论

    @BindView(R2.id.vp_recommend)
    ConvenientBanner<List<GoodsInfo>> vpRecommend; //推荐位置

    @BindView(R2.id.sv_switch)
    SlideLayout svSwitch;

    @BindView(R2.id.ccv_click)
    CountClickView ccvClick;//商品数量加减控件


    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    private GoodsInfo goodsInfo;
    private GoodsDetailActivity goodsDetailActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        goodsDetailActivity = (GoodsDetailActivity) context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.market_fragment_goods_info_main;
    }

    @Override
    protected void initView() {

        ccvClick.setMinCount(1);
        ccvClick.setCurrCount(1);
        svSwitch.setOnSlideDetailsListener(this);
        //设置文字中间一条横线
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        presenter.start();
    }


    @OnClick({R2.id.ll_pull_up, R2.id.ll_comment})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_pull_up) {//上拉查看图文详情
            svSwitch.smoothOpen(true);
        } else if (v.getId() == R.id.ll_comment) {
            //查看评论
            goodsDetailActivity.setCurrentFragment(2);
        }
    }


    @Override
    public void onStateChanged(SlideLayout.Status status) {
        if (goodsDetailActivity != null) {
            goodsDetailActivity.setViewContent(status == SlideLayout.Status.OPEN);
        }
    }

    /**
     * 设置商品头图 轮播
     */
    private void setGoodsHeadImg() {
        if (goodsInfo != null) {
            vpItemGoodsImg.setPages(new BannerImgAdapter() {
                @Override
                public ImageView getImageView() {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    return imageView;
                }
            }, goodsInfo.getGoodsHeadImg())
                    .setPageIndicator(new int[]{R.drawable.market_banner_index_white, R.drawable.market_banner_index_red})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        }
    }

    /**
     * 设置商品信息
     */
    private void setGoodsInfo() {
        if (goodsInfo != null) {
            tvGoodsName.setText(goodsInfo.getGoodsName());
            tvGoodsPrice.setText(String.format("¥%s", goodsInfo.getGoodsPrice()));
            tvOldPrice.setText(String.format("¥%s", goodsInfo.getGoodsOldPrice()));
            tvCommentCount.setText(String.format("用户点评(%S)", goodsInfo.getCommentCount()));
            tvPraiseRate.setText(String.format("好评率%s", goodsInfo.getPraiseRate()));
        }
    }


    @Override
    public String getGoodsId() {
        return null;
    }

    @Override
    public void goodsInfo(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
        setGoodsHeadImg();
        setGoodsInfo();
    }

    @Override
    public void recommendList(List<List<GoodsInfo>> recommendList) {
        //加载推荐位商品
        vpRecommend.setPages(new RecommendGoodsAdapter(mContext), recommendList)
                .setCanLoop(recommendList.size() != 1)
                .setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void commentList(List<GoodsComment> commentList) {
        if (commentList.size() > 0) {
            tvEmptyComment.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(new GoodsCommentAdapter(mContext, commentList, R.layout.market_item_of_goods_comment_list));
        } else {
            tvEmptyComment.setVisibility(View.VISIBLE);
            tvEmptyComment.setText("暂无精彩评论");
        }
    }


    @Override
    protected GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    /**
     * 得到当前商品增减数量
     *
     * @return int
     */
    public int getGoodsCount() {
        return ccvClick.getCount();
    }
}
