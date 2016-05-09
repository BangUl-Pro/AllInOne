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
                webView.loadUrl(Global.QNA_LINK);
                break;

            case Global.QUESTION:
                webView.loadUrl(Global.QUESTION_LINK);
                break;

            case Global.AFTER:
                webView.loadUrl(Global.AFTER_LINK);
                break;
        }
    }
}
