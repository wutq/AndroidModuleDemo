package com.wss.common.base.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.wss.common.base.BaseFragment;
import com.wss.common.bean.HorizontalTabTitle;

import java.util.List;

/**
 * Describe：FragmentPagerAdapter
 * Created by 吴天强 on 2018/10/22.
 */

public abstract class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    private BaseFragment[] fragments;
    private List<HorizontalTabTitle> titles;

    public FragmentPagerAdapter(FragmentManager fm, List<HorizontalTabTitle> titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles != null ? titles.size() : 0;
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments == null) {
            fragments = new BaseFragment[titles.size()];
        }
        BaseFragment fragment = getTabFragment();
        fragment.setFragmentData(titles.get(position));
        fragments[position] = fragment;
        return fragment;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position).getTitle();
    }

    public abstract BaseFragment getTabFragment();
}
