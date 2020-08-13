package com.wss.module.wan.ui.search;

import android.view.View;
import android.widget.EditText;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wss.common.base.BaseRefreshListActivity;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.utils.KeyboardUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.ObserverButton;
import com.wss.common.widget.manager.ScrollSpeedLinearLayoutManger;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.ui.main.adapter.ArticleAdapter;
import com.wss.module.wan.ui.search.mvp.SearchPresenter;
import com.wss.module.wan.ui.search.mvp.contract.SearchContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：搜索页面
 * Created by 吴天强 on 2018/11/15.
 */
public class SearchActivity extends BaseRefreshListActivity<SearchPresenter> implements SearchContract.View {


    private EditText editText;
    private List<Article> data = new ArrayList<>();
    private int page = 0;

    @Override
    protected void initView() {
        super.initView();
        View actionBarView = View.inflate(context, R.layout.wan_action_bar_of_search, null);
        editText = actionBarView.findViewById(R.id.edt_search);
        ObserverButton oButton = actionBarView.findViewById(R.id.obt_search);
        oButton.observer(editText);
        getTitleBar().setActionBarView(actionBarView);
        KeyboardUtils.showKeyboard(editText);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            KeyboardUtils.hideKeyboard(v);
            resetPage();
            return true;
        });
    }

    private void resetPage() {
        page = 0;
        data.clear();
        getPresenter().search();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new ScrollSpeedLinearLayoutManger(context);
    }


    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter();
    }


    @Override
    protected OnRefreshLoadMoreListener createRefreshListener() {
        return new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getPresenter().search();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                resetPage();
            }
        };
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return new ArticleAdapter(context, data,
                ((data1, position) -> ActivityToActivity.toWebView(context, data1.getLink())));
    }


    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ll_back) {
            finish();
        } else if (i == R.id.obt_search) {
            resetPage();
        }
    }

    @Override
    public String getWord() {
        return editText.getText().toString().trim();
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void refreshSearchData(List<Article> searchs) {
        int position = this.data.size();
        this.data.addAll(searchs);
        getAdapter().notifyDataSetChanged();
        if (page > 0) {
            getRecyclerView().smoothScrollToPosition(position + 3);
        }
        page++;
        stopRefresh();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        if (page == 0) {
            showErrorView(errorMsg);
        } else {
            ToastUtils.show(errorMsg);
        }
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        if (page == 0) {
            showEmptyView("木有搜到你想要的");
        } else {
            ToastUtils.show("没有更多啦~");
        }
    }
}
