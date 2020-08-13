package com.wss.common.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.base.adapter.BaseListAdapter;
import com.wss.common.base.adapter.listener.OnListItemClickListener;
import com.wss.common.bean.SelectorData;

import org.byteam.superadapter.SuperViewHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：弹窗选择器
 * Created by 吴天强 on 2020/4/21.
 */
public class SelectorPopupWindow extends PopupWindow {

    private Context context;
    private View parent;
    private OnItemClickListener itemClickListener;
    private PopupWindow.OnDismissListener dismissListener;
    private List<SelectorData> dataList = new ArrayList<>();
    private String defaultChecked;

    /**
     * @param context context
     * @param parent  显示位置父控件
     */
    public SelectorPopupWindow(Context context, View parent, List<SelectorData> dataList) {
        this.context = context;
        this.parent = parent;
        this.dataList.addAll(dataList);
    }


    /**
     * 弹窗消失监听
     *
     * @param dismissListener 监听
     * @return SelectorPopupWindow
     */
    public SelectorPopupWindow setDismissListener(PopupWindow.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
        return this;
    }

    /**
     * Item点击事件
     *
     * @param itemClickListener 事件
     */
    public SelectorPopupWindow setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    /**
     * 默认选择项
     *
     * @param defaultChecked 默认值
     * @return SelectorPopupWindow
     */
    public SelectorPopupWindow setDefaultChecked(String defaultChecked) {
        this.defaultChecked = defaultChecked;
        return this;
    }

    public void show() {
        initView(ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 显示自定义宽度值
     *
     * @param value 宽度值
     */
    public void showMonthPopup(int value) {
        initView(PxUtils.dp2px(value));
    }

    private void initView(int width) {
        View childView = View.inflate(context, R.layout.pop_selector, null);
        childView.findViewById(R.id.close).setOnClickListener(v -> dismiss());
        initItems(childView);
        setWidth(width);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0);
        setBackgroundDrawable(dw);
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(childView);
        showAsDropDown(parent);
        update();
        setOnDismissListener(dismissListener);
    }

    private void initItems(@NotNull View childView) {
        RecyclerView recyclerView = childView.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SelectorAdapter(context, dataList, R.layout.item_of_selector_pop,
                (data, position) -> {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(data);
                    }
                    dismiss();
                }, defaultChecked));
    }


    @Override
    public void showAsDropDown(View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1 && !RomUtil.isVivo()) {
                //9.0
                height = anchor.getResources().getDisplayMetrics().heightPixels - (visibleFrame.bottom - PxUtils.dp2px(30));
            } else {
                height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            }
            setHeight(height);
        }
        super.showAsDropDown(anchor);
    }

    /**
     * 列表适配器
     */
    private static class SelectorAdapter extends BaseListAdapter<SelectorData> {

        private String defaultChecked;

        SelectorAdapter(Context context, List<SelectorData> mData, int layoutResId, OnListItemClickListener<SelectorData> listener, String defaultChecked) {
            super(context, mData, layoutResId, listener);
            this.defaultChecked = defaultChecked;
        }

        @Override
        public void onBindData(@NotNull SuperViewHolder holder, int viewType, int layoutPosition, @NotNull SelectorData data) {
            TextView tvName = holder.findViewById(R.id.tv_name);
            tvName.setText(data.getName());
            tvName.setSelected(TextUtils.equals(defaultChecked, data.getName()));
        }
    }


    /**
     * Item点击事件监听
     */
    public interface OnItemClickListener {

        /**
         * Item点击回调
         *
         * @param data 选择数据
         */
        void onItemClick(SelectorData data);
    }
}