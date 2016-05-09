package com.ironfactory.allinoneenglish.controllers.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ironfactory.allinoneenglish.R;

public class SettingFragment extends Fragment {

    private static final String TAG = "SettingFragment";

    private TextView versionView;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        versionView = (TextView) view.findViewById(R.id.fragment_setting_ver);
        try {
            PackageInfo i = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            versionView.setText(i.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}