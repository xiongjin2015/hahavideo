package com.haha.video.activity;


import com.umeng.analytics.MobclickAgent;

import android.support.v4.app.FragmentActivity;

public class HaBaseActivity extends FragmentActivity {
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
