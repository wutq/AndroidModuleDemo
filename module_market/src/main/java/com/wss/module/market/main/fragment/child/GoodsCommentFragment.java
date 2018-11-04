package com.wss.module.market.main.fragment.child;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wss.common.base.BaseMvpFragment;
import com.wss.module.market.R;
import com.wss.module.market.R2;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.main.adapter.GoodsCommentAdapter;
import com.wss.module.market.main.mvp.GoodsDetailPresenter;
import com.wss.module.market.main.mvp.IGoodsDetailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：商品详情 -商品评价
 * Created by 吴天强 on 2018/10/19.
 */
public class GoodsCommentFragment extends BaseMvpFragment<GoodsDetailPresenter> implements IGoodsDetailView {


    @BindView(R2.id.tv_comment_count)
    TextView tvCommentCount;

    @BindView(R2.id.tv_praise_rate)
    TextView tvPraiseRate;

    @BindView(R2.id.tv_empty_comment)
    TextView tvEmptyComment;

    @BindView(R2.id.recycle_view)
    RecyclerView recycleView;

    private List<GoodsComment> commentList = new ArrayList<>();
    private GoodsCommentAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.market_fragment_goods_comment;
    }

    @Override
    protected void initView() {
        tvEmptyComment.setVisibility(View.GONE);
        recycleView.setVisibility(View.VISIBLE);
        tvCommentCount.setText("用户点评(999)");
        tvPraiseRate.setText("好评率97.8%");

        presenter.getCommentList();
    }


    @Override
    protected GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    public String getGoodsId() {
        return null;
    }

    @Override
    public void goodsInfo(GoodsInfo goodsInfo) {
        //TODO 这里耦合了 需要后续优化
    }

    @Override
    public void recommendList(List<List<GoodsInfo>> recommendList) {
        //TODO 这里耦合了 需要后续优化
    }

    @Override
    public void commentList(List<GoodsComment> commentList) {
        this.commentList = commentList;
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new GoodsCommentAdapter(mContext, commentList, R.layout.market_item_of_goods_comment_list);
        recycleView.setAdapter(adapter);
    }
}
