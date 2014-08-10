package com.tedmemo.service;

import android.app.Service;
import android.content.*;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Toast;
import com.android.TedFramework.util.LogUtil;
import com.android.TedFramework.util.ToastUtil;
import com.tedmemo.db.MemoItemInfo;
import com.tedmemo.others.Constants;
import com.tedmemo.others.ShakeListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Ted on 14-8-7.
 */
public class WatchingService extends Service implements ShakeListener.OnShakeListener,ClipboardManager.OnPrimaryClipChangedListener{
    private Context mContext;
    private ShakeListener mShakeListener;
    private AtomicBoolean mShakeCallOver;
    private Vibrator mShakeVibrator;
    private ClipboardManager mClipboardManager = null;

    private MemoItemInfo mMemoItemInfo;

    /**是否有新的便签等待插入*/
    private boolean mHasNewMemo = false;
    /**上一个便签插入是否结束*/
    private boolean mInsertFinish = true;

    private long mLastUpdateTime = 0l;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constants.CLOSE_SHAKE_MSG:
                    stopListenShake();
                    break;
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mMemoItemInfo = new MemoItemInfo();
        mContext = getApplicationContext();
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(this);
        mShakeCallOver = new AtomicBoolean(true);
        mShakeVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mClipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboardManager.addPrimaryClipChangedListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mShakeListener.stop();
        mClipboardManager.removePrimaryClipChangedListener(this);
        super.onDestroy();
    }

    @Override
    public void onPrimaryClipChanged() {
        makeClipMemo();
        startListenShake();
    }

    @Override
    public void onShake() {
        if(mShakeCallOver.compareAndSet(true,false)){
            stopListenShake();
            doVibrate();
            mShakeCallOver.set(true);
        }
    }


    private void makeClipMemo(){
        if(!mInsertFinish){
            LogUtil.e("上次插入事务尚未结束");
            return;
        }
        long l1 = System.currentTimeMillis();
        if (l1 - this.mLastUpdateTime < 700L)
        {
            LogUtil.e("与上次间隔时间太短");
            return;
        }
        ClipDescription localClipDescription;
        ClipData localClipData;
        StringBuilder localStringBuilder;
        localClipDescription = this.mClipboardManager.getPrimaryClipDescription();
        if ((!localClipDescription.hasMimeType("text/plain")) && (!localClipDescription.hasMimeType("text/html")))
        {
            LogUtil.e("剪切板里面的不是文本内容");
            return;
        }
        this.mLastUpdateTime = l1;
        localClipData = this.mClipboardManager.getPrimaryClip();
        mMemoItemInfo.set_mUpdated(System.currentTimeMillis());
        localStringBuilder = new StringBuilder();
        int count = localClipData.getItemCount();
        for(int i=0;i < count;i++){
            ClipData.Item item = localClipData.getItemAt(i);
            if(null != item){
                localStringBuilder.append(item.getText());
            }
        }
        //ToastUtil.show(mContext,localStringBuilder.toString());
    }
    private void doVibrate(){
        if(null == mShakeVibrator){
            mShakeVibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        }
        long [] pattern = {100,400};
        mShakeVibrator.vibrate(pattern, -1);
    }

    private void startListenShake(){
        if(null == mShakeListener){
            mShakeListener = new ShakeListener(this);
            mShakeListener.setOnShakeListener(this);
        }
        mShakeListener.start();
        mHandler.sendEmptyMessageDelayed(Constants.CLOSE_SHAKE_MSG,Constants.LISTEN_SHAKE_TIME);
    }

    private void stopListenShake(){
        if(null != mShakeListener){
            if(mShakeCallOver.compareAndSet(true,false)){
                mShakeListener.stop();
                mShakeCallOver.set(true);
            }
        }

    }
}
