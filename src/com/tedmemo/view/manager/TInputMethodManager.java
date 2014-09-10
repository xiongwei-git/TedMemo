package com.tedmemo.view.manager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.tedmemo.base.TMApplication;
/**
 * Created by w_xiong on 2014/9/11.
 */
public class TInputMethodManager {
    /***
     * 隐藏输入框
     * @param view
     */
    public static void hideSoftInput(EditText view) {
        final InputMethodManager imm = (InputMethodManager) TMApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        final IMMResult result = new IMMResult();
        if (null != view) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0, result);
        }
    }

    public static void hideSoftInput(Fragment fragment) {
        final InputMethodManager imm = (InputMethodManager) TMApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        final IMMResult result = new IMMResult();
        if (null != fragment && imm.isActive() && fragment.getActivity().getCurrentFocus()!=null) {
            imm.hideSoftInputFromWindow(fragment.getActivity().getCurrentFocus().getWindowToken(), 0, result);
        }
    }

    /***
     * 显示输入框
     * @param view
     */
    public static void showSoftInput(EditText view) {
        final InputMethodManager imm = (InputMethodManager) TMApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        final IMMResult result = new IMMResult();
        imm.showSoftInput(view, 0, result);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                int res = result.getResult();
                if (res != InputMethodManager.RESULT_SHOWN && res != InputMethodManager.RESULT_UNCHANGED_HIDDEN) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        }, 500);
        view.requestFocus();
    }
}
