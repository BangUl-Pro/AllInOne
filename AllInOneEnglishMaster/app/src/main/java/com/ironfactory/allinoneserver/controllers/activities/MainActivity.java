package com.ironfactory.allinoneserver.controllers.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ironfactory.allinoneserver.R;
import com.ironfactory.allinoneserver.controllers.adapter.MainAdapter;
import com.ironfactory.allinoneserver.entities.UserEntity;
import com.ironfactory.allinoneserver.networks.RequestListener;
import com.ironfactory.allinoneserver.networks.SocketManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private List<UserEntity> userEntities;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.activity_main_toolbar));


        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUsers();
    }

    private void init() {
        // 버전 M 이후 로는 권한관리 해줘야함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            }
        }
        SocketManager.createInstance(this);

        recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        searchView = (SearchView) findViewById(R.id.activity_main_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "query = " + query);
                List<UserEntity> userEntities = new ArrayList<UserEntity>();
                for (UserEntity user : MainActivity.this.userEntities) {
                    if (user.getId().contains(query))
                        userEntities.add(user);
                }
                adapter.setUserEntities(userEntities);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "newText = " + newText);
                List<UserEntity> userEntities = new ArrayList<UserEntity>();
                for (UserEntity user : MainActivity.this.userEntities) {
                    if (user.getId().contains(newText))
                        userEntities.add(user);
                }
                adapter.setUserEntities(userEntities);
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "권한 없이 사용 불가능합니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_main) {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_search) {
            searchView.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUsers() {
        SocketManager.getUsers(new RequestListener.OnGetUsers() {
            @Override
            public void onSuccess(List<UserEntity> userEntities) {
                MainActivity.this.userEntities = userEntities;
                adapter.setUserEntities(userEntities);
            }

            @Override
            public void onException(int code) {
                Log.d(TAG, "유저 리스트 로드 실패");
            }
        });
    }
}
