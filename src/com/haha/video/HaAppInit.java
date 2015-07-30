package com.haha.video;

import android.content.Context;

import com.haha.common.haudid.HaAppType;
import com.haha.common.init.HaInit;
import com.haha.common.logger.Logcat;
import com.haha.common.utils.HaAppInfo;

public final class HaAppInit {
    
    private final static String TAG = "HaAppInit";
    
    private static boolean isInited;
    
    public static void init(Context context){
        if(isInited)
            return;
        
        Logcat.d(TAG, "initialize modules start.");
        long startTM = System.currentTimeMillis();
        
        //initialize the video common modules
        HaInit.init(context, HaAppType.APHONE, HaAppInfo.getVersionName(context));
        
        isInited = true;
        
        long endTM = System.currentTimeMillis();
        Logcat.d(TAG, "initialize modules end. time used: "+(endTM-startTM)+"ms");
    }

}
