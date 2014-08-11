package com.tedmemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.TedFramework.Fragment.TFragment;
import com.tedmemo.view.R;

/**
 * Created by Ted on 14-8-11.
 */
public class TagGridFragment extends TFragment {

    private static TagGridFragment mTagGridFragment = null;

    public static TagGridFragment getInstance(){
        if(null == mTagGridFragment){
            mTagGridFragment = new TagGridFragment();
        }
        return mTagGridFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.tag_fragment,null);
        return mRootView;
    }
}
