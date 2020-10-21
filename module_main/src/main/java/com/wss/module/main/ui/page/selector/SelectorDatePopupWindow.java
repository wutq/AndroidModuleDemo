package com.wss.module.main.ui.page.selector;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.wss.common.utils.DateUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.ui.page.selector.bean.DateItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * Describe：弹窗选择器
 * Created by 吴天强 on 2020/4/21.
 */
public class SelectorDatePopupWindow extends PopupWindow {
    private static final int MAX_HOUR = 12;
    private static final int MAX_MINUTE = 60;
    private static final String[] WEEKS = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private static final String MORNING = "上午";
    private static final String AFTERNOON = "下午";
    /**
     * 延时的分钟数  默认30分钟
     */
    private static final int DELAYED_MINUTE = 30;
    /**
     * 最大可选天数
     */
    private static final int MAX_DAY_COUNT = 14;

    private Context context;
    private View parent;
    private WheelView wvDay, wvHour, wvMinute;

    private List<DateItem> dateList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    private List<String> hourList = new ArrayList<>();
    private List<String> minuteList = new ArrayList<>();
    private int datePosition, dayPosition, hourPosition, minutePosition;


    /**
     * @param context context
     * @param parent  显示位置父控件
     */
    public SelectorDatePopupWindow(Context context, View parent) {
        this.context = context;
        this.parent = parent;
    }

    public void show() {
        initView();
    }


    /**
     * 初始化View
     */
    private void initView() {
        View childView = View.inflate(context, R.layout.main_pop_of_selector_date, null);
        childView.findViewById(R.id.close).setOnClickListener(v -> dismiss());
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
        dateList.addAll(createDateList());
        createDayHourMinuteList();
        initItems(childView);
    }

    /**
     * 初始化Item
     *
     * @param childView View
     */
    private void initItems(@NonNull View childView) {
        WheelView wvDate = childView.findViewById(R.id.wv_date);
        wvDay = childView.findViewById(R.id.wv_day);
        wvHour = childView.findViewById(R.id.wv_hour);
        wvMinute = childView.findViewById(R.id.wv_minute);

        wvDate.setAdapter(new ArrayWheelAdapter<>(dateList));
        wvDate.setCurrentItem(datePosition);
        wvDate.setCyclic(false);
        wvDate.setOnItemSelectedListener(index -> {
            datePosition = index;
            createDayHourMinuteList();
            initDayItems();
        });
        childView.findViewById(R.id.btn_left).setOnClickListener(v -> dismiss());
        childView.findViewById(R.id.btn_right).setOnClickListener(v -> {
            String hour = hourList.get(hourPosition);
            if (AFTERNOON.equals(dayList.get(dayPosition))) {
                //时间是下午 需要把小时加12
                if (MAX_HOUR == Integer.parseInt(hour)) {
                    hour = "00";
                } else {
                    hour = String.format(Locale.CHINA, "%02d", (Integer.parseInt(hour) + 12));
                }
            }
            String dateString = String.format("%s %s:%s", dateList.get(datePosition).getDate(),
                    hour, minuteList.get(minutePosition));
            ToastUtils.show(context, dateString);
            dismiss();
        });
        //设置View的高度不可超过屏幕
        EditText editText = childView.findViewById(R.id.edt_input);
        editText.setMaxHeight(PxUtils.getScreenHeight(context) / 3);
        initDayItems();
    }

    /**
     * 初始化上午、下午Item
     */
    private void initDayItems() {
        wvDay.setAdapter(new ArrayWheelAdapter<>(dayList));
        wvDay.setCurrentItem(dayPosition);
        wvDay.setCyclic(false);
        wvDay.setOnItemSelectedListener(index -> {
            dayPosition = index;
            createDayHourMinuteList();
            initHourItems();
        });
        initHourItems();
    }

