package com.wss.module.main.ui.selector;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.orhanobut.logger.Logger;
import com.wss.common.base.ActionBarActivity;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.bean.Province;
import com.wss.module.main.ui.selector.mvp.contract.SelectContract;
import com.wss.module.main.ui.selector.mvp.SelectorPresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;

/**
 * Describe：多种选择器
 * Created by 吴天强 on 2018/10/24.
 */

public class SelectorActivity extends ActionBarActivity<SelectorPresenter> implements SelectContract.View {
    private Calendar selectDate;
    private List<Province> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private List<List<List<String>>> options3Items = new ArrayList<>();
    private TimePickerView lunarPicker;//农历日历

    private List<String> user = new ArrayList<>();//自定义
    private List<String> userFrom = new ArrayList<>();//自定义
    private List<String> userDes = new ArrayList<>();//自定义

    @Override
    protected SelectorPresenter createPresenter() {
        return new SelectorPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_selector;
    }

    @Override
    protected void initView() {
        Logger.e("SelectorActivity initView");
        setTitleText("多功能选择器");
        selectDate = Calendar.getInstance();
        presenter.start();
    }

    /**
     * 注意：lib中使用  ButterKnife 绑定事件用R2 判断 用if else 判断里面用R
     */
    @OnClick({R2.id.btn_01, R2.id.btn_02, R2.id.btn_03, R2.id.btn_04, R2.id.btn_05})
    public void onBtnClick(View view) {
        if (view.getId() == R.id.btn_01) {
            showDateTime();
        } else if (view.getId() == R.id.btn_02) {
            showAddress();
        } else if (view.getId() == R.id.btn_03) {
            showLunarPicker();
        } else if (view.getId() == R.id.btn_04) {
            showUserList();
        } else if (view.getId() == R.id.btn_05) {
            showUserInfoList();
        }
    }


    /**
     * 农历时间已扩展至 ： 1900 - 2100年
     */
    private void showLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2069, 2, 28);

        //时间选择器 ，自定义布局
        lunarPicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                ToastUtils.showToast(mContext, getTime(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.main_pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        v.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lunarPicker.returnData();
                                lunarPicker.dismiss();
                            }
                        });
                        v.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lunarPicker.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                lunarPicker.setLunarCalendar(!lunarPicker.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.theme))
                .build();
        lunarPicker.show();
    }

    /**
     * 日期选择器
     */
    private void showDateTime() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                selectDate = calendar;
                ToastUtils.showToast(mContext, getTime(date));
            }
        }).build();
        //注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，
        // 避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.setDate(selectDate);
        pvTime.show();
    }


    /**
     * 三级联动地址
     */
    private void showAddress() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                ToastUtils.showToast(mContext, tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    /**
     * 单行显示
     */
    private void showUserList() {//条件选择器初始化，自定义布局

        OptionsPickerView<String> pvCustomOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                ToastUtils.showToast(mContext, user.get(options1));
            }
        })
                .setSelectOptions(2)
                .build();

        pvCustomOptions.setPicker(user);//添加数据
        pvCustomOptions.show();
    }

    /**
     * 多行不联动
     */
    private void showUserInfoList() {
        OptionsPickerView<String> pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = user.get(options1) + "来自" + userFrom.get(options2) + "是一个" + userDes.get(options3);
                ToastUtils.showToast(mContext, str);
            }
        })
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {

                    }
                })
                .setSelectOptions(0, 1, 1)
                .build();
        pvNoLinkOptions.setNPicker(user, userFrom, userDes);
        pvNoLinkOptions.setSelectOptions(0, 2, 1);
        pvNoLinkOptions.show();

    }


    @SuppressLint("SimpleDateFormat")
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    @Override
    public void addressList(List<Province> options1Items, List<List<String>> options2Items, List<List<List<String>>> options3Items) {
        this.options1Items.addAll(options1Items);
        this.options2Items.addAll(options2Items);
        this.options3Items.addAll(options3Items);
    }

    @Override
    public void userList(List<String> userList, List<String> userFrom, List<String> userDes) {
        this.user.addAll(userList);
        this.userFrom.addAll(userFrom);
        this.userDes.addAll(userDes);
    }
}
