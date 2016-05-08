package com.ironfactory.allinoneenglish.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ironfactory.allinoneenglish.entities.CourseEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IronFactory on 2016. 4. 14..
 * TODO : DB 관리 클래스
 */
public class DBManger extends SQLiteOpenHelper {

    private static final String TAG = "DBManger";

    private static final String TABLE_NAME = "CourseTable";

    private static final String COL_LAST_STUDY_DATE = "lastStudyDate";
    private static final String COL_BOOKMARK = "bookmark";
    private static final String COL_INDEX = "_id";

    public DBManger(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBManger(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_INDEX + " INTEGER PRIMARY KEY AUTOINCREMENT , " + COL_LAST_STUDY_DATE + " TEXT, " + COL_BOOKMARK + " INTEGER);";
        db.execSQL(sql);
    }


    public void insertCourse(CourseEntity courseEntity) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(" + courseEntity.getIndex() + ", '" + simpleDateFormat.format(courseEntity.getLastStudyDate()) + "', " + (courseEntity.isBookmark() ? 1 : 0) + ");");
    }

    public void updateCourse(CourseEntity courseEntity) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COL_LAST_STUDY_DATE + " = '" + simpleDateFormat.format(courseEntity.getLastStudyDate()) + "', " + COL_BOOKMARK + " = " + (courseEntity.isBookmark() ? 1 : 0) + " WHERE " + COL_INDEX + " = " + courseEntity.getIndex());
    }

    public CourseEntity getCourse(int index) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_INDEX + " = " + index, null);
        cursor.moveToFirst();
        StringBuilder sb = new StringBuilder(cursor.getString(1));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(sb.substring(0, 4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(sb.substring(5, 7)));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sb.substring(8, 10)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sb.substring(11, 13)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(sb.substring(14, 16)));
        CourseEntity courseEntity = new CourseEntity(cursor.getInt(0), calendar.getTime(), (cursor.getInt(2) == 0 ? false : true));
        return courseEntity;
    }

    public List<CourseEntity> getCourses() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<CourseEntity> courseList = new ArrayList<>();

        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder(cursor.getString(1));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(sb.substring(0, 4)));
            calendar.set(Calendar.MONTH, Integer.parseInt(sb.substring(5, 7)));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sb.substring(8, 10)));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sb.substring(11, 13)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(sb.substring(14, 16)));
            courseList.add(new CourseEntity(cursor.getInt(0), calendar.getTime(), (cursor.getInt(2) == 0 ? false : true)));
        }
        return courseList;
    }

    public List<CourseEntity> getCoursesSortByTime() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_LAST_STUDY_DATE + " DESC LIMIT 10;", null);
        List<CourseEntity> courseList = new ArrayList<>();

        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder(cursor.getString(1));
            Calendar calendar = Calendar.getInstance();
            if (Integer.parseInt(sb.substring(0, 4)) < 2016)
                break;
            calendar.set(Calendar.YEAR, Integer.parseInt(sb.substring(0, 4)));
            calendar.set(Calendar.MONTH, Integer.parseInt(sb.substring(5, 7)));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sb.substring(8, 10)));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sb.substring(11, 13)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(sb.substring(14, 16)));
            courseList.add(new CourseEntity(cursor.getInt(0), calendar.getTime(), (cursor.getInt(2) == 0 ? false : true)));
        }
        return courseList;
    }

    public List<CourseEntity> getBookmarkCourses() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_BOOKMARK + " = 1;", null);
        List<CourseEntity> courseList = new ArrayList<>();

        while (cursor.moveToNext()) {
            StringBuilder sb = new StringBuilder(cursor.getString(1));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(sb.substring(0, 4)));
            calendar.set(Calendar.MONTH, Integer.parseInt(sb.substring(5, 7)));
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sb.substring(8, 10)));
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(sb.substring(11, 13)));
            calendar.set(Calendar.MINUTE, Integer.parseInt(sb.substring(14, 16)));
            courseList.add(new CourseEntity(cursor.getInt(0), calendar.getTime(), (cursor.getInt(2) == 0 ? false : true)));
        }
        return courseList;
    }
}
