package com.lihao.en_base.utils;

import android.app.Application;

/**
 * 使用反射获取应用程序的全局上下文。
 */
public class EnContext {

    /** 应用程序的全局上下文。 */
    public static final Application mInstance;

    static {
        Application context = null;
        try {
            context = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
        } catch (final Exception e) {
            e.printStackTrace();
            try {
                context = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            } catch (final Exception ex) {
                e.printStackTrace();
            }
        } finally {
            mInstance = context;
        }
    }

    /**
     * 使用反射的方法获取应用程序全局上下文。
     * @return 应用程序全局上下文。
     */
    public static Application get() {
        return mInstance;
    }
}
