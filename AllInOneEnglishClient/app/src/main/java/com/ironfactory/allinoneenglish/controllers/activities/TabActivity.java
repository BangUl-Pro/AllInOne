package com.ironfactory.allinoneenglish.controllers.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.controllers.adapters.TabAdapter;
import com.ironfactory.allinoneenglish.controllers.fragments.LatelyFragment;
import com.ironfactory.allinoneenglish.controllers.fragments.MineFragment;
import com.ironfactory.allinoneenglish.entities.CourseEntity;
import com.ironfactory.allinoneenglish.managers.DBManger;
import com.ironfactory.allinoneenglish.utils.FontUtils;
import com.ironfactory.allinoneenglish.utils.VLCUtils;

import java.io.File;
import java.util.Date;

import io.userhabit.service.Userhabit;

/**
* TODO : 초기화면 메뉴 선택 탭 액티비티
 * http://blog.naver.com/PostView.nhn?blogId=just4u78&logNo=220630233740&parentCategoryNo=&categoryNo=&viewDate=&isShowPopularPosts=false&from=postView
* */
public class TabActivity extends FragmentActivity implements MineFragment.OnPlayVideo, LatelyFragment.OnPlayVideo {

    private static final String TAG = "TabActivity";
    private static final int HOME = 0;
    private static final int STUDY = 1;
    private static final int MINE = 2;
    private static final int LATELY = 3;
    private static final int SET = 4;

    private TabAdapter tabAdapter;

    private ViewPager viewPager;

    private LinearLayout menuLayout[] = new LinearLayout[5];
    private ImageView menuImage[] = new ImageView[5];
    private TextView menuText[] = new TextView[5];

