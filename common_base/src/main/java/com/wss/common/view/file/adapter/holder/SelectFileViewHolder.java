package com.wss.common.view.file.adapter.holder;

import com.wss.common.base.R;
import com.wss.common.constants.Constants;
import com.wss.common.view.file.bean.FileInfo;

import org.byteam.superadapter.IMulItemViewType;
import org.jetbrains.annotations.NotNull;

/**
 * Describe：选择文件多View
 * Created by 吴天强 on 2020/6/20.
 */
public class SelectFileViewHolder implements IMulItemViewType<FileInfo> {

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position, @NotNull FileInfo fileInfo) {
        return fileInfo.getType();
    }

    @Override
    public int getLayoutId(int viewType) {
        if (viewType == Constants.FileType.IMAGE) {
            return R.layout.item_of_select_image;
        }
        return R.layout.item_of_select_file;
    }
}
