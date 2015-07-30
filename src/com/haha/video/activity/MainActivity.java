
package com.haha.video.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.haha.video.R;
import com.haha.video.adapter.MainTabFragmentAdapter;
import com.haha.video.widget.TabPageIndicator;

public class MainActivity extends HaBaseActionBarActivity {

    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;
    private String[] mTabTitles;
    private MainTabFragmentAdapter mAdapter;

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
        mTabTitles = getResources().getStringArray(R.array.main_tab);

        mAdapter = new MainTabFragmentAdapter(getSupportFragmentManager(), mTabTitles);
        mViewPager.setAdapter(mAdapter);

        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
    }

    private void initView() {
        mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
    }

    private void initActionBar() {
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(false);
        bar.setDisplayHomeAsUpEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setIcon(R.drawable.ic_log_default);
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
                // SearchActivity.start(this);
                break;
        }
        return true;
    }
}
