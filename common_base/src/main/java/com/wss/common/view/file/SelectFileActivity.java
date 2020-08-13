package com.wss.common.view.file;

import android.content.Intent;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.constants.Constants;
import com.wss.common.constants.Dic;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.view.file.adapter.SelectFileAdapter;
import com.wss.common.view.file.bean.FileInfo;
import com.wss.common.view.file.mvp.SelectFilePresenter;
import com.wss.common.view.file.mvp.contract.SelectFileContract;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：选择手机文件
 * Created by 吴天强 on 2020/6/20.
 */
public class SelectFileActivity extends BaseActionBarActivity<SelectFilePresenter> implements SelectFileContract.View {

    @BindView(R2.id.recycle_view)
    RecyclerView recyclerView;

    private SelectFileAdapter adapter;
    private List<FileInfo> fileList = new ArrayList<>();

    /**
     * 最大可选文件数
     */
    private int max = 9;

    @Override
    protected SelectFilePresenter createPresenter() {
        return new SelectFilePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_file;
    }

    @Override
    protected void initView() {
        if (getType() == Constants.FileType.IMAGE) {
            setCenterText(getString(R.string.slelect_image));
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        } else {
            setCenterText(getString(R.string.select_file));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

        }
        max = getIntent().getIntExtra(Dic.MAX_SELECT_FILE, max);
        adapter = new SelectFileAdapter(context, fileList, (fileInfo, position) -> {
            if (!fileInfo.isChecked()) {
                List<String> allSelect = getAllSelect();
                if (allSelect.size() >= max) {
                    if (getType() == Constants.FileType.IMAGE) {
                        ToastUtils.show(context, getString(R.string.max_select_image, max));
                    } else {
                        ToastUtils.show(context, getString(R.string.max_select_file, max));
                    }
                    return;
                }
            }
            fileList.get(position).setChecked(!fileList.get(position).isChecked());
            adapter.notifyItemChanged(position);
        });
        recyclerView.setAdapter(adapter);
        setRightText(R.string.confirm, v -> {
            ArrayList<String> allSelect = getAllSelect();
            if (allSelect.size() < 1) {
                ToastUtils.show(context, getString(R.string.no_select));
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(Dic.SELECT_FILE_PATHS, JsonUtils.toJson(allSelect));
            setResult(RESULT_OK, intent);
            finish();
        });
        getPresenter().start();
    }

    @Override
    public int getType() {
        return getIntent().getIntExtra(Dic.FROM_TYPE, Constants.FileType.IMAGE);
    }

    @Override
    public void refreshFileList(List<FileInfo> fileList) {
        this.fileList.clear();
        this.fileList.addAll(fileList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(Object tag, String errorMsg) {
        super.onError(tag, errorMsg);
        showErrorView(errorMsg);
    }

    @Override
    public void onEmpty(Object tag) {
        super.onEmpty(tag);
        showEmptyView();
    }

    /**
     * 获取全部已选中文件
     *
     * @return 所有已选中的文件的路径
     */
    @NotNull
    private ArrayList<String> getAllSelect() {
        ArrayList<String> pathList = new ArrayList<>();
        for (FileInfo info : fileList) {
            if (info.isChecked()) {
                pathList.add(info.getFilePath());
            }
        }
        return pathList;
    }
}
