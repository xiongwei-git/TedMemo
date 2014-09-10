package com.tedmemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.CheckDoubleClick;
import com.tedmemo.view.ImageEditText;
import com.tedmemo.view.R;
import com.tedmemo.view.manager.TInputMethodManager;

/**
 * Created by w_xiong on 2014/9/11.
 */
public class WriteMemoFragment extends TFragment implements View.OnClickListener{
    private ImageEditText mImageEditText;

    private static WriteMemoFragment self = null;
    public static WriteMemoFragment getInstance(){
        if(null == self){
            self = new WriteMemoFragment();
        }
        return self;
    }

    @Override
    public void onClick(View v) {
        if(!CheckDoubleClick.isFastDoubleClick()){
            switch (v.getId()){
                case R.id.cancelButton:
                    TInputMethodManager.hideSoftInput(mImageEditText);
                    getActivity().onBackPressed();
                    break;
                case R.id.saveButton:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_memo_fragment,null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        rootView.findViewById(R.id.cancelButton).setOnClickListener(this);
        rootView.findViewById(R.id.saveButton).setOnClickListener(this);
        mImageEditText =(ImageEditText)rootView.findViewById(R.id.imageEditText);
    }
}
