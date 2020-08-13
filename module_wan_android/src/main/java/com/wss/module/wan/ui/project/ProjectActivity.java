package com.wss.module.wan.ui.project;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseRefreshListActivity;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.project.adapter.ProjectListAdapter;
import com.wss.module.wan.ui.project.mvp.ProjectPresenter;
import com.wss.module.wan.ui.project.mvp.contract.ProjectContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：项目
 * Created by 吴天强 on 2018/11/15.
 */
public class ProjectActivity extends BaseRefreshListActivity<ProjectPresenter> implements ProjectContract.View {

    private List<Classification> types = new ArrayList<>();
    private List<Article> projects = new ArrayList<>();

    /**
     * 当前显示的项目分类
     */
    private Classification currentClassification;
    private int page = 1;

    @Override
    protected void initView() {
        super.initView();
        getTitleBar().setRightText("分类", v -> new TypePopupWindow(context)
                .setData(types)
                .setCurrent(currentClassification)
                .setListener(classification -> {
                    if (classification != null) {
                        currentClassification = classification;
                        setCenterText(currentClassification.getName());
                        resetPage();
                    }
                })
                .show(v));
        getPresenter().start();
    }

    private void resetPage() {
        page = 0;
        getPresenter().getProject();
        this.projects.clear();
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getTypeId() {
        return currentClassification.getId();
    }

    @Override
    public void refreshProjectTypeList(List<Classification> types) {
        this.types.addAll(types);
        if (ValidUtils.isValid(types, 0)) {
            currentClassification = types.get(0);
            if (currentClassification != null) {
                setCenterText(currentClassification.getName());
                resetPage();
            } else {
                showEmptyView("暂无项目数据");
            }
        }
    }

    @Override
    public void refreshProjectList(List<Article> projects) {
        int position = this.projects.size();
        this.projects.addAll(projects);
        getAdapter().notifyDataSetChanged();
        if (page > 0) {
            getRecyclerView().smoothScrollToPosition(position + 3);
        }
        page++;
    }


    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(context);
    }

    @Override
    protected OnRefreshLoadMoreListener createRefreshListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().getProject();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                resetPage();
            }
        };
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        ToastUtils.show(getPage() > 0 ? "暂无更多数据~" : "暂无数据");
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        ToastUtils.show(errorMsg);
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ProjectListAdapter(context, projects,
                (data, position) -> ActivityToActivity.toWebView(context, data.getLink()));
    }
}
