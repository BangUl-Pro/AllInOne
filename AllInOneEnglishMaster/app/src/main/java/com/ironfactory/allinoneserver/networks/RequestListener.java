package com.ironfactory.allinoneserver.networks;

import com.ironfactory.allinoneserver.entities.UserEntity;

import java.util.List;

/**
 * Created by IronFactory on 2016. 1. 12..
 */
public class RequestListener {

    public interface OnSignUp {
        void onSuccess();
        void onException(int code);
    }

    public interface OnGetUsers {
        void onSuccess(List<UserEntity> userEntities);
        void onException(int code);
    }

    public interface OnSetAccessable {
        void onSuccess();
        void onException(int code);
    }
}
