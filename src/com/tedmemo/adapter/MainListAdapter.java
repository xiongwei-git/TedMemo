package com.tedmemo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.tedmemo.data.InnerMemoData;

import java.util.ArrayList;

/**
 * Created by Ted on 14-8-11.
 */
public class MainListAdapter extends BaseAdapter{
    private ArrayList<InnerMemoData> mListData = new ArrayList<InnerMemoData>();


    public MainListAdapter() {
        super();
    }

    private void setData(ArrayList<InnerMemoData> list){
        this.mListData = list;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(null != convertView){

        }
        return convertView;
    }



}
