package com.haha.video.activity;


import com.haha.common.utils.HaAppInfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;

/**
 * 带有ActionBar的Activity的基类；
 * @author xj
 * create time:2015/07/29
 */
public class HaBaseActionBarActivity extends ActionBarActivity {
    
    private static String mHasSmartBar;
    
    public static void show(Context from, Class<? extends HaBaseActionBarActivity> to){
        Intent intent = new Intent(from, to);
        from.startActivity(intent);
    }

    /**
     * This must be done before the decor view has been created.
     * The safest place to put this call to ensure that always happens is 
     * in your activity onCreate method before you call super.onCreate.
     * @author Jake Wharton
     */
    @SuppressLint({ "InlinedApi", "NewApi" })
    protected void setUiOptionsForSmartBar(){
        if(hasSmartBar(this)){
            getWindow().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        }
    }

    @SuppressLint("DefaultLocale")
    private boolean hasSmartBar(Context ctx) {
        if (TextUtils.isEmpty(mHasSmartBar)){
            String hasSmartBar = HaAppInfo.getDeclaredField(this, "HAS_SMARTBAR");
            if (!TextUtils.isEmpty(hasSmartBar)) {
                mHasSmartBar = hasSmartBar.trim().toLowerCase();
            }
        }
        return TextUtils.equals(mHasSmartBar, "true");
    }
}
