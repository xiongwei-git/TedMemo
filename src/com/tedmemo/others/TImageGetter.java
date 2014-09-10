package com.tedmemo.others;

import android.graphics.drawable.Drawable;
import android.text.Html;
import com.tedmemo.view.ImageEditText;

/**
 * Created by Ted on 2014/9/10.
 */
public class TImageGetter implements Html.ImageGetter {
    private Drawable mDrawable;

    public TImageGetter(ImageEditText imageEditText, Drawable drawable) {
        this.mDrawable = drawable;
    }

    public Drawable getDrawable(String paramString) {
        this.mDrawable.setBounds(0, 0, this.mDrawable.getMinimumWidth(), this.mDrawable.getMinimumHeight());
        return this.mDrawable;
    }
}
