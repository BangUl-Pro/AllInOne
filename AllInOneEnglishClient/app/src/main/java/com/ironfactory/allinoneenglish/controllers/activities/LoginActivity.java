package com.ironfactory.allinoneenglish.controllers.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.PackageReceiver;
import com.ironfactory.allinoneenglish.R;
import com.ironfactory.allinoneenglish.entities.UserEntity;
import com.ironfactory.allinoneenglish.networks.RequestListener;
import com.ironfactory.allinoneenglish.networks.SocketManager;
import com.ironfactory.allinoneenglish.utils.FontUtils;

import io.userhabit.service.Userhabit;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private CheckBox checkBox;

    private PackageReceiver mPackageReceiver = new PackageReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Userhabit.start(this);
        autoLogin();
    }

    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String id = mEmailView.getText().toString();
        final String pw = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(id)) {
            mEmailView.setError("아이디를 입력해주세요");
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(pw)) {
            mPasswordView.setError("비밀번호를 입력해주세요");
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
//            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//
//            if (!wifi.isConnected()) {
//                Intent intent = new Intent(getApplicationContext(), TabActivity.class);
//                startActivity(intent);
//                finish();
//            }
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            SocketManager.login(id, pw, new RequestListener.OnLogin() {
                @Override
                public void onSuccess(UserEntity userEntity) {
                    // 회원 차단 상태
                    if (userEntity.getAccessable() == UserEntity.UNACCESSABLE) {
                        Toast.makeText(getApplicationContext(), "회원 차단 상태입니다", Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences = getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(Global.ACCESS, 0);
                        editor.commit();
                    } else {
                        if (checkBox.isChecked()) {
                            setAutoLogin(id, pw);
                            Log.d(TAG, "자동로그인 허용");
                        } else {
                            setAutoLogin(null, null);
                            Log.d(TAG, "자동로그인 비허용");
                        }

                        SharedPreferences preferences = getSharedPreferences(Global.APP_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(Global.ACCESS, 1);
                        editor.commit();


                        Log.d(TAG, "deviceId = " + userEntity.getDeviceId());
                        Log.d(TAG, "deviceId = " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                        if (userEntity.getDeviceId() == null) {
                            SocketManager.setDeviceId(userEntity.getId(), Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), new RequestListener.OnSetDeviceId() {
                                @Override
                                public void onSuccess() {
                                    Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onException(int code) {
                                }
                            });
                        } else {
                            if (userEntity.getDeviceId().equals(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID))) {
                                Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "본인의 기기에서만 사용해주세요", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onException(int code) {
                    Log.d(TAG, "로그인 실패");
                    if (code == 332) {
                        Toast.makeText(getApplicationContext(), "존재하지 않는 계정입니다.", Toast.LENGTH_SHORT).show();
                        showProgress(false);
                    }
                }
            });
        }
    }

    private void setAutoLogin(String id, String pw) {
        SharedPreferences preferences = getSharedPreferences(Global.APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (id == null && pw == null) {
            editor.clear();
        } else {
            editor.putString(Global.ID, id);
            editor.putString(Global.PW, pw);
        }
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mPackageReceiver);
    }

    private void autoLogin() {
        registerReceiver(mPackageReceiver, new IntentFilter(
                Intent.ACTION_PACKAGE_ADDED));

        SocketManager.createInstance(this);
        final SharedPreferences preferences = getSharedPreferences(Global.APP_NAME, MODE_PRIVATE);
        final String id = preferences.getString(Global.ID, null);
        final String pw = preferences.getString(Global.PW, null);
        if (id != null && pw != null) {
//            checkBox.setChecked(true);
//            checkBox.setBackgroundResource(R.drawable.auto_login);
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!wifi.isConnected()) {
                int access = preferences.getInt(Global.ACCESS, 1);
                Log.d(TAG, "access = " + access);
                if (access == 1) {
                    Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "회원 차단 상태입니다", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            SocketManager.login(id, pw, new RequestListener.OnLogin() {
                @Override
                public void onSuccess(UserEntity userEntity) {
                    // 회원 차단 상태
                    if (userEntity.getAccessable() == UserEntity.UNACCESSABLE) {
                        Toast.makeText(getApplicationContext(), "회원 차단 상태입니다", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(Global.ACCESS, 0);
                        editor.commit();
                    } else {
//                        if (checkBox.isChecked()) {
//                            setAutoLogin(id, pw);
//                            Log.d(TAG, "자동로그인 허용");
//                        } else {
//                            setAutoLogin(null, null);
//                            Log.d(TAG, "자동로그인 차단");
//                        }

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt(Global.ACCESS, 1);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onException(int code) {
                    Log.d(TAG, "로그인 실패");
                }
            });
        } else {
            setContentView(R.layout.activity_login);
            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);
            checkBox = (CheckBox) findViewById(R.id.activity_login_auto);
//            checkBox.setChecked(false);
//            checkBox.setBackgroundResource(R.drawable.auto_login_blank);

            // Set up the login form.
            mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

            mPasswordView = (EditText) findViewById(R.id.password);
            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.login || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });

            FontUtils.setGlobalFont(this, getWindow().getDecorView(), Global.NANUM);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

