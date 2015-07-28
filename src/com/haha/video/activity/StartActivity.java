
package com.haha.video.activity;

import com.haha.video.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author xj 创建时间：2015年7月21日 下午5:00:36 修改时间：2014年6月6日 下午5:00:36 Description:启动界面
 */
public class StartActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen
        setContentView(R.layout.activity_start);
    }

}
