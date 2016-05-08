package com.ironfactory.allinoneenglish.controllers.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.activities.WebActivity;
import com.ironfactory.allinoneenglish.controllers.adapters.StudyAdapter;

/**
 * Created by IronFactory on 2016. 4. 6..
 */
public class StudyFragment extends Fragment {

    public static final String TAG = "StudyFragment";
    private RecyclerView recyclerView;
    private StudyAdapter adapter;

    private Button qnaBtn;
    private Button questionBtn;
    private Button customerBtn;
    private Button afterBtn;

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

        qnaBtn = (Button) view.findViewById(R.id.fragment_study_qna);
        questionBtn = (Button) view.findViewById(R.id.fragment_study_question);
        customerBtn = (Button) view.findViewById(R.id.fragment_study_customer);
        afterBtn = (Button) view.findViewById(R.id.fragment_study_after);

        setListener();
    }


    private void setListener() {
        qnaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(Global.TYPE, Global.QNA);
                getActivity().startActivity(intent);
            }
        });

        questionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(Global.TYPE, Global.QUESTION);
                getActivity().startActivity(intent);
            }
        });

        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(Global.TYPE, Global.CUSTOMER);
                getActivity().startActivity(intent);
            }
        });

        afterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(Global.TYPE, Global.AFTER);
                getActivity().startActivity(intent);
            }
        });
    }
}