package com.wss.module.main.ui.page;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.SwipeItemLayout;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.adapter.SlewedAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：RecyclerView侧滑
 * Created by 吴天强 on 2020/11/4.
 */
public class SlewedActivity extends BaseActionBarActivity {

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_slewed;
    }

    @Override
    protected void initView() {
        setCenterText("侧滑菜单");
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            dataList.add("这是Item" + i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        recyclerView.setAdapter(new SlewedAdapter(context, dataList));

    }
}
