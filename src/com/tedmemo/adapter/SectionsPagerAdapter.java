package com.tedmemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.tedmemo.fragment.MainListFragment;
import com.tedmemo.fragment.PlaceholderFragment;
import com.tedmemo.fragment.TagGridFragment;

/**
 * Created by Ted on 14-7-23.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private MainListFragment mMainListFragment = null;
    private TagGridFragment mTagGridFragment = null;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            if(null == mMainListFragment){
                mMainListFragment = MainListFragment.getInstance();
            }
            return mMainListFragment;
        }else {
            if(null == mTagGridFragment){
                mTagGridFragment = TagGridFragment.getInstance();
            }
            return mTagGridFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return position+"";
    }
}
