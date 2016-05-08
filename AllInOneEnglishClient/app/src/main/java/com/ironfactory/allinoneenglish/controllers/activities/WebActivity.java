package com.ironfactory.allinoneenglish.controllers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.R;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";

    private WebView webView;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        type = getIntent().getIntExtra(Global.TYPE, -1);
        webView = (WebView) findViewById(R.id.activity_web_view);
        switch (type) {
            case Global.QNA:

                break;

            case Global.QUESTION:

                break;

            case Global.CUSTOMER:

                break;

            case Global.AFTER:

                break;
        }
    }
}
