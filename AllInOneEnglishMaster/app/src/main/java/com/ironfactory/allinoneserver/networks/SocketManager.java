package com.ironfactory.allinoneserver.networks;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.ironfactory.allinoneserver.Global;
import com.ironfactory.allinoneserver.entities.UserEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public static void signUp(String id, String pw, final RequestListener.OnSignUp sender) {
        try {
            Log.d(TAG, "회원등록 ");
            checkSocket();
            JSONObject object = new JSONObject();
            object.put(Global.ID, id);
            object.put(Global.PW, pw);
            socket.emit(Global.SIGN_UP, object);
            socket.once(Global.SIGN_UP, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject reqObject = (JSONObject) args[0];
                        final int code = reqObject.getInt(CODE);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (code == SUCCESS) {
                                    Log.d(TAG, "회원등록 성공");
                                    sender.onSuccess();
                                } else {
                                    Log.d(TAG, "회원등록 실패");
                                    sender.onException(code);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void getUsers(final RequestListener.OnGetUsers sender) {
        Log.d(TAG, "회원 리스트 로드 ");
        checkSocket();
        socket.emit(Global.GET_USERS, "");
        socket.once(Global.GET_USERS, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject reqObject = (JSONObject) args[0];
                    final int code = reqObject.getInt(CODE);

                    if (code == SUCCESS) {
                        Log.d(TAG, "회원 리스트 로드 성공");
                        JSONArray array = reqObject.getJSONArray(Global.USERS);
                        final List<UserEntity> userList = new ArrayList<UserEntity>();
                        for (int i = 0; i < array.length(); i++) {
                            userList.add(new UserEntity(array.getJSONObject(i)));
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                sender.onSuccess(userList);
                            }
                        });
                    } else {
                        Log.d(TAG, "회원 리스트 로드 실패");
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
    }

    public static void setAccessable(String id, int accessable, final RequestListener.OnSetAccessable sender) {
        try {
            Log.d(TAG, "해당 회원 접근 권한설정");
            checkSocket();
            JSONObject object = new JSONObject();
            object.put(Global.ID, id);
            object.put(Global.ACCESS_ABLE, accessable);
            socket.emit(Global.SET_ACCESSABLE, object);
            socket.once(Global.SET_ACCESSABLE, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        JSONObject reqObject = (JSONObject) args[0];
                        final int code = reqObject.getInt(CODE);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (code == SUCCESS) {
                                    Log.d(TAG, "해당 회원 접근 권한설정 성공");
                                    sender.onSuccess();
                                } else {
                                    Log.d(TAG, "해당 회원 접근 권한설정 실패");
                                    sender.onException(code);
                                }
                            }
                        });
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
