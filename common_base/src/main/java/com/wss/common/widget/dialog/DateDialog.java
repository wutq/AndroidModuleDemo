package com.wss.common.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.view.WheelView;
import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.utils.DateUtils;
import com.wss.common.utils.PxUtils;
import com.wss.common.utils.ValidUtils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Describe：自定义选择日期对话框,适用于在PopupWindow之上弹出
 * Created by 吴天强 on 2019年7月27日18:13:09
 */
@SuppressLint("DefaultLocale")
public class DateDialog {


    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;


    @BindView(R2.id.wv_year)
    WheelView wvYear;

    @BindView(R2.id.wv_month)
    WheelView wvMonth;

    @BindView(R2.id.wv_day)
    WheelView wvDay;

    private Dialog dialog;
    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;
    private List<String> yearList = new ArrayList<>();
    private List<String> monthList = new ArrayList<>();
    private List<String> dayList = new ArrayList<>();
    /**
     * 当前年份下标
     */
    private int yearPosition = 0;
    /**
     * 当前月份下标
     */
    private int monthPosition = 0;
    /**
     * 当前日下标
     */
    private int dayPosition = 0;
    /**
     * 默认显示的日期 yyyy-MM-dd
     */
    private String defaultDate = DateUtils.getCurrentDateStr();
    private OnDateSelectListener dateSelectListener;
    /**
     * 切换过日期
     */
    private boolean changeDay = false;

    public DateDialog(Context context) {
        View dialogView = View.inflate(context, R.layout.dailog_date, null);
        ButterKnife.bind(this, dialogView);
        //底部弹出对话框
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialogView.setMinimumWidth(PxUtils.getScreenWidth(context));
        dialog.setContentView(dialogView);
        Window window = dialog.getWindow();
        if (window != null) {
            window.getAttributes().gravity = Gravity.BOTTOM;
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.x = 0;
            lp.y = 0;
            window.setAttributes(lp);
        }
    }

    /**
     * 日期选择回调
     */
    public DateDialog setOnDateSelectListener(OnDateSelectListener dateSelectListener) {
        this.dateSelectListener = dateSelectListener;
        return this;
    }

    /**
     * 设置开始 结束年份
     */
    public DateDialog setYearStartEnd(int startYear, int endYear) {
        this.startYear = startYear;
        this.endYear = endYear;
        return this;
    }

    /**
     * 设置当前日期  格式yyyy-MM-dd
     */
    public DateDialog setCurrentDate(String date) {
        if (ValidUtils.isValid(date)) {
            this.defaultDate = date;
        }
        return this;
    }

    private void initView() {
        initData();
        //设置"年"的显示数据
        wvYear.setAdapter(new ArrayWheelAdapter<>(yearList));
        // 初始化时显示的数据
        wvYear.setCurrentItem(yearPosition);
        wvYear.setOnItemSelectedListener(index -> {
            yearPosition = index;
            initDayDate();
        });
        wvMonth.setAdapter(new ArrayWheelAdapter<>(monthList));
        wvMonth.setCurrentItem(monthPosition);
        wvMonth.setOnItemSelectedListener(index -> {
            monthPosition = index;
            initDayDate();
        });
        initDayDate();
    }

    /**
     * 初始化年份 月份数据
     */
    private void initData() {
        yearList.clear();
        monthList.clear();
        String year = DateUtils.getFormatDate(defaultDate, "yyyy");
        String month = DateUtils.getFormatDate(defaultDate, "MM");
        int yearIndex = 0;
        for (int i = startYear; i < endYear + 1; i++) {
            yearList.add(String.format("%s年", i));
            if (i == Integer.parseInt(year)) {
                yearPosition = yearIndex;
            }
            yearIndex++;
        }
        for (int i = 1; i < 12 + 1; i++) {
            monthList.add(String.format("%02d月", i));
            if (i == Integer.parseInt(month)) {
                monthPosition = i - 1;
            }
        }
    }

    /**
     * 初始化日数据
     */
    private void initDayDate() {
        dayList.clear();
        int maxDay = 31;
        String monthString = monthList.get(monthPosition);
        switch (Integer.parseInt(monthString.substring(0, monthString.length() - 1))) {
            case 2:
                String yearString = yearList.get(yearPosition);
                int year = Integer.parseInt(yearString.substring(0, yearString.length() - 1));
                maxDay = isLeapYear(year) ? 28 : 29;
                break;
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 11:
                maxDay = 30;
                break;
            default:
                break;
        }
        String day = DateUtils.getFormatDate(defaultDate, "dd");
        for (int i = 1; i < maxDay + 1; i++) {
            dayList.add(String.format("%02d日", i));
            if (!changeDay && i == Integer.parseInt(day)) {
                dayPosition = i - 1;
            }
        }
        wvDay.setAdapter(new ArrayWheelAdapter<>(dayList));
        wvDay.setCurrentItem(dayPosition);
        wvDay.setOnItemSelectedListener(index -> {
            changeDay = true;
            dayPosition = index;
        });
    }

    /**
     * 是否闰年
     *
     * @param year 年份
     * @return boolean
     */
    @Contract(pure = true)
    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    private boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 显示对话框
     */
    public void show() {
        if (!isShowing()) {
            initView();
            dialog.show();
        }
    }


    @OnClick({R2.id.btnCancel, R2.id.btnSubmit})
    public void onViewClicked(@NotNull View view) {
        if (view.getId() == R.id.btnSubmit) {
            if (dateSelectListener != null) {
                String yearString = yearList.get(yearPosition);
                String monthString = monthList.get(monthPosition);
                String dayString = dayList.get(dayPosition);
                dateSelectListener.selectDate(
                        yearString.substring(0, yearString.length() - 1),
                        monthString.substring(0, monthString.length() - 1),
                        dayString.substring(0, dayString.length() - 1));
            }

        }
        dialog.dismiss();
    }

    /**
     * 日期选择监听器
     */
    public interface OnDateSelectListener {
        /**
         * 日期选择了
         *
         * @param year  年
         * @param month 月
         * @param day   日
         */
        void selectDate(String year, String month, String day);
    }
}