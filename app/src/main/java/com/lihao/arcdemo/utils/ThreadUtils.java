package com.lihao.arcdemo.utils;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {

    public static void runOnUI(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(runnable);
        } else {
            runnable.run();
        }
    }
}
