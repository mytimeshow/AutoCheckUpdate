package com.example.autocheckupdate;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import okhttp3.internal.platform.Platform;

/**
 * Created by 夜听海雨 on 2018/7/7.
 */

public class MyApplication extends Application{
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;


    }
    public static Context getContext(){

        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
}
