package com.tedmemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.DateUtil;
import com.android.TedFramework.util.DeviceUtil;
import com.android.TedFramework.util.ToastUtil;
import com.tedmemo.activity.MainActivity;
import com.tedmemo.adapter.MainListAdapter;
import com.tedmemo.data.InnerMemoData;
import com.tedmemo.view.R;

import java.util.ArrayList;

/**
 * Created by Ted on 14-8-11.
 */
public class MainListFragment extends TFragment {

    private ListView mListView;
    private ArrayList<InnerMemoData> mListData = new ArrayList<InnerMemoData>();
    private MainListAdapter mMainListAdapter;

    private static MainListFragment mMainListFragment = null;

    public static MainListFragment getInstance(){
        if(null == mMainListFragment){
            mMainListFragment = new MainListFragment();
        }
        return mMainListFragment;
    }


    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /**因为新增了Header，所以position要减一*/
            position--;
            /**当前处于编辑模式*/
            if(mMainListAdapter.isEditMode()){
                if(null != view.findViewById(R.id.checkBox)){
                    mMainListAdapter.selectMemo(view,position);
                }
            }else {

            }
        }
    };

    private AdapterView.OnItemLongClickListener mItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            ((MainActivity)getActivity()).switchHeaderMode(MainActivity.HeaderMode.SelectMemo);
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_main,null);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initHeaderView();
        ArrayList<InnerMemoData> list = new ArrayList<InnerMemoData>();
        for (int i= 0;i<10;i++){
            InnerMemoData  memoData = new InnerMemoData();
            memoData.setType(1);
            memoData.setText("一个android项目开发到后期，页面和组件越来越多，页面与页面或者组件之间的通信就变得越来越繁多，使用接口回调或者或者或者的");
            list.add(memoData);
        }
        InnerMemoData  memoData = new InnerMemoData();
        memoData.setType(1);
        memoData.setText("android android android");
        list.add(memoData);
        mMainListAdapter = new MainListAdapter(getActivity(),list);
        mListView.setAdapter(mMainListAdapter);
        mListView.setOnItemClickListener(mOnItemClickListener);
        mListView.setOnItemLongClickListener(mItemLongClickListener);
    }

    public void setEditMode(MainActivity.HeaderMode mode){
        if(mode == MainActivity.HeaderMode.SelectMemo){
            mMainListAdapter.setEditMode(true);
        }else {
            mMainListAdapter.setEditMode(false);
        }
        mMainListAdapter.notifyDataSetChanged();
    }

    private void initView(View mRootView){
        mListView=(ListView)mRootView.findViewById(R.id.homeListView);
    }

    private void initHeaderView(){
        if(mListView.getHeaderViewsCount() > 0){
            return;
        }
        TextView header = new TextView(getActivity());
        int headerHeight = DeviceUtil.getPixelFromDip(getActivity(),55);
        header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT,headerHeight));
        mListView.addHeaderView(header);
    }

}
