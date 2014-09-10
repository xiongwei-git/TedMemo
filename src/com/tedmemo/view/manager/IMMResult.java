package com.tedmemo.view.manager;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by w_xiong on 2014/9/11.
 */
public class IMMResult extends ResultReceiver {
    public int result = -1;

    public IMMResult() {
        super(null);
    }

    @Override
    public void onReceiveResult(int r, Bundle data) {
        result = r;
    }

    public int getResult() {
        return result;
    }
}