package com.tedmemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.tedwidget.view.TImageView;
import com.tedmemo.data.InnerMemoData;
import com.tedmemo.view.R;

import java.util.ArrayList;

/**
 * Created by Ted on 14-8-11.
 */
public class MainListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<InnerMemoData> mListData = new ArrayList<InnerMemoData>();


    public MainListAdapter(Context context,ArrayList<InnerMemoData> list) {
        this.mContext = context;
        setData(list);
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
        MemoCellClass memoCell = null;
        if(null == convertView){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.memo_list_cell, null);
            memoCell = new MemoCellClass();
            memoCell.init(convertView);
            convertView.setTag(memoCell);
        }else{
            memoCell = (MemoCellClass)convertView.getTag();
        }

        return convertView;
    }



    public static class MemoCellClass {

        public void init(View rootView){
            checkBox = (FrameLayout)rootView.findViewById(R.id.checkBox);
            checkBoxOff = rootView.findViewById(R.id.checkBoxOff);
            checkBoxOn = (ImageView)rootView.findViewById(R.id.checkBoxOn);
            memoListMaintextArea = (LinearLayout)rootView.findViewById(R.id.memoListMaintextArea);
            text_top = (TextView)rootView.findViewById(R.id.text_top);
            summary_image_layout = (LinearLayout)rootView.findViewById(R.id.summary_image_layout);
            summary_image = (ImageView)rootView.findViewById(R.id.summary_image);
            summary_cell = (LinearLayout)rootView.findViewById(R.id.summary_cell);
            article_url = (TextView)rootView.findViewById(R.id.article_url);
            article_thumb = (TImageView)rootView.findViewById(R.id.article_thumb);
            article_title = (TextView)rootView.findViewById(R.id.article_title);
            iconArea = (LinearLayout)rootView.findViewById(R.id.iconArea);
            icon = (ImageView)rootView.findViewById(R.id.icon);
            dateArea = (LinearLayout)rootView.findViewById(R.id.dateArea);
            see_more = (TextView)rootView.findViewById(R.id.see_more);
            update_date = (TextView)rootView.findViewById(R.id.update_date);
        }

        public  FrameLayout checkBox;
        public  View checkBoxOff;
        public  ImageView checkBoxOn;
        public  LinearLayout memoListMaintextArea;
        public  TextView text_top;
        public  LinearLayout summary_image_layout;
        public  ImageView summary_image;
        public  LinearLayout summary_cell;
        public  TextView article_url;
        public  TImageView article_thumb;
        public  TextView article_title;
        public  LinearLayout iconArea;
        public  ImageView icon;
        public  LinearLayout dateArea;
        public  TextView see_more;
        public  TextView update_date;
    }
}
