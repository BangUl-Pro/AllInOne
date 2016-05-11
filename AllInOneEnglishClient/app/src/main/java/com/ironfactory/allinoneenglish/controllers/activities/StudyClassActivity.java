package com.ironfactory.allinoneenglish.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.adapters.StudyClassAdapter;
import com.ironfactory.allinoneenglish.utils.FontUtils;

import java.io.File;

public class StudyClassActivity extends AppCompatActivity implements StudyClassAdapter.OnPlayVideo {

    private static final String TAG = "StudyClassActivity";
    private RecyclerView recyclerView;
    private StudyClassAdapter adapter;
    private int position;
    private MaterialDialog dialog;
    public static String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_class);

        position = getIntent().getIntExtra(Global.POSITION, 1);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_study_class_recycler);
        adapter = new StudyClassAdapter(this, position);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FontUtils.setGlobalFont(this, getWindow().getDecorView(), Global.NANUM);
    }

    @Override
    public void onPlay() {
        if (dialog == null) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            builder.progress(true, 0);
            builder.cancelable(false);
            builder.title("잠시만 기다려주세요");
            builder.content("버퍼링 중입니다.\n5 ~ 15초 가량 소요될 수있습니다.");
            dialog = builder.build();
        }
        dialog.show();
    }

    @Override
    public void onStopPlay() {
        dialog.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (filePath != null) {
                    File file = new File(filePath);
                    if (file.delete()) {
                        Log.d(TAG, "삭제 성공");
                    } else {
                        Log.d(TAG, "삭제 실패");
                    }
                    filePath = null;
                }
            }
        }).start();
    }
}
