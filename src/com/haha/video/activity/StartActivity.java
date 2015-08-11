
package com.haha.video.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haha.common.utils.HaTime;
import com.haha.video.R;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.update.UmengUpdateAgent;

/**
 * @author xj 创建时间：2015年7月21日 下午5:00:36 修改时间：2014年6月6日 下午5:00:36 Description:启动界面
 */
public class StartActivity extends HaBaseActivity {

    private final static int MSG_ID_CLOSE = 1;
    private final static long SHOW_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// remove title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // full screen
        setContentView(R.layout.activity_start);
        initView();
        AnalyticsConfig.setChannel("0002");//TODO:对应渠道id：sid
        //MobclickAgent.updateOnlineConfig(this); //set send data policy
        AnalyticsConfig.enableEncrypt(true);//encrypte log
        UmengUpdateAgent.update(this); //check if version is new
    }

    private void initView() {
        TextView copyrightText = (TextView) findViewById(R.id.start_bottom_copyright);
        copyrightText.setText(getString(R.string.copy_right_info, HaTime.getCurrentYearString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.removeMessages(MSG_ID_CLOSE);
        handler.sendEmptyMessageDelayed(MSG_ID_CLOSE, SHOW_TIME);
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_ID_CLOSE:
                    startNext();
                    break;
                default:
                    break;
            }
        }
    };

    private void startNext() {
        MainActivity.start(this);
        finish();
    }

}
