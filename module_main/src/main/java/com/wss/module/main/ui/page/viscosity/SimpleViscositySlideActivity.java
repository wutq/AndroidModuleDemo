package com.wss.module.main.ui.page.viscosity;

import android.os.Handler;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.manager.ScrollSpeedLinearLayoutManger;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.viscosity.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：黏性滑动
 * Created by 吴天强 on 2020/8/13.
 */
public class SimpleViscositySlideActivity extends BaseActionBarActivity {

    @BindView(R2.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @BindView(R2.id.rv_list_view)
    RecyclerView recyclerView;

    private List<String> data = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_action_simple_viscosity_slide;
    }

    @Override
    protected void initView() {
        setCenterText("简单黏性滑动");
        recyclerView.setLayoutManager(new ScrollSpeedLinearLayoutManger(context));
        recyclerView.setAdapter(simpleAdapter = new SimpleAdapter(context, data));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> resetPage(false), 500);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                new Handler().postDelayed(() -> resetPage(true), 500);
            }
        });
        refreshLayout.setEnableAutoLoadMore(true);
        resetPage(false);
    }

    private void resetPage(boolean clear) {
        if (clear) {
            data.clear();
        }
        int start = data.size();
        for (int i = start; i < start + 5; i++) {
            data.add("黏性的Item" + (i + 1));
        }
        simpleAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(start + 3);
        stopRefresh(refreshLayout);
    }
}
