package com.wss.module.wan.ui.project;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.wss.common.base.RefreshListActivity;
import com.wss.common.utils.ActivityToActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.ui.project.adapter.ProjectListAdapter;
import com.wss.module.wan.ui.project.mvp.IProjectView;
import com.wss.module.wan.ui.project.mvp.ProjectPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：项目
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectActivity extends RefreshListActivity<ProjectPresenter> implements IProjectView,
        TypePopupWindow.OnProjectTypeClickListener {
    private static final String TAG_TYPE = "TAG_TYPE";
    private static final String TAG_PROJECT = "TAG_PROJECT";

    private List<Classification> types = new ArrayList<>();
    private List<Article> projects = new ArrayList<>();

    private Classification currentClassification;//当前显示的项目分类
    private int page = 1;

    @Override
    protected void initView() {
        super.initView();
        actionBar.setRightText("项目", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TypePopupWindow(mContext)
                        .setData(types)
                        .setCurrent(currentClassification)
                        .setListener(ProjectActivity.this)
                        .show(v);
            }
        });
        presenter.getType(TAG_TYPE);
    }

    @Override
    public void onRefresh() {
        page = 1;
        projects.clear();
        presenter.getProject(TAG_PROJECT);
    }

    @Override
    public void onLoadMore() {
        page++;
        presenter.getProject(TAG_PROJECT);
    }

    @Override
    public void onItemClick(View view, int position) {
        ActivityToActivity.toWebView(mContext, projects.get(position).getLink());
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
    public void projectType(List<Classification> types) {
        this.types.addAll(types);
        currentClassification = types.get(0);
        setTitleText(currentClassification.getName());
        onRefresh();
    }

    @Override
    public void projectList(List<Article> projects) {
        this.projects.addAll(projects);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        if (TextUtils.equals(tag.toString(), TAG_TYPE)) {
            showEmptyView("暂无项目");
        } else {
            if (page == 1) {
                showEmptyView("暂无项目数据");
            } else {
                ToastUtils.showToast(mContext, "暂无更多项目数据");
            }
        }
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        if (TextUtils.equals(tag.toString(), TAG_TYPE)) {
            showEmptyView(errorMsg);
        } else {
            if (page == 1) {
                showEmptyView(errorMsg);
            } else {
                ToastUtils.showToast(mContext, errorMsg);
            }
        }
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ProjectListAdapter(mContext, projects, R.layout.wan_item_of_project_list, this);
    }

    @Override
    public void onProjectTypeClick(Classification classification) {
        //选择了一个分类
        if (classification != null) {
            this.currentClassification = classification;
            setTitleText(currentClassification.getName());
            onRefresh();
        }
    }
}
