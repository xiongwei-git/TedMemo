package com.tedmemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.TedFramework.Fragment.TFragment;
import com.tedmemo.view.R;

/**
 * Created by w_xiong on 2014/9/12.
 */
public class IconSelectFrament extends TFragment implements View.OnClickListener {
    private static IconSelectFrament self = null;

    public static IconSelectFrament getInstance() {
        if (null == self) {
            self = new IconSelectFrament();
        }
        return self;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                getActivity().onBackPressed();
                break;
            case R.id.saveButton:
                break;
            case R.id.galleryButton:
                break;
            case R.id.imageCaptureButton:
                break;
            case R.id.tagButton:
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.icon_selection_fragment, container,false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        rootView.findViewById(R.id.cancelButton).setOnClickListener(this);
    }
}
