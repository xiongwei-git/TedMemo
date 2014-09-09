package com.tedmemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.tedmemo.data.IconDataManager;

/**
 * Created by Ted on 2014/9/9.
 */
public class SplashActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IconDataManager.getInstance(this).initIconDBDatas();
        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
