package com.salthai.a17376042_khb_finaltest;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
