package com.wss.module.wan.ui.setup;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ActivityToActivity;
import com.wss.module.wan.R;
import com.wss.module.wan.R2;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.bean.ClassificationChild;
import com.wss.module.wan.ui.setup.adapter.SetupLeftAdapter;
import com.wss.module.wan.ui.setup.adapter.SetupRightAdapter;
import com.wss.module.wan.ui.setup.mvp.SystemPresenter;
import com.wss.module.wan.ui.setup.mvp.contract.SystemContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Describe：体系
 * Created by 吴天强 on 2018/11/15.
 */

public class SystemActivity extends ActionBarActivity<SystemPresenter> implements SystemContract.View,
        OnListItemClickListener {

    @BindView(R2.id.rv_left)
    RecyclerView rvLeft;
    @BindView(R2.id.rv_right)
    RecyclerView rvRight;

    private List<Classification> leftData = new ArrayList<>();
    private List<ClassificationChild> rightData = new ArrayList<>();
    private SetupLeftAdapter leftAdapter;
    private SetupRightAdapter rightAdapter;

    @Override
    protected SystemPresenter createPresenter() {
        return new SystemPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wan_activity_setup;
    }

    @Override
    protected void initView() {
        setTitleText("体系");
        leftAdapter = new SetupLeftAdapter(mContext, leftData, R.layout.wan_item_of_setup_list, this);
        rightAdapter = new SetupRightAdapter(mContext, rightData, R.layout.wan_item_of_setup_list, this);
        rvLeft.setLayoutManager(new LinearLayoutManager(mContext));
        rvRight.setLayoutManager(new LinearLayoutManager(mContext));
        rvLeft.setAdapter(leftAdapter);
        rvRight.setAdapter(rightAdapter);
        presenter.start();

    }

    @Override
    public void onItemClick(View view, int position) {
        if (view.getId() == R.id.rv_left) {
            rightData.clear();
            rightData.addAll(leftData.get(position).getChildren());
            leftAdapter.notifyData(position);
            //左边切换之后右边默认显示第一个
            rightAdapter.notifyData(0);
        } else {
            //right
            rightAdapter.notifyData(position);
            Map<String, ClassificationChild> param = new HashMap<>();
            param.put("ClassificationChild", rightData.get(position));
            ActivityToActivity.toActivity(mContext, SystemArticleActivity.class, param);
        }
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        showErrorView();
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        showErrorView();
    }

    @Override
    public void systemList(List<Classification> systems) {
        this.leftData.addAll(systems);
        leftAdapter.notifyData(0);
        rightData.clear();
        rightData.addAll(leftData.get(0).getChildren());
        rightAdapter.notifyData(-1);
    }
}
