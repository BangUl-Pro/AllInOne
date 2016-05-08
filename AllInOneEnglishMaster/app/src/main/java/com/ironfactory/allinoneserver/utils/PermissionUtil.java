package com.ironfactory.allinoneserver.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by IronFactory on 2016. 4. 25..
 */
public class PermissionUtil {

    private static final String TAG = "PermissionUtil";

    private Activity activity;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermission(String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
}
