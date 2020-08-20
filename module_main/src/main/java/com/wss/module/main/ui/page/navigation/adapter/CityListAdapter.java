package com.wss.module.main.ui.page.navigation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.base.adapter.listener.OnListItemClickListener;
import com.wss.module.main.R;
import com.wss.module.main.ui.page.navigation.bean.City;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Describe：开户行城市、总行适配器
 * Created by 吴天强 on 2019/10/30.
 */
public class CityListAdapter extends BaseListAdapter<City> {


    public CityListAdapter(Context context, List<City> items, OnListItemClickListener<City> listener) {
        super(context, items, R.layout.main_item_of_city_list, listener);
    }

    @Override
    public void onBindData(@NotNull SuperViewHolder holder, int viewType, int position, @NotNull City data) {
        holder.setText(R.id.tv_name, data.getName());
        View line = holder.findViewById(R.id.line);
        String oldLetter = "";
        if (position > 0) {
            oldLetter = String.valueOf(getData().get(position - 1).getInitials().charAt(0)).toUpperCase();
        }
        TextView tvHeader = holder.findViewById(R.id.tv_header);
        String initial = String.valueOf(data.getInitials().charAt(0)).toUpperCase();
        if (!TextUtils.equals(oldLetter, initial)) {
            line.setVisibility(View.VISIBLE);
            tvHeader.setVisibility(View.VISIBLE);
            tvHeader.setText(initial);
        } else {
            tvHeader.setVisibility(View.GONE);
        }
        //判断是否显示线条
        if (position >= getData().size() - 1) {
            //到最后了 直接隐藏
            line.setVisibility(View.GONE);
        } else {
            String nextLetter = String.valueOf(getData().get(position + 1).getInitials().charAt(0)).toUpperCase();
            if (!TextUtils.equals(nextLetter, initial)) {
                line.setVisibility(View.GONE);
            } else {
                line.setVisibility(View.VISIBLE);
            }
        }
    }
}
