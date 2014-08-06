package com.tedmemo.activity;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.TedFramework.util.DeviceUtil;
import com.android.TedFramework.util.LogUtil;
import com.tedmemo.adapter.SectionsPagerAdapter;
import com.tedmemo.others.ShakeListener;
import com.tedmemo.view.R;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends FragmentActivity implements View.OnClickListener,ShakeListener.OnShakeListener{
    private int SELECT_HOME;
    private int SELECT_FOLDER;
    private int WINDOW_WIDTH = 0;
    private int mIconBgMarginLeft = SELECT_HOME;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageView mIconBg;
    private ImageView mTabHome;
    private ImageView mTabTag;
    private ViewGroup.MarginLayoutParams mIconBgMarPars;
    private float mPageMovePercent = 0;
    private ShakeListener mShakeListener;
    private AtomicBoolean mShakeCallOver;
    private Vibrator mShakeVibrator;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tabHome:
                mViewPager.setCurrentItem(0,true);
                break;
            case R.id.tabTag:
                mViewPager.setCurrentItem(1,true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Crittercism.initialize(getApplicationContext(), "53d85fa4bb94753b60000005");
        //Crittercism.setUsername("xiongwei");
        setContentView(R.layout.activity_main);
        initShake();
        initView();
        initData();
        setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShakeListener.stop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onShake() {
        if(mShakeCallOver.compareAndSet(true,false)){
            mShakeListener.stop();
            long [] pattern = {100,400};
            mShakeVibrator.vibrate(pattern,-1);
            Toast.makeText(getApplicationContext(),"晃动手机了",Toast.LENGTH_SHORT).show();
            LogUtil.e("晃动手机了");
            mShakeCallOver.set(true);
            mShakeListener.start();
        }
    }
    void initShake(){
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(this);
        mShakeCallOver = new AtomicBoolean(true);
        mShakeVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void initData(){
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        SELECT_FOLDER = DeviceUtil.getPixelFromDip(dm, 125*3/4-22);
        SELECT_HOME = DeviceUtil.getPixelFromDip(dm,125/4-22);
        WINDOW_WIDTH = DeviceUtil.getScreenSize(dm)[0];
        mIconBgMarginLeft = SELECT_HOME;
        mIconBgMarPars = (ViewGroup.MarginLayoutParams)mIconBg.getLayoutParams();
    }


    private void initView(){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mIconBg = (ImageView) findViewById(R.id.tabIconBackground);
        mTabHome = (ImageView) findViewById(R.id.tabHome);
        mTabTag = (ImageView) findViewById(R.id.tabTag);

    }

    private void setData(){
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(myOnPageChangeListener);
        mTabHome.setOnClickListener(this);
        mTabTag.setOnClickListener(this);
        setIconFilter();
        setIconBgPosition();
    }

    private void setIconBgPosition(){
        mIconBgMarPars.leftMargin = mIconBgMarginLeft;
        mIconBg.setLayoutParams(mIconBgMarPars);
    }

    private void setIconFilter(){
        mTabTag.setColorFilter(Color.rgb((int)(226-97*mPageMovePercent), (int)(243-29*mPageMovePercent), (int)(242-35*mPageMovePercent)));
        mTabHome.setColorFilter(Color.rgb((int)(129+97*mPageMovePercent), (int)(214+29*mPageMovePercent), (int)(207+35*mPageMovePercent)));
    }

    private ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i2) {
            if(v==0.0){
                if(i==0){
                    mIconBgMarginLeft = SELECT_HOME;
                }else if(i == 1){
                    mIconBgMarginLeft = SELECT_FOLDER;
                }
                mPageMovePercent = i;
            }else {
                mPageMovePercent = (float)i2/(float)WINDOW_WIDTH;
                mIconBgMarginLeft = (int)(SELECT_HOME+(SELECT_FOLDER-SELECT_HOME)*mPageMovePercent);
            }
            setIconBgPosition();
            setIconFilter();
        }

        @Override
        public void onPageSelected(int i) {
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };
}
