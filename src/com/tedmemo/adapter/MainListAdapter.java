package com.tedmemo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.TedFramework.util.DeviceUtil;
import com.android.TedFramework.util.ToastUtil;
import com.android.tedwidget.view.TImageView;
import com.tedmemo.data.InnerMemoData;
import com.tedmemo.view.R;

import java.util.ArrayList;

/**
 * Created by Ted on 14-8-11.
 */
public class MainListAdapter extends BaseAdapter implements View.OnClickListener,View.OnLongClickListener{
    private Context mContext;
    private ArrayList<InnerMemoData> mListData = new ArrayList<InnerMemoData>();

    private boolean isEditMode = false;
    /**当前勾选的Memo*/
    private ArrayList<Integer> mSelectMemo = new ArrayList<Integer>();

    @Override
    public void onClick(View v) {
        ToastUtil.show(mContext,"点击id="+v.getId());
    }

    @Override
    public boolean onLongClick(View v) {
        ToastUtil.show(mContext,"长按id="+v.getId());
        if(v.getId() == R.id.iconArea){
            return true;
        }
        return false;
    }

    public MainListAdapter(Context context,ArrayList<InnerMemoData> list) {
        this.mContext = context;
        setData(list);
    }


    public void setData(ArrayList<InnerMemoData> list){
        this.mListData = list;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void selectMemo(FrameLayout checkBox,int position){
        /**已经包含，就移除*/
        if(mSelectMemo.contains(position)){
            mSelectMemo.remove((Integer)position);
            updateCheckBoxState(checkBox,1);
        }else {
            mSelectMemo.add(position);
            updateCheckBoxState(checkBox,2);
        }
    }
    /***
     * 更新勾选框的状态
     * @param state 0正常 1待勾选 2已勾选
     *
     */
    private void updateCheckBoxState(FrameLayout checkBox,int state){
        if(null == checkBox){
            return;
        }
        View checkBoxOff = checkBox.findViewById(R.id.checkBoxOff);
        ImageView checkBoxOn = (ImageView)checkBox.findViewById(R.id.checkBoxOn);
        if(null == checkBoxOff || null == checkBoxOn){
            return;
        }
        LinearLayout.LayoutParams checkBoxLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBoxLp.gravity = Gravity.CENTER_VERTICAL;
        switch (state){
            case 0:
                checkBoxOff.setVisibility(View.GONE);
                checkBoxLp.height = DeviceUtil.getPixelFromDip(mContext,17);
                checkBoxLp.width = DeviceUtil.getPixelFromDip(mContext,17);
                checkBoxOff.setVisibility(View.GONE);
                checkBoxOn.setVisibility(View.GONE);
                checkBox.setLayoutParams(checkBoxLp);
                break;
            case 1:
                checkBox.setLayoutParams(checkBoxLp);
                checkBoxOff.setVisibility(View.VISIBLE);
                checkBoxOn.setVisibility(View.GONE);
                break;
            case 2:
                checkBox.setLayoutParams(checkBoxLp);
                checkBoxOff.setVisibility(View.GONE);
                checkBoxOn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
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

        updateItemData(memoCell,position);
        return convertView;
    }



    private void updateItemData(MemoCellClass memoCell,int position){
        InnerMemoData memoData = (InnerMemoData)getItem(position);
        memoCell.iconArea.setOnClickListener(this);
        memoCell.iconArea.setOnLongClickListener(this);
        if(isEditMode){
            if(mSelectMemo.contains(position)){
                updateCheckBoxState(memoCell.checkBox,2);
            }else {
                updateCheckBoxState(memoCell.checkBox,1);
            }
        }else {
            updateCheckBoxState(memoCell.checkBox,0);
        }
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
