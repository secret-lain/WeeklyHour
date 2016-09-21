package org.weeklyhour.MainActivity.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.weeklyhour.MainActivity.Fragment.DefaultFragment.DefaultFragment;
import org.weeklyhour.MainActivity.Fragment.RecyclerListFragment.RecyclerListFragment;

/**
 * ViewPager Adapter
 * 현재 달력 Fragment는 사용하지 않고있다.
 */
public class SectionsPageAdapter extends FragmentPagerAdapter {
    private final int _tabCount = 2;
    private final String[] _tabTitle= {"주간목표", "달력", "할일"};

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return RecyclerListFragment.newInstance();
            //case 1:
            //    return CalendarFragment.newInstance();
            case 1:
                return DefaultFragment.newInstance(123);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // return total tab count
        return _tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //탭 이름 반환
        switch (position) {
            case 0:
                return _tabTitle[0];
            case 1:
                return _tabTitle[2];
            //case 2:
            //    return _tabTitle[2];
        }
        return null;
    }
}
