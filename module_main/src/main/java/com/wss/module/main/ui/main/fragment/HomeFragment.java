package com.wss.module.main.ui.main.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.wss.common.activity.WebViewActivity;
import com.wss.common.base.BaseActivity;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.BannerInfo;
import com.wss.module.main.bean.MainBlock;
import com.wss.module.main.ui.main.adapter.HomeRcyAdapter;
import com.wss.module.main.ui.main.mvp.HomePresenter;
import com.wss.module.main.ui.main.mvp.IHomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：首页
 * Created by 吴天强 on 2018/10/17.
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements IHomeView, OnListItemClickListener {

    @BindView(R2.id.cb_banner)
    ConvenientBanner<String> banner;

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;


    private HomeRcyAdapter adapter;
    private List<MainBlock> data = new ArrayList<>();
    private List<BannerInfo> bannerList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_main;
    }

    @Override
    protected void initView() {

        adapter = new HomeRcyAdapter(mContext, data, R.layout.main_item_of_block_list, this);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(adapter);

        presenter.getBanner();
        presenter.getBlock();
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void bannerList(List<BannerInfo> banners) {
        this.bannerList.addAll(banners);
        List<String> strings = new ArrayList<>();
        for (BannerInfo bannerInfo : banners) {
            strings.add(bannerInfo.getImagePath());
        }
        ImageUtils.loadBanner(banner, strings, new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                WebViewActivity.actionStart(mContext, bannerList.get(position).getUrl());
            }
        });
    }

    @Override
    public void blockList(List<MainBlock> blockList) {
        this.data.addAll(blockList);
        //presenter中子线程模拟获取数据 这里更新需要切换到UI线程
        ((BaseActivity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        MainBlock mainBlock = data.get(position);
        switch (position) {
            case 0:
                //跳转 玩安卓
                ARouter.getInstance()
                        .build(mainBlock.getUrl())
                        .navigation();
                break;
            case 1:
                //跳转 商城
                ARouter.getInstance()
                        .build(mainBlock.getUrl())
                        .navigation();
                break;
            default:
                WebViewActivity.actionStart(mContext, mainBlock.getUrl());
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        banner.startTurning();
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止自动翻页
        banner.stopTurning();
    }
}
