package com.wss.module.wan.ui.project;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.listener.OnListItemClickListener;
import com.wss.module.wan.R;
import com.wss.module.wan.bean.Classification;

import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

/**
 * Describe：右侧弹出Pop
 * Created by 吴天强 on 2018/11/15.
 */

public class TypePopupWindow extends PopupWindow {

    private View parent;
    private Context mContext;
    private RecyclerView recyclerView;
    private List<Classification> data;
    private Classification classification;//当前显示的分类
    private OnProjectTypeClickListener listener;

    public TypePopupWindow(Context context) {
        super(context);
        this.mContext = context;
    }

    public TypePopupWindow setData(List<Classification> data) {
        this.data = data;
        return this;
    }

    public TypePopupWindow setCurrent(Classification classification) {
        this.classification = classification;
        return this;
    }


    public TypePopupWindow setListener(OnProjectTypeClickListener listener) {
        this.listener = listener;
        return this;
    }

    public void show(View view) {
        this.parent = view;
        initView();
    }

    private void initView() {
        View childView = View.inflate(mContext, R.layout.wan_pop_project_pop, null);
        childView.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        recyclerView = childView.findViewById(R.id.recycle_view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0);
        setAnimationStyle(R.style.ActionSheetDialogAnimation);
        setBackgroundDrawable(dw);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(childView);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        update();
        initList();
    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new TypeAdapter(mContext, data, R.layout.wan_item_of_setup_list, new OnListItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (listener != null) {
                    listener.onProjectTypeClick(data.get(position));
                    dismiss();
                }
            }
        }));
    }


    class TypeAdapter extends BaseListAdapter<Classification> {

        TypeAdapter(Context context, List<Classification> items, int layoutResId, OnListItemClickListener listener) {
            super(context, items, layoutResId, listener);
        }

        @Override
        public void onBindData(SuperViewHolder holder, int viewType, int layoutPosition, Classification data) {
            TextView textView = holder.findViewById(R.id.tv_text);
            if (classification != null && classification.getId() == data.getId()) {
                textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setBackgroundColor(mContext.getResources().getColor(R.color.theme));
            } else {
                textView.setTextColor(mContext.getResources().getColor(R.color.black));
                textView.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            }
            textView.setText(data.getName());
        }
    }

    interface OnProjectTypeClickListener {
        void onProjectTypeClick(Classification classification);
    }
}
