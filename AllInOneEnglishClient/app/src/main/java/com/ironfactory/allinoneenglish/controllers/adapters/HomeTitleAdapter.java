package com.ironfactory.allinoneenglish.controllers.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ironfactory.allinoneenglish.controllers.fragments.HomeTitleFragment;

/**
 * Created by IronFactory on 2016. 4. 6..
 */
public class HomeTitleAdapter extends FragmentPagerAdapter {

    private static final String TAG = "HomeTitleAdapter";

    public HomeTitleAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return HomeTitleFragment.createInstance(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
