package com.wss.common.widget.dialog;

/**
 * Describe：多条目对话框Item被点击事件
 * Created by 吴天强 on 2019/3/31.
 */
public interface OnItemClickListener {

    /**
     * 点击事件
     *
     * @param position position
     * @param val      值
     */
    void onItemClick(int position, String val);
}
