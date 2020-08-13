package com.wss.common.view.file.mvp;


import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ValidUtils;
import com.wss.common.view.file.mvp.contract.SelectFileContract;
import com.wss.common.view.file.mvp.model.SelectFileModel;

/**
 * Describe：选择文件控制器
 * Created by 吴天强 on 2020/6/20.
 */
public class SelectFilePresenter extends BasePresenter<SelectFileModel, SelectFileContract.View>
        implements SelectFileContract.Presenter {
    @Override
    protected SelectFileModel createModule() {
        return new SelectFileModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        showLoading();
        getModel().queryFile(getView().getType()).subscribe(
                fileInfoList -> {
                    dismissLoading();
                    if (ValidUtils.isValid(fileInfoList)) {
                        getView().refreshFileList(fileInfoList);
                    } else {
                        getView().onEmpty("");
                    }
                }, t -> {
                    dismissLoading();
                    getView().onError("", t.getMessage());
                });
    }
}
