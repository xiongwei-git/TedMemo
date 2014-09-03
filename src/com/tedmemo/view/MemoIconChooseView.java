package com.tedmemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Ted on 2014/9/3.
 */
public class MemoIconChooseView extends LinearLayout {
    public MemoIconChooseView(Context context) {
        super(context);
        initView(context);
    }

    public MemoIconChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        View contentView = LayoutInflater.from(context).inflate(R.layout.edit_icon_dialog_view, null);
        addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
