package com.lihao.arcdemo;

import android.app.Application;

public class MApplication extends Application {

    private static MApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static MApplication getInstance() {
        return mInstance;
    }
}
