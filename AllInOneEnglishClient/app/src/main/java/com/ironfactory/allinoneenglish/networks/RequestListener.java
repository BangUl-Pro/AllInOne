package com.ironfactory.allinoneenglish.networks;

import com.ironfactory.allinoneenglish.entities.UserEntity;

/**
 * Created by IronFactory on 2016. 1. 12..
 */
public class RequestListener {

    public interface OnLogin {
        void onSuccess(UserEntity userEntity);
        void onException(int code);
    }
}
