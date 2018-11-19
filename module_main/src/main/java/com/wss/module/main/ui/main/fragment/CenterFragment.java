package com.wss.module.main.ui.main.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wss.common.base.BaseMvpFragment;
import com.wss.common.bean.Template;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ActivityToActivity;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.main.adapter.CenterRcyAdapter;
import com.wss.module.main.ui.main.mvp.CenterPresenter;
import com.wss.module.main.ui.main.mvp.ICenterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：中间
 * Created by 吴天强 on 2018/10/17.
 */
public class CenterFragment extends BaseMvpFragment<CenterPresenter> implements ICenterView, OnListItemClickListener {

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;


    private List<Template> data = new ArrayList<>();

    private CenterRcyAdapter adapter;

    @Override
    protected void initView() {

        adapter = new CenterRcyAdapter(mContext, data, R.layout.main_item_of_center_list, this);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(adapter);
        presenter.getTabList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_center;
    }

    @Override
    protected CenterPresenter createPresenter() {
        return new CenterPresenter();
    }

    @Override
    public void tabList(List<Template> blockList) {
        this.data.addAll(blockList);
        adapter.notifyDataSetChanged();
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(View view, int position) {
        ActivityToActivity.toActivity(mContext, data.get(position));
    }


}
