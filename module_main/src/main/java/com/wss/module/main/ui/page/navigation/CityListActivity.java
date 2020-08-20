package com.wss.module.main.ui.page.navigation;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.ValidUtils;
import com.wss.common.widget.SlideBarView;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.navigation.adapter.CityListAdapter;
import com.wss.module.main.ui.page.navigation.bean.City;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Describe：字母导航选择城市
 * Created by 吴天强 on 2020/8/20.
 */
public class CityListActivity extends BaseActionBarActivity {

    @BindView(R2.id.recycle_view)
    RecyclerView recycleView;

    @BindView(R2.id.tv_initial)
    TextView tvInitial;

    @BindView(R2.id.sbbv_city)
    SlideBarView sbbvCity;

    /**
     * 记录SlideBar的滚动位置
     */
    private WeakHashMap<String, Integer> positionsCache = new WeakHashMap<>();
    private List<City> cityList = new ArrayList<>();

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_city_list;
    }

    @Override
    protected void initView() {
        setCenterText("字母侧边导航");
        createCity();
        sbbvCity.setIndexs(createIndexs(cityList));
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setAdapter(new CityListAdapter(context, cityList, (data, position) -> ToastUtils.show(data)));

        //右侧字母导航条
        sbbvCity.setFlipListener(new SlideBarView.OnSlideBarBaseViewFlipListener() {
            @Override
            public void onFlip(int index, String mChar) {
                tvInitial.setVisibility(View.VISIBLE);
                tvInitial.setText(mChar);
                if (TextUtils.equals(mChar, "#")) {
                    scrollCityList(cityList.size());
                } else {
                    Integer indexInList = positionsCache.get(mChar);
                    if (ValidUtils.isValid(indexInList) && ValidUtils.isValid(cityList, indexInList)) {
                        scrollCityList(indexInList);
                    } else {
                        for (int i = 0; i < cityList.size(); i++) {
                            String cityPinyin = cityList.get(i).getInitials();
                            if (ValidUtils.isValid(cityPinyin)) {
                                String initial = String.valueOf(cityPinyin.charAt(0));
                                if (mChar.equalsIgnoreCase(initial)) {
                                    indexInList = i;
                                    break;
                                }
                            }
                        }
                        if (indexInList != null && indexInList != -1) {
                            positionsCache.put(mChar, indexInList);
                            scrollCityList(indexInList);
                        }
                    }
                }
            }

            @Override
            public void onFlipUp() {
                tvInitial.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 滚动列表
     */
    private void scrollCityList(int position) {
        if (position > 6) {
            position = position + 4;
        }
        recycleView.scrollToPosition(position);
    }

    /**
     * 根据数据获取首字符串
     */
    @NotNull
    private char[] createIndexs(@NotNull List<City> cityList) {
        StringBuilder sb = new StringBuilder();
        for (City city : cityList) {
            String initial = String.valueOf(city.getInitials()).toUpperCase();
            if (!sb.toString().contains(initial)) {
                sb.append(initial);
            }
        }
        sb.append("#");
        return sb.toString().toCharArray();
    }

    private void createCity() {
        cityList.add(new City("A", "Amanda"));
        cityList.add(new City("A", "阿尔巴"));
        cityList.add(new City("A", "阿坝州"));
        cityList.add(new City("A", "阿里巴巴和四十大盗"));
        cityList.add(new City("A", "阿里山"));
        cityList.add(new City("B", "北京"));
        cityList.add(new City("B", "北凉"));
        cityList.add(new City("B", "贝塔"));
        cityList.add(new City("B", "B-阿尔法"));
        cityList.add(new City("B", "卜璐子"));
        cityList.add(new City("B", "半山腰"));
        cityList.add(new City("B", "棒子面"));
        cityList.add(new City("C", "长宁"));
        cityList.add(new City("D", "底下"));
        cityList.add(new City("E", "额噢哟"));
        cityList.add(new City("F", "法兰西"));
        cityList.add(new City("G", "歌乐山"));
        cityList.add(new City("G", "工地"));
        cityList.add(new City("G", "高亮"));
        cityList.add(new City("G", "高密"));
        cityList.add(new City("G", "高山"));
        cityList.add(new City("G", "钢铁锅"));
        cityList.add(new City("G", "光明顶"));
        cityList.add(new City("G", "尕山"));
        cityList.add(new City("H", "黄珊珊"));
        cityList.add(new City("J", "揭阳"));
        cityList.add(new City("J", "剑门关"));
        cityList.add(new City("J", "见南山"));
        cityList.add(new City("J", "江油"));
        cityList.add(new City("J", "江南"));
        cityList.add(new City("K", "狂怒"));
        cityList.add(new City("K", "克朗"));
        cityList.add(new City("L", "莱阳"));
        cityList.add(new City("L", "榔榆"));
        cityList.add(new City("L", "琅琊"));
        cityList.add(new City("M", "磨坊岭"));
        cityList.add(new City("M", "磨坊岭"));
        cityList.add(new City("N", "孥"));
        cityList.add(new City("P", "貔貅"));
        cityList.add(new City("P", "毗邻"));
        cityList.add(new City("P", "披露之"));
        cityList.add(new City("P", "胖大大"));
        cityList.add(new City("P", "PPPP"));
        cityList.add(new City("P", "坡度"));
        cityList.add(new City("R", "日朗逸"));
        cityList.add(new City("S", "萨洛西"));
        cityList.add(new City("T", "体院"));
        cityList.add(new City("V", "V源义"));
        cityList.add(new City("W", "王可爱"));
        cityList.add(new City("X", "西湖"));
        cityList.add(new City("X", "吸烟机"));
        cityList.add(new City("Y", "阳明"));
        cityList.add(new City("Z", "张掖"));
    }

}