    /**
     * 初始化小时Item
     */
    private void initHourItems() {
        wvHour.setAdapter(new ArrayWheelAdapter<>(hourList));
        wvHour.setCurrentItem(hourPosition);
        wvHour.setOnItemSelectedListener(index -> {
            hourPosition = index;
            createDayHourMinuteList();
            initMinuteItems();
        });
        wvHour.setCyclic(false);
        initMinuteItems();
    }

    /**
     * 初始化分钟Item
     */
    private void initMinuteItems() {
        wvMinute.setAdapter(new ArrayWheelAdapter<>(minuteList));
        wvMinute.setCurrentItem(minutePosition);
        wvMinute.setOnItemSelectedListener(index -> {
            minutePosition = index;
        });
        wvMinute.setGravity(Gravity.START);
        wvMinute.setCyclic(false);
    }

    /**
     * 创建日期列表
     *
     * @return List<String> c
     */
    @NonNull
    private List<DateItem> createDateList() {
        List<DateItem> list = new ArrayList<>();
        Calendar calender = Calendar.getInstance();
        Date today = new Date();
        calender.setTime(today);
        int start = 0;
        int max = MAX_DAY_COUNT;
        //判断DELAYED_MINUTE分钟以后是不是明天
        if (DateUtils.isTomorrow(DateUtils.getCurrentTimeStamp() / 1000 + DELAYED_MINUTE * 60)) {
            start = 1;
            max = max + 1;
        }
        for (int i = start; i <= max; i++) {
            DateItem dateItem = new DateItem();
            calender.add(Calendar.DATE, i);
            Date time = calender.getTime();
            dateItem.setDate(DateUtils.getFormatDate(time, "yyyy-MM-dd"));
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

    /**
     * 创建小时列表
     */
    private void createDayHourMinuteList() {
        //上午、下午
        dayList.clear();
        dayList.add(MORNING);
        dayList.add(AFTERNOON);

        hourList.clear();
        minuteList.clear();
        String date = dateList.get(datePosition).getDate();
        int startHour = 0, startMinute = 0;
        if (DateUtils.isToday(date)) {
            //判断上午、下午
            String afterMinuteTime = DateUtils.getAfterMinuteTime(DELAYED_MINUTE);
            if (DateUtils.isDatePm(afterMinuteTime)) {
                //如果DELAYED_MINUTE分钟以后是下午 则把上午移除掉
                dayList.remove(0);
            }

            //如果包含上午、下午则只需要计算上午的时间选择
            String formatHour, formatMinute;
            if (dayList.size() == 1) {
                //只有下午
                formatHour = DateUtils.getFormatDate2(afterMinuteTime, "hh");
                startHour = Integer.parseInt(formatHour) - 1;
                if (hourPosition == 0) {
                    //选择的第一个小时
                    formatMinute = DateUtils.getFormatDate2(afterMinuteTime, "mm");
                    startMinute = Integer.parseInt(formatMinute) - 1;
                }
            } else if (dayList.size() == 2) {
                //包含上午、下午
                if (MORNING.equals(dayList.get(dayPosition))) {
                    //选择的上午
                    formatHour = DateUtils.getFormatDate2(afterMinuteTime, "hh");
                    startHour = Integer.parseInt(formatHour) - 1;
                    if (hourPosition == 0) {
                        //选择的第一个小时
                        formatMinute = DateUtils.getFormatDate2(afterMinuteTime, "mm");
                        startMinute = Integer.parseInt(formatMinute) - 1;
                    }

                }
            }
            //如果计算出来的分钟如果大于55则需要把小时+1，分钟归0，也就是取下一个小时的整点时间
            //这里不考虑+1小时是第二天的时间是因为，在createDateList()中已经计算过了
            if (startMinute > 55) {
                startHour++;
                startMinute = 0;
            }
        }
        for (int i = startHour; i < MAX_HOUR; i++) {
            hourList.add(String.format(Locale.CHINA, "%02d", (i + 1)));
        }
        for (int i = startMinute; i < MAX_MINUTE; i++) {
            if (i % 5 == 0) {
                minuteList.add(String.format(Locale.CHINA, "%02d", i));
            }
        }
    }

}