package com.example.ash.cleantind_dgu_cse_se;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ash.cleantind_dgu_cse_se.tab1.TabFragment1;
import com.example.ash.cleantind_dgu_cse_se.tab2.TabFragment2;
import com.example.ash.cleantind_dgu_cse_se.tab3.TabFragment3;
import com.example.ash.cleantind_dgu_cse_se.tab4.TabFragment4;

/**
 * Created by ash on 2016-11-06
 * TabLayout (Google제공 API)
 * 를 위한 어뎁터 클래스.
 */
public class TabPagerAdapter  extends FragmentStatePagerAdapter {

    // Count number of tabs
   private int tabCount;

    //int count 잠시 삭제 (파라메터)
    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                TabFragment1 tabFragment1 = new TabFragment1();
                return tabFragment1;
            case 1:
                TabFragment2 tabFragment2 = new TabFragment2();
                return tabFragment2;
            case 2:
                TabFragment3 tabFragment3 = new TabFragment3();
                return tabFragment3;
            case 3:
                TabFragment4 tabFragment4 = new TabFragment4();
                return tabFragment4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}