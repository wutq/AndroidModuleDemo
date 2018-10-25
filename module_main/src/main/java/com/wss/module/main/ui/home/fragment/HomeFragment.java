package com.wss.module.main.ui.home.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wss.common.activity.WebViewActivity;
import com.wss.common.base.BaseActivity;
import com.wss.common.base.BaseMvpFragment;
import com.wss.common.base.adapter.BaseRcyAdapter;
import com.wss.common.base.adapter.listener.OnRcyItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.BannerInfo;
import com.wss.module.main.bean.MainBlock;
import com.wss.module.main.ui.home.adapter.HomeRcyAdapter;
import com.wss.module.main.ui.home.mvp.HomePresenter;
import com.wss.module.main.ui.home.mvp.IHomeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：首页
 * Created by 吴天强 on 2018/10/17.
 */

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements IHomeView, OnRcyItemClickListener {

    @BindView(R2.id.banner)
    Banner banner;

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;


    private BaseRcyAdapter adapter;
    private List<MainBlock> data = new ArrayList<>();
    private List<BannerInfo> bannerList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_main;
    }

    @Override
    protected void initView() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //Banner点击
                WebViewActivity.actionStart(mContext, bannerList.get(position).getUrl());
            }
        });

        adapter = new HomeRcyAdapter(mContext, data, this);
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
        ImageUtils.loadBanner(banner, strings);
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
                ToastUtils.showToast(mContext, "跳转wan_android模块");
                //跳转 玩安卓
                ARouter.getInstance()
                        .build(mainBlock.getUrl())
                        .navigation();
                break;
            case 1:
                ToastUtils.showToast(mContext, "跳转market模块");
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
}
