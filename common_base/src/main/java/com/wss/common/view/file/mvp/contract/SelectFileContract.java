package com.wss.common.view.file.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.view.file.bean.FileInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * Describe：选择文件契约类
 * Created by 吴天强 on 2020/6/20.
 */
public interface SelectFileContract {

    interface Model {
        /**
         * 查询文件
         *
         * @param type 类型
         * @return 文件列表
         */
        Observable<List<FileInfo>> queryFile(int type);
    }

    interface View extends IBaseView {

        /**
         * 选择文件的类型
         *
         * @return 类型
         */
        int getType();

        /**
         * 刷新文件列表
         *
         * @param fileList 文件列表
         */
        void refreshFileList(List<FileInfo> fileList);

    }

    interface Presenter {


    }
}
