package com.wss.module.wan.ui.series;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.constants.Dic;
import com.wss.common.manage.ActivityToActivity;
import com.wss.module.wan.R;
import com.wss.module.wan.R2;
import com.wss.module.wan.bean.Classification;
import com.wss.module.wan.bean.ClassificationChild;
import com.wss.module.wan.ui.series.adapter.SeriesAdapter;
import com.wss.module.wan.ui.series.mvp.SeriesPresenter;
import com.wss.module.wan.ui.series.mvp.contract.SeriesContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：体系 系列
 * Created by 吴天强 on 2018/11/15.
 */
public class SeriesActivity extends BaseActionBarActivity<SeriesPresenter> implements SeriesContract.View {

    @BindView(R2.id.rv_left)
    RecyclerView rvLeft;
    @BindView(R2.id.rv_right)
    RecyclerView rvRight;

    private List<Classification> leftData = new ArrayList<>();
    private List<Classification> rightData = new ArrayList<>();
    private SeriesAdapter leftAdapter, rightAdapter;

    @Override
    protected SeriesPresenter createPresenter() {
        return new SeriesPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wan_activity_series;
    }

    @Override
    protected void initView() {
        setCenterText("体系");
        leftAdapter = new SeriesAdapter(context, leftData, (data, position) -> {
            rightData.clear();
            rightData.addAll(copyData(leftData.get(position).getChildren()));
            leftAdapter.notifyData(position);
            //左边切换之后右边默认显示第一个
            rightAdapter.notifyData(0);
        });
        rightAdapter = new SeriesAdapter(context, rightData,
                (data, position) -> {
                    rightAdapter.notifyData(position);
                    Map<String, Object> param = new HashMap<>(1);
                    param.put(Dic.CLASSIFICATION_CHILD, data);
                    ActivityToActivity.toActivity(context, SeriesArticleActivity.class, param);
                });
        rvLeft.setLayoutManager(new LinearLayoutManager(context));
        rvRight.setLayoutManager(new LinearLayoutManager(context));
        rvLeft.setAdapter(leftAdapter);
        rvRight.setAdapter(rightAdapter);
        getPresenter().start();

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
    protected void onRefreshRetry() {
        super.onRefreshRetry();
        getPresenter().start();
    }

    @Override
    public void refreshSystemList(List<Classification> systems) {
        leftData.clear();
        leftData.addAll(systems);
        leftAdapter.notifyData(0);

        rightData.clear();
        rightData.addAll(copyData(leftData.get(0).getChildren()));
        rightAdapter.notifyData(-1);
    }

    /**
     * 把数据copy一遍组装
     *
     * @param children 系列子list
     * @return 组装好的list
     */
    private List<Classification> copyData(List<ClassificationChild> children) {
        List<Classification> list = new ArrayList<>();
        for (ClassificationChild classificationChild : children) {
            Classification classification = new Classification();
            classification.setId(classificationChild.getId());
            classification.setName(classificationChild.getName());
            classification.setCourseId(classificationChild.getCourseId());
            classification.setOrder(classificationChild.getOrder());
            classification.setParentChapterId(classificationChild.getParentChapterId());
            classification.setVisible(classificationChild.getVisible());
            list.add(classification);
        }
        return list;
    }
}
