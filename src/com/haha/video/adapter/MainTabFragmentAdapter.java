
package com.haha.video.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.haha.video.fragment.ChannelFragment;
import com.haha.video.fragment.LocalFragment;
import com.haha.video.fragment.MainAllFragment;
import com.haha.video.fragment.PersonFragment;

/**
 * @author xj Desription:main page slide tab adapter;
 */
public class MainTabFragmentAdapter extends FragmentStatePagerAdapter {

    private String[] mTabTitles;
    private Fragment[] mFragments;

    public MainTabFragmentAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm);
        mTabTitles = tabTitles;
        mFragments = new Fragment[mTabTitles.length];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments[position];
        if (fragment == null) {
            switch (position) {
                case 0:
                     fragment = new MainAllFragment();
                    break;
                case 1:
                     fragment = new ChannelFragment();
                    break;
                case 2:
                     fragment = new LocalFragment();
                    break;
                case 3:
                     fragment = new PersonFragment();
                    break;
                default:
                    break;
            }
            mFragments[position] = fragment;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }

    @Override
    public int getCount() {
        return mTabTitles.length;
    }

}
