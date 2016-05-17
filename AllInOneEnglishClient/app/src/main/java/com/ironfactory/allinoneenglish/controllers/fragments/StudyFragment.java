package com.ironfactory.allinoneenglish.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.adapters.StudyAdapter;
import com.ironfactory.allinoneenglish.utils.FontUtils;

/**
 * Created by IronFactory on 2016. 4. 6..
 */
public class StudyFragment extends Fragment {

    public static final String TAG = "StudyFragment";
    private RecyclerView recyclerView;
    private StudyAdapter adapter;

    public StudyFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_study_recycler);
        adapter = new StudyAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        FontUtils.setGlobalFont(getActivity(), getActivity().getWindow().getDecorView(), Global.NANUM);
    }
}