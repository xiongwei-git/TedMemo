package com.tedmemo.others;

import android.text.Editable;
import android.text.style.ImageSpan;
import com.tedmemo.view.ImageEditText;

import java.util.Comparator;

/**
 * Created by Ted on 2014/9/10.
 */
public class TComparator implements Comparator<ImageSpan> {
    private Editable mEditable = null;
    private ImageEditText mImageEditText;

    public TComparator(ImageEditText imageEditText, Editable editable) {
        this.mEditable = editable;
        this.mImageEditText = imageEditText;
    }

    @Override
    public int compare(ImageSpan lhs, ImageSpan rhs) {
        if (this.mEditable.getSpanStart(lhs) > this.mEditable.getSpanStart(rhs)) {
            return 1;
        }
        if (this.mEditable.getSpanStart(lhs) == this.mEditable.getSpanStart(rhs)) {
            return 0;
        }
        return -1;
    }
}
