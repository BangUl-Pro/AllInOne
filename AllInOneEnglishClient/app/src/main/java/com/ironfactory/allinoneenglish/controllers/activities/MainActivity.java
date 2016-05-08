package com.ironfactory.allinoneenglish.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.adapters.MainAdapter;

/**
 * 동영상 파일 리스트 액티비티
 * */
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
        adapter = new MainAdapter(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
