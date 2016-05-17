package com.ironfactory.allinoneenglish.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IronFactory on 2016. 4. 25..
 */
public class UserEntity {

    private static final String PROPERTY_USER_ID = "user_id";
    private static final String PROPERTY_USER_PW = "user_pw";
    private static final String PROPERTY_DEVICE_ID = "user_device_id";
    private static final String PROPERTY_USER_ACCESSABLE = "user_accessable";

    public static final int UNACCESSABLE = 1;
    public static final int ACCESSABLE = 2;

    private String id;
    private String deviceId;
    private String pw;
    private int accessable;

    public UserEntity(JSONObject object) {
        try {
            if (!object.get(PROPERTY_USER_ID).equals(null))
                id = object.getString(PROPERTY_USER_ID);
            if (!object.get(PROPERTY_USER_PW).equals(null))
                pw = object.getString(PROPERTY_USER_PW);
            if (!object.get(PROPERTY_DEVICE_ID).equals(null))
                deviceId = object.getString(PROPERTY_DEVICE_ID);
            if (!object.get(PROPERTY_USER_ACCESSABLE).equals(null))
                accessable = object.getInt(PROPERTY_USER_ACCESSABLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserEntity(String id, String pw, int accessable) {
        this.id = id;
        this.pw = pw;
        this.accessable = accessable;
    }

    public int getAccessable() {
        return accessable;
    }

    public void setAccessable(int accessable) {
        this.accessable = accessable;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setId(String id) {
        this.id = id;
    }
}
