package com.wss.module.main.ui.im.adapter;

import android.content.Context;
import android.view.View;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.utils.ActivityToActivity;
import com.wss.module.main.R;
import com.wss.module.main.bean.IMMessage;
import com.wss.module.main.ui.im.IMUserInfoActivity;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describe：聊天列表适配器
 * Created by 吴天强 on 2018/10/30.
 */

public class IMAdapter extends BaseListAdapter<IMMessage> {


    public IMAdapter(Context context, List<IMMessage> items) {
        super(context, items, null);
    }


    @Override
    public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, final IMMessage data) {
        //        if (viewType == IMMessage.TYPE_SEND) {
////            holder.setBackgroundResource(R.id.iv_head, item.getIcon());
//            holder.setText(R.id.tv_message, item.getMsg());
//        } else {
////            holder.setBackgroundResource(R.id.iv_head, item.getIcon());
//        }
        //因为两边布局用的是相同的ID 故可如此设置数据
        holder.setText(R.id.tv_message, data.getMsg());
        holder.setOnClickListener(R.id.iv_head, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("IMUserInfoActivity", data);
                ActivityToActivity.toActivity(getContext(), IMUserInfoActivity.class, map);
            }
        });
    }

    @Override
    protected IMulItemViewType<IMMessage> offerMultiItemViewType() {
        return new IMulItemViewType<IMMessage>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position, IMMessage imMessage) {
                return imMessage.getType();
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == IMMessage.TYPE_SEND) {
                    return R.layout.main_item_of_im_right;
                }
                return R.layout.main_item_of_im_left;
            }
        };
    }
}
