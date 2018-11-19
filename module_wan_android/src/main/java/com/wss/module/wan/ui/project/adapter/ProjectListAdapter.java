package com.wss.module.wan.ui.project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.PxUtils;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Article;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：项目列表适配器
 * Created by 吴天强 on 2018/11/15.
 */

public class ProjectListAdapter extends BaseListAdapter<Article> {


    public ProjectListAdapter(Context context, List<Article> items, int layoutResId, OnListItemClickListener listener) {
        super(context, items, layoutResId, listener);
    }

    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, final Article data) {
        ImageView imageView = holder.findViewById(R.id.iv_img);
        TextView tvDes = holder.findViewById(R.id.tv_des);
        if (!TextUtils.isEmpty(data.getEnvelopePic())) {
            ImageUtils.loadImage(imageView, data.getEnvelopePic());
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            tvDes.setMinHeight(PxUtils.dp2px(getContext(), 25));
            tvDes.setMinimumHeight(PxUtils.dp2px(getContext(), 25));

        }
        Logger.e("getMinHeight:" + tvDes.getMinHeight());
        holder.setText(R.id.tv_des, data.getDesc());
        holder.setText(R.id.tv_author, String.format("作者：%s", data.getAuthor()));
        holder.setText(R.id.tv_date, data.getNiceDate());
    }
}
