
package com.haha.video.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.haha.video.R;

public class MainActivity extends HaBaseActionBarActivity {
    
    public static void start(Context ctx) {
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setUiOptionsForSmartBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initActionBar();
    }
    
    private void initView(){
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(false);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(R.drawable.ic_log_default);
    }
    
    private void initActionBar(){
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.main_action_search:
            //SearchActivity.start(this);
            break;
        }
        return true;
    }
}
