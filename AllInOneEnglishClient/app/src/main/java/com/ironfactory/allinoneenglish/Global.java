package com.ironfactory.allinoneenglish;

import android.os.Environment;
import android.util.Log;

import com.ironfactory.allinoneenglish.entities.CourseEntity;
import com.ironfactory.allinoneenglish.managers.DBManger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IronFactory on 2016. 3. 22..
 */
public class Global {

    public static final String TAG = "Global";
    public static final String PATH = "path";

    public static final String HANNA = "BMHANNA.otf";
    public static final String JUA = "BMJUA.otf";
    public static final String NANUM = "NanumBarunGothic.otf";
    public static final String APP_NAME = "AllInOne";

    public static final String TYPE = "type";
    public static final String POSITION = "position";

    public static final String ID = "id";
    public static final String DEVICE_ID = "deviceId";
    public static final String PW = "pw";
    public static final String USER = "user";

    public static final String LOGIN = "login";
    public static final String SET_DEVICE_ID = "setDeviceId";
    public static final String QNA_LINK = "http://www.allinoneenglish.com/qna";
    public static final String QUESTION_LINK = "http://www.allinoneenglish.com/index.php?mid=classqna&act=dispNproductCommentList";
    public static final String AFTER_LINK = "http://www.allinoneenglish.com/index.php?mid=review&act=dispNproductReviewList";
    public static final String ACCESS = "access";

    public static String SD_CARD_PATH = null;
    public static final String DECRYPT_PATH = Environment.getExternalStorageDirectory().getPath() + "/AllInOne";

    public static final int QNA = 1;
    public static final int QUESTION = 2;
    public static final int AFTER = 3;
    public static final int TAB_10_1 = 4;
    public static final int TAB_8_0 = 5;

    public static DBManger dbManager;
    public static List<File> files = new ArrayList<>();
    public static List<CourseEntity> courses = new ArrayList<>();

    public static List<File> searchAllFile(File fileList, String endStr){
        List<File> curFileList = new ArrayList<>();
        File[] list = fileList.listFiles();

        if(list==null)
            return null;
        for(File file : list){
            if (file.isDirectory()) {
                List<File> curFiles = searchAllFile(file, endStr);
                if (curFiles != null && curFiles.size() > 0) {
                    curFileList.addAll(curFiles);
                }
            }

            if(file.getName().endsWith(endStr)) {
                curFileList.add(file);
//                Log.d(",,", "filename = " + file.getName());
            }
        }
        if (endStr.equals(".abcde"))
            files = curFileList;
        return curFileList;
    }

    public static void sortFile() {
        for (int i = 0; i < files.size(); i++) {
            for (int j = 1; j < files.size() - i; j++) {
                StringBuilder sb1 = new StringBuilder(files.get(j).getName());
                StringBuilder sb2 = new StringBuilder(files.get(j - 1).getName());

//                System.out.println("sb1 = " + sb1.toString());

                int num1 = 0, num2 = 0, count = 1;
                char temp;
                for (int k = 1; sb1.length() > k && ((temp = sb1.charAt(k)) >= '0' && temp <= '9'); k++) {
                    num1 *= 10;
                    num1 += temp - '0';
                }

//                System.out.println("sb2 = " + sb2.toString());
                for (int k = 1; sb2.length() > k && ((temp = sb2.charAt(k)) >= '0' && temp <= '9'); k++) {
                    num2 *= 10;
                    num2 += temp - '0';
                }

                if (num1 < num2) {
                    File file = files.get(j - 1);
                    files.set(j - 1, files.get(j));
                    files.set(j, file);
                }
            }
        }
    }

    public static void checkSDCardPath() {
        if (Global.SD_CARD_PATH == null) {
            File[] files = new File("/storage/").listFiles();
            for (int i = 0; i < files.length; i++) {
                if (!files[i].getName().equals("emulated")) {
                    File file = new File(files[i].getPath() + "/");
                    Log.d(TAG, "file = " + file.getPath());
                    File[] tempFiles = file.listFiles();
                    if (tempFiles != null) {
                        for (int j = 0; j < tempFiles.length; j++) {
                            if (tempFiles[j].getName().equals("AllInOne")) {
                                Global.SD_CARD_PATH = tempFiles[j].getPath();
                            }
                        }
                    }
                }
            }
        }
    }
}
