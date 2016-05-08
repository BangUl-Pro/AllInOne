package com.ironfactory.allinoneenglish.controllers.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ironfactory.allinoneenglish.controllers.fragments.HomeFragment;
import com.ironfactory.allinoneenglish.controllers.fragments.LatelyFragment;
import com.ironfactory.allinoneenglish.controllers.fragments.MineFragment;
import com.ironfactory.allinoneenglish.controllers.fragments.SettingFragment;
import com.ironfactory.allinoneenglish.controllers.fragments.StudyFragment;

/**
 * Created by IronFactory on 2016. 4. 2..
 */
public class TabAdapter extends FragmentPagerAdapter {

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new StudyFragment();

            case 2:
                return new MineFragment();

            case 3:
                return new LatelyFragment();

            default:
                return new SettingFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
