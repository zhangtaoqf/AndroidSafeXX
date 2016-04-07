package com.zt.jiamishouji;

import android.app.Application;

/**
 * Created by Administrator on 2016/3/2 0002.
 */
public class MyApp extends Application {
    private static MyApp app;
    private boolean isOn;

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isOn() {
        return isOn;
    }

    public static MyApp getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
    }
}
