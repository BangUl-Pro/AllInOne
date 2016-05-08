package com.ironfactory.allinoneenglish.controllers.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.adapters.LatelyAdapter;
import com.ironfactory.allinoneenglish.entities.CourseEntity;
import com.ironfactory.allinoneenglish.utils.FontUtils;

import java.util.List;

public class LatelyFragment extends Fragment {

    private static final String TAG = "LatelyFragment";
    private RecyclerView recyclerView;
    private LatelyAdapter adapter;

    private OnPlayVideo onPlayVideo;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        onPlayVideo = (OnPlayVideo) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lately, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_lately);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FontUtils.setGlobalFont(getActivity(), getActivity().getWindow().getDecorView(), Global.NANUM);
    }

    @Override
    public void onResume() {
        super.onResume();

        List<CourseEntity> courseEntities = Global.dbManager.getCoursesSortByTime();
        adapter = new LatelyAdapter(getActivity(), courseEntities, onPlayVideo);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "Dd");
    }

    public interface OnPlayVideo {
        void onPlay();
        void onStopPlay();
    }
}
