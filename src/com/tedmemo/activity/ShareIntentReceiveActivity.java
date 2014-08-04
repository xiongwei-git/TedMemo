package com.tedmemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import com.android.TedFramework.util.LogUtil;

/**
 * Created by Ted on 14-8-4.
 */
public class ShareIntentReceiveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent localIntent = getIntent();
        if ("android.intent.action.SEND".equals(localIntent.getAction())){
            LogUtil.e("localIntent.getAction()");
        }
        super.onCreate(savedInstanceState);
    }


}
