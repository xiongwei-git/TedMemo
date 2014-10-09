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
    //private ArrayList<Integer> mSelectMemo = new ArrayList<Integer>();

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
        /**在每次切换模式时候都要把上次勾选的记录清空*/
//        if(null != mSelectMemo){
//            mSelectMemo.clear();
//        }
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void selectMemo(View itemView,int position){
        InnerMemoData memoData = (InnerMemoData)getItem(position);
        /**已经包含，就移除*/
        if(memoData.getStatusCode() == 2){
            memoData.setStatusCode(1);
            //mSelectMemo.remove((Integer)position);
            updateCheckBoxState(itemView,1);
        }else {
            memoData.setStatusCode(2);
            //mSelectMemo.add(position);
            updateCheckBoxState(itemView,2);
        }
    }
    /***
     * 更新勾选框的状态
     * @param state 0正常 1待勾选 2已勾选
     *
     */
    private void updateCheckBoxState(View itemView,int state){
        if(null == itemView){
            return;
        }
        FrameLayout checkBox = (FrameLayout)itemView.findViewById(R.id.checkBox);
        View checkBoxOff = checkBox.findViewById(R.id.checkBoxOff);
        ImageView checkBoxOn = (ImageView)checkBox.findViewById(R.id.checkBoxOn);
        if(null == checkBoxOff || null == checkBoxOn){
            return;
        }
        LinearLayout.LayoutParams checkBoxLp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        checkBoxLp.gravity = Gravity.CENTER_VERTICAL;
        switch (state){
            case 0:
                itemView.setBackgroundResource(R.drawable.cell_drop_shadow);
                checkBoxOff.setVisibility(View.GONE);
                checkBoxLp.height = DeviceUtil.getPixelFromDip(mContext,17);
                checkBoxLp.width = DeviceUtil.getPixelFromDip(mContext,17);
                checkBoxOff.setVisibility(View.GONE);
                checkBoxOn.setVisibility(View.GONE);
                checkBox.setLayoutParams(checkBoxLp);
                break;
            case 1:
                itemView.setBackgroundResource(R.drawable.cell_drop_shadow);
                checkBox.setLayoutParams(checkBoxLp);
                checkBoxOff.setVisibility(View.VISIBLE);
                checkBoxOn.setVisibility(View.GONE);
                break;
            case 2:
                itemView.setBackgroundResource(R.drawable.cell_drop_shadow_checked);
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
        if(!isEditMode){
            InnerMemoData memoData = (InnerMemoData)mListData.get(position);
            memoData.setStatusCode(0);
            return memoData;
        }
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemoCellClass memoCell;
        if(null == convertView){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.memo_list_cell, null);
            memoCell = new MemoCellClass();
            memoCell.init(convertView);
            convertView.setTag(memoCell);
        }else{
            memoCell = (MemoCellClass)convertView.getTag();
        }

        updateItemData(convertView,memoCell,position);
        return convertView;
    }



    private void updateItemData(View view,MemoCellClass memoCell,int position){
        InnerMemoData memoData = (InnerMemoData)getItem(position);
        memoCell.iconArea.setOnClickListener(this);
        memoCell.iconArea.setOnLongClickListener(this);
        if(isEditMode){
            if(memoData.getStatusCode() == 2){
                updateCheckBoxState(view,2);
            }else {
                updateCheckBoxState(view,1);
            }
        }else {
            updateCheckBoxState(view,0);
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
