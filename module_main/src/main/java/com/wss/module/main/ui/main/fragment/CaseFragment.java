package com.wss.module.main.ui.main.fragment;

import com.wss.common.base.BaseMvpFragment;
import com.wss.common.bean.Template;
import com.wss.common.manage.ActivityToActivity;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.main.adapter.CaseAdapter;
import com.wss.module.main.ui.main.mvp.CasePresenter;
import com.wss.module.main.ui.main.mvp.contract.CaseContract;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：一些案例
 * Created by 吴天强 on 2018/10/17.
 */
public class CaseFragment extends BaseMvpFragment<CasePresenter> implements CaseContract.View {

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment_case;
    }

    @Override
    protected void initView() {
        getPresenter().start();
    }

    @Override
    protected CasePresenter createPresenter() {
        return new CasePresenter();
    }

    @Override
    public void refreshCaseList(List<Template> caseList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new CaseAdapter(context, caseList,
                (data, position) -> ActivityToActivity.toActivity(getActivity(), data)));

    }
}
