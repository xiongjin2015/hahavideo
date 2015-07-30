package com.haha.video;

import android.app.Application;

import com.haha.common.logger.Logcat;

public class HaApplication extends Application {
    
    private final static String TAG = "HaApplication";
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        long startTM = System.currentTimeMillis();
        
        
        long endTM = System.currentTimeMillis();
        Logcat.d(TAG, "Application startup success, time used: "+String.valueOf(endTM-startTM)+"ms");
    }

}
