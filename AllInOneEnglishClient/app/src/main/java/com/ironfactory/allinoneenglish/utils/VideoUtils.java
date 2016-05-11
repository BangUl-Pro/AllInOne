package com.ironfactory.allinoneenglish.utils;

import android.util.Log;

import com.ironfactory.allinoneenglish.Global;

import java.io.File;
import java.util.Date;

/**
 * Created by IronFactory on 2016. 4. 27..
 */
public class VideoUtils {

    private String videoEncryptPath;
    private String videoDecryptPath = null;
    private int position;

    public String getVideoEncryptPath() {
        return videoEncryptPath;
    }

    public void setVideoEncryptPath(String videoEncryptPath) {
        this.videoEncryptPath = videoEncryptPath;
    }

    public String getVideoDecryptPath() {
        return videoDecryptPath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
        videoEncryptPath = Global.files.get(position).getPath();
        Log.d("dd", "videoEn = " + videoEncryptPath);
        StringBuilder name = new StringBuilder(Global.files.get(position).getName());
//        name.insert(0, ".");
        String nameStr = name.toString().replace(".abcde", ".mp4");
        File parentFile = new File(Global.DECRYPT_PATH);
        if (!parentFile.exists())
            parentFile.mkdirs();
        videoDecryptPath = parentFile.getPath() + nameStr;
        Log.d("dd", "videoDe = " + videoDecryptPath);
    }

    public void decryptVideo() {
        Decrypt.decrypt(new File(videoEncryptPath), new File(videoDecryptPath));
    }

    public void updateCource() {
        Global.courses.get(position).setLastStudyDate(new Date(System.currentTimeMillis()));
        Global.dbManager.updateCourse(Global.courses.get(position));
    }
}
