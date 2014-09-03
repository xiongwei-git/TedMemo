package com.tedmemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Ted on 2014/9/3.
 */
public class MemoIconChooseView extends RelativeLayout {
    public MemoIconChooseView(Context context) {
        super(context);
        initView(context);
    }

    public MemoIconChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        View contentView = LayoutInflater.from(context).inflate(R.layout.edit_icon_dialog_view, this);
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.setBackgroundResource(R.drawable.mtmm_dialog_bg);
    }
}
