package com.ironfactory.allinoneenglish.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by IronFactory on 2016. 4. 9..
 */
public class VLCUtils {

    private Context context;
    public static final String VLC_PACKAGE_NAME = "org.videolan.vlc";

    public VLCUtils(Context context) {
        this.context = context;
    }

    public void playVideo(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri videoUri = Uri.parse(filePath);
        intent.setDataAndType(videoUri, "application/x-mpegURL");
        intent.setPackage(VLC_PACKAGE_NAME);
        context.startActivity(intent);
    }

    public void installVlc() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + VLC_PACKAGE_NAME));
        context.startActivity(intent);
    }

    public boolean isInstalledVlc() {
        Intent startLink = context.getPackageManager().getLaunchIntentForPackage(VLC_PACKAGE_NAME);
        if (startLink == null)
            return false;
        return true;
    }
}
