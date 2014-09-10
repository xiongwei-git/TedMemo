package com.tedmemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.TedFramework.util.DeviceUtil;
import com.android.tedwidget.view.HoldableViewPager;
import com.tedmemo.adapter.SectionsPagerAdapter;
import com.tedmemo.dialog.CustomerFragmentCallBack;
import com.tedmemo.fragment.FragmentExchangeController;
import com.tedmemo.fragment.WriteMemoFragment;
import com.tedmemo.service.WatchingService;
import com.tedmemo.view.R;


public class MainActivity extends FragmentActivity implements View.OnClickListener,CustomerFragmentCallBack{
    private int SELECT_HOME;
    private int SELECT_FOLDER;
    private int WINDOW_WIDTH = 0;
    private int mIconBgMarginLeft = SELECT_HOME;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private HoldableViewPager mViewPager;
    private ImageView mIconBg;
    private ImageView mTabHome;
    private ImageView mTabTag;
    private ImageButton mWriteBtn;
    private ViewGroup.MarginLayoutParams mIconBgMarPars;
    private float mPageMovePercent = 0;
    private View mCustomDialogView;
    /**编辑icon时候的顶部视图*/
    private RelativeLayout mIconEditHeader;
    /**正常状态的顶部视图*/
    private RelativeLayout mTopHeader;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tabHome:
                mViewPager.setCurrentItem(0,true);
                break;
            case R.id.tabTag:
                mViewPager.setCurrentItem(1,true);
                break;
            case R.id.writeButton:
                writeNewMemo();
                break;
            case R.id.iconEditCancelButton:
                setEditHeader(View.GONE);
                break;
            default:
                break;
        }
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

    @Override
    public View getCustomerView(String mTag) {
        return mCustomDialogView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crittercism.initialize(getApplicationContext(), "53d85fa4bb94753b60000005");
        //Crittercism.setUsername("xiongwei");
        setContentView(R.layout.activity_main);
        initService();
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
        //IconDataManager.getInstance(this).clostIconDB();
        stopService(new Intent(this, WatchingService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            /**当前处在编辑icon模式*/
            if(mIconEditHeader.isShown()){
                setEditHeader(View.GONE);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**********Public***************/
    public void setEditHeader(int visibility){
        mIconEditHeader.setVisibility(visibility);
        if(visibility == View.VISIBLE){
            mViewPager.setSwipeHold(true);
            mTopHeader.setVisibility(View.GONE);
            mIconEditHeader.findViewById(R.id.iconEditCancelButton).setOnClickListener(this);
        }else {
            mViewPager.setSwipeHold(false);
            mTopHeader.setVisibility(View.VISIBLE);
            mSectionsPagerAdapter.getTagGridFragment().closeEditIconMode();
        }
    }


    void initService(){
        startService(new Intent(this, WatchingService.class));
    }

    private void initData(){
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        SELECT_FOLDER = DeviceUtil.getPixelFromDip(dm, 125*3/4-22);
        SELECT_HOME = DeviceUtil.getPixelFromDip(dm,125/4-22);
        WINDOW_WIDTH = DeviceUtil.getScreenSize()[0];
        mIconBgMarginLeft = SELECT_HOME;
        mIconBgMarPars = (ViewGroup.MarginLayoutParams)mIconBg.getLayoutParams();
    }


    private void initView(){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mTopHeader = (RelativeLayout)findViewById(R.id.topHeader);
        mIconEditHeader = (RelativeLayout)findViewById(R.id.iconEditHeader);
        mViewPager = (HoldableViewPager) findViewById(R.id.topPager);
        mIconBg = (ImageView) findViewById(R.id.tabIconBackground);
        mTabHome = (ImageView) findViewById(R.id.tabHome);
        mTabTag = (ImageView) findViewById(R.id.tabTag);
        mWriteBtn = (ImageButton)findViewById(R.id.writeButton);
    }

    private void setData(){
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(myOnPageChangeListener);
        mTabHome.setOnClickListener(this);
        mTabTag.setOnClickListener(this);
        mWriteBtn.setOnClickListener(this);
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

    private void writeNewMemo(){
        WriteMemoFragment writeMemoFragment = WriteMemoFragment.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_fragment_in, R.anim.anim_fragment_out, R.anim.anim_fragment_close_in, R.anim.anim_fragment_close_out);
        transaction.add(android.R.id.content, writeMemoFragment, "WRITE_MEMO");
        transaction.addToBackStack("WRITE_MEMO");
        transaction.commitAllowingStateLoss();
        //FragmentExchangeController.initFragment(getSupportFragmentManager(),writeMemoFragment,"WRITE_MEMO");
    }



    /****set+get method****/
    public void setmCustomDialogView(View mCustomDialogView) {
        this.mCustomDialogView = mCustomDialogView;
    }
}
