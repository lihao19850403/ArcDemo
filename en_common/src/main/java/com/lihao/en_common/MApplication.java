package com.lihao.en_common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class MApplication extends Application {

    private static MApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ARouter.init(this);
    }

    public static MApplication getInstance() {
        return mInstance;
    }
}
