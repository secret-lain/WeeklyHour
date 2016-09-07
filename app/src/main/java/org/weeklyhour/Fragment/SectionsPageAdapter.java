package org.weeklyhour.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import org.weeklyhour.Fragment.CalendarFragment.CalendarFragment;
import org.weeklyhour.Fragment.PlaceholderFragment.PlaceholderFragment;
import org.weeklyhour.Fragment.RecyclerListFragment.RecyclerListFragment;

/**
 * Created by admin on 2016-09-06.
 */
public class SectionsPageAdapter extends FragmentPagerAdapter {
    private final int _tabCount = 3;
    private final String[] _tabTitle= {"주간목표", "달력", "할일"};

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }
/*
    @Override
    public Fragment getItem(int position) {
        // 단지 섹션넘버 : n 이라고 써져있는 프래그먼트를 뿌림.
        if(position == 1)
            return CalendarFragment.newInstance("1", "2");
        return PlaceholderFragment.newInstance(position + 1);
    }
*/
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                //return PlaceholderFragment.newInstance(123);
                return RecyclerListFragment.newInstance();

            case 1:
                return CalendarFragment.newInstance("1","2");
            case 2:
                return PlaceholderFragment.newInstance(123);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return _tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return _tabTitle[0];
            case 1:
                return _tabTitle[1];
            case 2:
                return _tabTitle[2];
        }
        return null;
    }
}
