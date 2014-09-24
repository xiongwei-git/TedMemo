package com.tedmemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.android.TedFramework.Fragment.TFragment;
import com.tedmemo.data.InnerMemoData;
import com.tedmemo.view.R;

import java.util.ArrayList;

/**
 * Created by Ted on 14-8-11.
 */
public class MainListFragment extends TFragment{

    private ListView mListView;
    private ArrayList<InnerMemoData> mListData = new ArrayList<InnerMemoData>();


    private static MainListFragment mMainListFragment = null;

    public static MainListFragment getInstance(){
        if(null == mMainListFragment){
            mMainListFragment = new MainListFragment();
        }
        return mMainListFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_main,null);
        initView(mRootView);
        return mRootView;
    }

    private void initView(View mRootView){
        mListView=(ListView)mRootView.findViewById(R.id.homeListView);


    }

}
