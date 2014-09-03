package com.tedmemo.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import com.tedmemo.fragment.FragmentExchangeController;
import com.tedmemo.view.R;

public class BaseDialogFragment extends DialogFragment {
    public final static String TAG = "BaseDialogFragment";
    protected String mDialogTag;// 标记
    protected String mPositiveBtnTxt;// 确认操作
    protected String mNegativeBtnTxt;// 取消操作
    protected String mSingleBtnTxt;// 单个button文字
    protected String mContentTxt;// 内容
    public boolean bIsBackable;// 是否back取消
    public boolean bIsSpaceable;// 是否空白取消
    public int gravity = Gravity.CENTER;
    protected OnClickListener mSpaceClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (bIsSpaceable) {
                dismissSelf();
            }
        }
    };

    public static BaseDialogFragment getInstance(Bundle bundle) {
        BaseDialogFragment baseDialogFragment = new BaseDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public BaseDialogFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.ThemeHolo);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            DialogExchangeModel ctripDialogExchangeModel = ((DialogExchangeModel.DialogExchangeModelBuilder) bundle
                    .getSerializable(TAG)).creat();
            if (ctripDialogExchangeModel != null) {
                mDialogTag = ctripDialogExchangeModel.getTag();
                bIsBackable = ctripDialogExchangeModel.isBackable();
                bIsSpaceable = ctripDialogExchangeModel.isSpaceable();
                mContentTxt = ctripDialogExchangeModel.getDialogContext();
                setCancelable(bIsBackable);
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismissAllowingStateLoss();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return show(transaction, tag, true);
    }

    public int show(FragmentTransaction transaction, String tag, boolean allowStateLoss) {
        transaction.add(this, tag);
        int mBackStackId = allowStateLoss ? transaction.commitAllowingStateLoss() : transaction.commit();
        return mBackStackId;
    }

    public void dismissSelf() {
        FragmentExchangeController.removeFragment(getFragmentManager(), this);
    }

}
