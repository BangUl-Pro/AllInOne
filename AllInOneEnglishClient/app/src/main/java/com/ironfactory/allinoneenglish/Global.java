package com.ironfactory.allinoneenglish;

import android.os.Environment;

import com.ironfactory.allinoneenglish.entities.CourseEntity;
import com.ironfactory.allinoneenglish.managers.DBManger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IronFactory on 2016. 3. 22..
 */
public class Global {

    public static final String AES_KEY = "01033677355010421073550108986735";
    public static final String PATH = "path";

    public static final String HANNA = "BMHANNA.otf";
    public static final String JUA = "BMJUA.otf";
    public static final String NANUM = "NanumBarunGothic.otf";
    public static final String APP_NAME = "AllInOne";

    public static final String TYPE = "type";
    public static final String POSITION = "position";

    public static final String ID = "id";
    public static final String PW = "pw";
    public static final String USER = "user";

    public static final String LOGIN = "login";
    public static final String QNA_LINK = "http://www.allinoneenglish.com/qna";
    public static final String QUESTION_LINK = "http://www.allinoneenglish.com/index.php?mid=classqna&act=dispNproductCommentList";
    public static final String AFTER_LINK = "http://www.allinoneenglish.com/index.php?mid=review&act=dispNproductReviewList";

    public static final String SD_CARD_PATH = System.getenv("SECONDARY_STORAGE") + "/AllInOne";
    public static final String DECRYPT_PATH = Environment.getExternalStorageDirectory().getPath() + "/AllInOne";


    public static final int QNA = 1;
    public static final int QUESTION = 2;
    public static final int CUSTOMER = 3;
    public static final int AFTER = 4;

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
}
