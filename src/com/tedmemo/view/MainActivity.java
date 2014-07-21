package com.tedmemo.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tedmemo.Util.DeviceUtil;


public class MainActivity extends FragmentActivity {
    private int SELECT_HOME;
    private int SELECT_FOLDER;

    private int mIconBgMarginLeft = SELECT_HOME;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    ImageView mIconBg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mIconBg = (ImageView) findViewById(R.id.tabIconBackground);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        initData();
        setIconBgPosition();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    private void initData(){
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        SELECT_FOLDER = DeviceUtil.getPixelFromDip(dm, 125*3/4-22);
        SELECT_HOME = DeviceUtil.getPixelFromDip(dm,125/4-22);
        mIconBgMarginLeft = SELECT_HOME;
    }
    private void setIconBgPosition(){
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)mIconBg.getLayoutParams();
        marginLayoutParams.leftMargin = mIconBgMarginLeft;
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return position+"";
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }
}
