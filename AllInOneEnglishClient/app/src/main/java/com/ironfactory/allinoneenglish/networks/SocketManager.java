package com.ironfactory.allinoneenglish.networks;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.ironfactory.allinoneenglish.Global;
import com.ironfactory.allinoneenglish.entities.UserEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ironFactory on 2015-08-03.
 */
public class SocketManager {

    private static Handler handler = new Handler();
    private static final String SERVER_URL = "http://allinone-server.herokuapp.com";
    private static final String TAG = "SocketManager";

    public static final String CODE = "code";
    public static final String SIGN_UP = "signUp";
    public static final String LOGIN = "login";


    public static final int SUCCESS = 200;


    public static Socket socket;
    private static Context context;

    public SocketManager(Context context) {
        this.context = context;
        init();
    }

    public static void createInstance(Context context) {
        SocketManager.context = context;
        init();
    }

    private static void init() {
        Log.d(TAG, "init");
        try {
            socket = IO.socket(SERVER_URL);
        } catch (Exception e) {
            Log.e(TAG, "init 에러 = " + e.getMessage());
        }

        if (socket != null) {
            socketConnect();
        }
    }

    private static void checkSocket() {
        if (socket == null) {
            init();
        }
    }

    public static Socket getSocket() {
        return socket;
    }


    private static void socketConnect() {
        socket.open();
        socket.connect();
    }

    public static void login(String id, String pw, final RequestListener.OnLogin sender) {
        try {
            checkSocket();
            Log.d(TAG, "로그인 ");
            JSONObject object = new JSONObject();
            object.put(Global.ID, id);
            object.put(Global.PW, pw);
            socket.emit(Global.LOGIN, object);
            socket.once(Global.LOGIN, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        final JSONObject reqObject = (JSONObject) args[0];
                        final int code = reqObject.getInt(CODE);

                        if (code == SUCCESS) {
                            Log.d(TAG, "로그인 성공");
                            JSONArray array = reqObject.getJSONArray(Global.USER);
                            final UserEntity userEntity = new UserEntity(array.getJSONObject(0));
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sender.onSuccess(userEntity);
                                }
                            });
                        } else {
                            Log.d(TAG, "로그인 실패");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sender.onException(code);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setDeviceId(String id, String pw, final RequestListener.OnSetDeviceId sender) {
        try {
            checkSocket();
            Log.d(TAG, "디바이스 아이디 설정 ");
            JSONObject object = new JSONObject();
            object.put(Global.ID, id);
            object.put(Global.DEVICE_ID, pw);
            socket.emit(Global.SET_DEVICE_ID, object);
            socket.once(Global.SET_DEVICE_ID, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        final JSONObject reqObject = (JSONObject) args[0];
                        final int code = reqObject.getInt(CODE);

                        if (code == SUCCESS) {
                            Log.d(TAG, "디바이스 아이디 설정 성공");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sender.onSuccess();
                                }
                            });
                        } else {
                            Log.d(TAG, "디바이스 아이디 설정 실패");
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    sender.onException(code);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