    private VLCUtils vlcUtils;
    private MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        checkPermission();
    }

    /**
     * TODO : 생성자
     * */
    private void init() {
        checkFile();
        vlcUtils = new VLCUtils(this);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.tab_activity_container);
        viewPager.setAdapter(tabAdapter);

        menuLayout[0] = (LinearLayout) findViewById(R.id.activity_tab_home);
        menuImage[0] = (ImageView) findViewById(R.id.activity_tab_home_image);
        menuText[0] = (TextView) findViewById(R.id.activity_tab_home_text);
        menuLayout[1] = (LinearLayout) findViewById(R.id.activity_tab_study);
        menuImage[1] = (ImageView) findViewById(R.id.activity_tab_study_image);
        menuText[1] = (TextView) findViewById(R.id.activity_tab_study_text);
        menuLayout[2] = (LinearLayout) findViewById(R.id.activity_tab_mine);
        menuImage[2] = (ImageView) findViewById(R.id.activity_tab_mine_image);
        menuText[2] = (TextView) findViewById(R.id.activity_tab_mine_text);
        menuLayout[3] = (LinearLayout) findViewById(R.id.activity_tab_lately);
        menuImage[3] = (ImageView) findViewById(R.id.activity_tab_lately_image);
        menuText[3] = (TextView) findViewById(R.id.activity_tab_lately_text);
        menuLayout[4] = (LinearLayout) findViewById(R.id.activity_tab_set);
        menuImage[4] = (ImageView) findViewById(R.id.activity_tab_set_image);
        menuText[4] = (TextView) findViewById(R.id.activity_tab_set_text);

        setListener();
        setBtnLayout(HOME);
        Log.d(TAG, "dd");
        viewPager.setOffscreenPageLimit(5);

        if (!vlcUtils.isInstalledVlc()) {
            vlcUtils.installVlc();
        }

        FontUtils.setGlobalFont(this, getWindow().getDecorView(), Global.NANUM);
        Userhabit.setViewPager(viewPager);
    }

    private void checkFile() {
        Log.d(TAG, "sdcard = " + Global.SD_CARD_PATH);

        Global.checkSDCardPath();

        Global.searchAllFile(new File(Global.SD_CARD_PATH), ".abcde");
        Global.sortFile();
        checkDB();
    }

    private void setListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBtnLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0; i < menuLayout.length; i++) {
            final int I = i;
            menuLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setBtnLayout(I);
                    viewPager.setCurrentItem(I);
                }
            });
        }
    }

    private void setBtnLayout(int btn) {
        setBtnImage(btn);
        setBtnText(btn);
        setBtnParams(btn);
        setBtnPadding(btn);

        for (int i = 0; i < menuLayout.length; i++) {
            if (i == btn) {
                menuLayout[i].setBackgroundResource(R.drawable.menu_selected);
            } else {
                menuLayout[i].setBackgroundColor(getResources().getColor(R.color.menu_not_selected));
            }
        }
    }

    private void setBtnPadding(int btn) {
        for (int i = 0; i < menuLayout.length; i++) {
            if (i == btn) {
                int padding = getResources().getDimensionPixelSize(R.dimen.btn_menu_margin);
                menuLayout[i].setPadding(0, 0, padding, 0);
            } else {
                menuLayout[i].setPadding(0, 0, 0, 0);
            }
        }
    }

    private void setBtnParams(int btn) {
        for (int i = 0; i < menuLayout.length; i++) {
            if (i == btn) {
                menuLayout[i].setLayoutParams(getSelectedBtnParams());
            } else {
                menuLayout[i].setLayoutParams(getNotSelectedBtnParams());
            }
        }
    }

    private void setBtnImage(int btn) {
        if (btn == HOME) {
            menuImage[0].setImageResource(R.drawable.ic_home_selected);
            menuImage[1].setImageResource(R.drawable.ic_study_not_selected);
            menuImage[2].setImageResource(R.drawable.ic_mine_not_selected);
            menuImage[3].setImageResource(R.drawable.ic_lately_not_selected);
            menuImage[4].setImageResource(R.drawable.ic_set_not_selected);
        } else if (btn == STUDY) {
            menuImage[0].setImageResource(R.drawable.ic_home_not_selected);
            menuImage[1].setImageResource(R.drawable.ic_study_selected);
            menuImage[2].setImageResource(R.drawable.ic_mine_not_selected);
            menuImage[3].setImageResource(R.drawable.ic_lately_not_selected);
            menuImage[4].setImageResource(R.drawable.ic_set_not_selected);
        } else if (btn == MINE) {
            menuImage[0].setImageResource(R.drawable.ic_home_not_selected);
            menuImage[1].setImageResource(R.drawable.ic_study_not_selected);
            menuImage[2].setImageResource(R.drawable.ic_mine_selected);
            menuImage[3].setImageResource(R.drawable.ic_lately_not_selected);
            menuImage[4].setImageResource(R.drawable.ic_set_not_selected);
        } else if (btn == LATELY) {
            menuImage[0].setImageResource(R.drawable.ic_home_not_selected);
            menuImage[1].setImageResource(R.drawable.ic_study_not_selected);
            menuImage[2].setImageResource(R.drawable.ic_mine_not_selected);
            menuImage[3].setImageResource(R.drawable.ic_lately_selected);
            menuImage[4].setImageResource(R.drawable.ic_set_not_selected);
        } else if (btn == SET) {
            menuImage[0].setImageResource(R.drawable.ic_home_not_selected);
            menuImage[1].setImageResource(R.drawable.ic_study_not_selected);
            menuImage[2].setImageResource(R.drawable.ic_mine_not_selected);
            menuImage[3].setImageResource(R.drawable.ic_lately_not_selected);
            menuImage[4].setImageResource(R.drawable.ic_set_selected);
        }
    }

    private void setBtnText(int btn) {
        for (int i = 0; i < menuLayout.length; i++) {
            if (i == btn) {
                menuText[i].setTextColor(Color.WHITE);
            } else {
                menuText[i].setTextColor(Color.BLACK);
            }
        }
    }

    private LinearLayout.LayoutParams getSelectedBtnParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        params.bottomMargin = 10;
        return params;
    }

    private LinearLayout.LayoutParams getNotSelectedBtnParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0
        );
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        params.bottomMargin = 10;
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);
        return params;
    }

    private void checkDB() {
        Global.dbManager = new DBManger(getApplicationContext(), Global.APP_NAME, null, 1);
        if (Global.dbManager.getCourses().size() == 0) {
            for (int i = 0; i < Global.files.size(); i++) {
                Global.dbManager.insertCourse(new CourseEntity(i, new Date(0), false));
            }
        }
        Global.courses = Global.dbManager.getCourses();
    }

    @Override
    public void onPlay() {
        if (dialog == null) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(this);
            builder.progress(true, 0);
            builder.cancelable(false);
            builder.title("잠시만 기다려주세요");
            builder.content("암호화 해제 중입니다.\n5 ~ 15초 가량 소요될 수 있습니다.");
            dialog = builder.build();
        }
        dialog.show();
    }

    @Override
    public void onStopPlay() {
        dialog.cancel();
    }



    final int MY_PERMISSION_REQUEST_STORAGE = 300;

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to write the permission.
                    Toast.makeText(this, "Read/Write external storage", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_STORAGE);

                // MY_PERMISSION_REQUEST_STORAGE is an
                // app-defined int constant
            } else {
                Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG).show();
                init();
            }
        } else {
            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
            init();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG).show();
                    init();

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    Log.d(TAG, "Permission always deny");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
}
