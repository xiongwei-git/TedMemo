package com.tedmemo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.android.TedFramework.util.DeviceUtil;
import com.tedmemo.adapter.SectionsPagerAdapter;
import com.tedmemo.db.IconBgData;
import com.tedmemo.dialog.CustomerFragmentCallBack;
import com.tedmemo.dialog.DialogExchangeModel;
import com.tedmemo.dialog.DialogType;
import com.tedmemo.dialog.TDialogManager;
import com.tedmemo.others.ShakeListener;
import com.tedmemo.view.MemoIconChooseView;
import com.tedmemo.view.R;
import com.tedmemo.service.WatchingService;

import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends FragmentActivity implements View.OnClickListener,CustomerFragmentCallBack{
    private int SELECT_HOME;
    private int SELECT_FOLDER;
    private int WINDOW_WIDTH = 0;
    private int mIconBgMarginLeft = SELECT_HOME;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ImageView mIconBg;
    private ImageView mTabHome;
    private ImageView mTabTag;
    private ImageButton mWriteBtn;
    private ViewGroup.MarginLayoutParams mIconBgMarPars;
    private float mPageMovePercent = 0;
    private View mCustomDialogView;

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
            default:
                break;
        }
    }

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
        stopService(new Intent(this, WatchingService.class));
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        mViewPager = (ViewPager) findViewById(R.id.topPager);
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
//        Dialog dialog = new Dialog(this,R.style.ChooseIconDialog);
//        dialog.setContentView(R.layout.edit_icon_dialog_view);
//        dialog.show();
        IconBgData mIconBgData = new IconBgData();
        mIconBgData.set_mName("c_funny");
        mIconBgData.setBackgroundColorOnStr("#f85d00");
        mIconBgData.setBackgroundColorOffStr("#0D9151");
        MemoIconChooseView chooseView = new MemoIconChooseView(this,mIconBgData);
        chooseView.setmSelecetIconCallBack(mSelecetIconCallBack);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        int marginTop = getResources().getDimensionPixelSize(R.dimen.action_bar_height)+DeviceUtil.getPixelFromDip(this,15.0f);
        int marginSide = DeviceUtil.getPixelFromDip(this,25.0f);
        lp.setMargins(marginSide, marginTop, marginSide, marginTop);
        chooseView.setLayoutParams(lp);
        setmCustomDialogView(chooseView);
        DialogExchangeModel.DialogExchangeModelBuilder dialogExchangeModelBuilder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.CUSTOMER,"SELECT_DIALOG");
        dialogExchangeModelBuilder.setBackable(true);
        TDialogManager.showDialogFragment(getSupportFragmentManager(),dialogExchangeModelBuilder.creat(),null);
    }

    private MemoIconChooseView.SelecetIconCallBack mSelecetIconCallBack = new MemoIconChooseView.SelecetIconCallBack() {
        @Override
        public void onSelected(IconBgData mIconBgData) {
            if(null != mIconBgData){

            }
            ((DialogFragment)getSupportFragmentManager().findFragmentByTag("SELECT_DIALOG")).dismiss();
        }
    };

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

    /****set+get method****/
    public void setmCustomDialogView(View mCustomDialogView) {
        this.mCustomDialogView = mCustomDialogView;
    }
}
