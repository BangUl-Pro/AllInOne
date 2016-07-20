package com.ironfactory.allinoneenglish.controllers.fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.utils.VLCUtils;

import java.io.File;

/**
 * Created by IronFactory on 2016. 4. 6..
 */
public class HomeTitleFragment extends Fragment {

    public static final String TAG = "HomeTitleFragment";
    public static final int TYPE_ANDREW = 0;
    public static final int TYPE_ALEX = 1;

    public static String ANDREW_VIDEO = "";
    public static String ALEX_VIDEO = "";

    private ImageView imageView;

    private int type;

    public static HomeTitleFragment createInstance(int type) {
        HomeTitleFragment fragment = new HomeTitleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Global.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HomeTitleFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getInt(Global.TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_title, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        imageView = (ImageView) view.findViewById(R.id.fragment_home_title_image);
        Log.d(TAG, "sd = " + Environment.getExternalStorageDirectory());

        Global.checkSDCardPath();

        ANDREW_VIDEO = Global.searchAllFile(new File(Global.SD_CARD_PATH), "andrew.avi").get(0).getPath();
        ALEX_VIDEO = Global.searchAllFile(new File(Global.SD_CARD_PATH), "alex.avi").get(0).getPath();

        setImage();
        setListener();
    }

    private void setImage() {
        Log.d(TAG, "setImage");
        if (type == TYPE_ALEX) {
            // ALEX 일 때 이미지
            imageView.setBackgroundResource(R.drawable.home_main_alex);
        } else if (type == TYPE_ANDREW) {
            // ANDREW 일 때 이미지
            imageView.setBackgroundResource(R.drawable.home_main_andrew);
        }
    }

    private void setListener() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VLCUtils vlcUtils = new VLCUtils(getActivity());
                vlcUtils.playVideo((type == TYPE_ANDREW ? ANDREW_VIDEO : ALEX_VIDEO));
            }
        });
    }
}