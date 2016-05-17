package com.ironfactory.allinoneenglish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ironfactory.allinoneenglish.entities.UserEntity;
import com.ironfactory.allinoneenglish.networks.RequestListener;
import com.ironfactory.allinoneenglish.networks.SocketManager;

public class PackageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO Auto-generated method stub
        String packageName = intent.getData().getSchemeSpecificPart();
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_PACKAGE_REMOVED))
        {
            SocketManager.createInstance(context);
            final SharedPreferences preferences = context.getSharedPreferences(Global.APP_NAME, context.MODE_PRIVATE);
            final String id = preferences.getString(Global.ID, null);
            final String pw = preferences.getString(Global.PW, null);
            if (id != null && pw != null) {
//            checkBox.setChecked(true);
//            checkBox.setBackgroundResource(R.drawable.auto_login);
                SocketManager.login(id, pw, new RequestListener.OnLogin() {
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        SocketManager.setDeviceId(userEntity.getId(), null, new RequestListener.OnSetDeviceId() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onException(int code) {
                            }
                        });
                    }

                    @Override
                    public void onException(int code) {
                    }
                });
            }
        }
    }
}
