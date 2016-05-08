package com.ironfactory.allinoneenglish.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.adapters.HomeTitleAdapter;
import com.ironfactory.allinoneenglish.utils.FontUtils;

/**
 * Created by IronFactory on 2016. 4. 2..
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private ViewPager viewPager;
    private LinearLayout banner1;
    private LinearLayout banner2;

    private HomeTitleAdapter adapter;

    public HomeFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.fragment_home_pager);
        adapter = new HomeTitleAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        banner1 = (LinearLayout) view.findViewById(R.id.fragment_home_banner1);
        banner2 = (LinearLayout) view.findViewById(R.id.fragment_home_banner2);

        FontUtils.setGlobalFont(getActivity(), banner1, Global.HANNA);
        FontUtils.setGlobalFont(getActivity(), banner2, Global.HANNA);

        setListener();
    }

    private void setListener() {
        banner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        banner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
