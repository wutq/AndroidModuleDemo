package com.wss.module.main.ui.page.selector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.wss.common.utils.DateUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.ui.page.selector.bean.DateItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Describe：弹窗选择器
 * Created by 吴天强 on 2020/4/21.
 */
public class SelectorDatePopupWindow extends PopupWindow {
    private static final int MAX_MINUTE = 12;
    private static final int MAX_SECOND = 60;
    private static final String[] WEEKS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    private Context context;
    private View parent;

    private List<DateItem> dateList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private List<String> minuteList = new ArrayList<>();
    private List<String> secondList = new ArrayList<>();
    private int datePosition, dayPosition, minutePosition, secondPosition;


    /**
     * @param context context
     * @param parent  显示位置父控件
     */
    public SelectorDatePopupWindow(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    public void show() {
        initData();
        initView();
    }

    private void initData() {
        dateList.addAll(createDateList());

        dayList.add("上午");
        dayList.add("下午");

        for (int i = 0; i < MAX_MINUTE; i++) {
            minuteList.add(String.format(Locale.CHINA, "%02d", (i + 1)));
        }

        for (int i = 0; i < MAX_SECOND; i++) {
            if (i % 5 == 0) {
                secondList.add(String.format(Locale.CHINA, "%02d", i));
            }
        }
    }

    private void initView() {
        View childView = View.inflate(context, R.layout.main_pop_of_selector_date, null);
        childView.findViewById(R.id.close).setOnClickListener(v -> dismiss());
        initItems(childView);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0);
        setBackgroundDrawable(dw);
        setFocusable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);
        setContentView(childView);
        showAtLocation(parent, Gravity.CENTER, 0, 0);
        update();

    }

    private void initItems(@NotNull View childView) {
        WheelView wvDate = childView.findViewById(R.id.wv_date);
        WheelView wvDay = childView.findViewById(R.id.wv_day);
        WheelView wvMinute = childView.findViewById(R.id.wv_minute);
        WheelView wvSecond = childView.findViewById(R.id.wv_second);

        wvDate.setAdapter(new ArrayWheelAdapter<>(dateList));
        wvDate.setCurrentItem(datePosition);
        wvDate.setCyclic(false);
        wvDate.setOnItemSelectedListener(index -> {
            datePosition = index;
        });
        wvDay.setAdapter(new ArrayWheelAdapter<>(dayList));
        wvDay.setCurrentItem(dayPosition);
        wvDay.setCyclic(false);
        wvDay.setOnItemSelectedListener(index -> {
            dayPosition = index;
        });

        wvMinute.setAdapter(new ArrayWheelAdapter<>(minuteList));
        wvMinute.setCurrentItem(minutePosition);
        wvMinute.setOnItemSelectedListener(index -> {
            minutePosition = index;
        });
        wvMinute.setCyclic(false);

        wvSecond.setAdapter(new ArrayWheelAdapter<>(secondList));
        wvSecond.setCurrentItem(secondPosition);
        wvSecond.setOnItemSelectedListener(index -> {
            secondPosition = index;
        });
        wvSecond.setGravity(Gravity.START);
        wvSecond.setCyclic(false);
        childView.findViewById(R.id.btn_left).setOnClickListener(v -> dismiss());
        childView.findViewById(R.id.btn_right).setOnClickListener(v -> {
            String data = dateList.get(datePosition).getDate() + dayList.get(dayPosition) +
                    minuteList.get(minutePosition) + secondList.get(secondPosition);
            ToastUtils.show(data);
            dismiss();
        });
    }

    /**
     * 创建日期列表
     *
     * @return List<String> c
     */
    @NotNull
    private List<DateItem> createDateList() {
        int count = 14;
        List<DateItem> list = new ArrayList<>();
        Calendar calender = Calendar.getInstance();
        Date today = new Date();
        calender.setTime(today);
        for (int i = 0; i <= count; i++) {
            DateItem dateItem = new DateItem();
            calender.add(Calendar.DATE, i);
            Date time = calender.getTime();
            dateItem.setDate(DateUtils.getFormatDate(time, DateUtils.DATE_FORMAT_LINE));
            if (time.getTime() == today.getTime()) {
                //今天
                dateItem.setShowText("今天");
            } else {
                String week = WEEKS[(calender.get(Calendar.DAY_OF_WEEK) - 1)];
                String formatDate = DateUtils.getFormatDate(calender.getTime(), "MM月dd日");
                dateItem.setShowText(String.format("%s %s", formatDate, week));
            }
            list.add(dateItem);
            calender.setTime(today);
        }
        return list;
    }
}