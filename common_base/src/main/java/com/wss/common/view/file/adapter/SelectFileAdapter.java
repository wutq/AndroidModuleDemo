package com.wss.common.view.file.adapter;

import android.content.Context;

import com.wss.common.base.R;
import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.constants.Constants;
import com.wss.common.utils.ImageUtils;
import com.wss.common.view.file.adapter.holder.SelectFileViewHolder;
import com.wss.common.view.file.bean.FileInfo;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Describe：选择文件适配器
 * Created by 吴天强 on 2020/6/20.
 */
public class SelectFileAdapter extends BaseListAdapter<FileInfo> {

    private OnItemClickListener onItemClickListener;

    public SelectFileAdapter(Context context, List<FileInfo> mData, OnItemClickListener listener) {
        super(context, mData, new SelectFileViewHolder());
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull FileInfo data) {
        holder.findViewById(R.id.iftv_checked).setSelected(data.isChecked());
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(data, layoutPosition));
        }
        if (viewType == Constants.FileType.IMAGE) {
            //图片
            ImageUtils.loadImageCircleBead(holder.findViewById(R.id.iv_image), data.getFilePath(), 4);
        } else {
            holder.setText(R.id.tv_name, data.getFileName());
        }
    }

    public interface OnItemClickListener {
        /**
         * Item选择点击事件
         *
         * @param fileInfo 文件
         * @param position 下标
         */
        void onItemClick(FileInfo fileInfo, int position);
    }
}
