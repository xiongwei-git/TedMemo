package com.tedmemo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.TedFramework.util.*;
import com.android.tedwidget.view.TImageView;
import com.tedmemo.data.IconDataManager;
import com.tedmemo.data.InnerMemoData;
import com.tedmemo.db.IconBgData;
import com.tedmemo.view.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Ted on 14-8-11.
 */
public class MainListAdapter extends BaseAdapter implements View.OnClickListener,View.OnLongClickListener{
    private Context mContext;
    private ArrayList<InnerMemoData> mListData = new ArrayList<InnerMemoData>();

    private boolean isEditMode = false;

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

    public void selectMemo(View itemView,int position){
        InnerMemoData memoData = (InnerMemoData)getItem(position);
        /**已经包含，就移除*/
        if(memoData.getStatusCode() == 2){
            memoData.setStatusCode(1);
            updateCheckBoxState(itemView,1);
        }else {
            memoData.setStatusCode(2);
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
            case -1:
                itemView.setBackgroundResource(R.drawable.cell_drop_shadow_unread);
                checkBoxOff.setVisibility(View.GONE);
                checkBoxLp.height = DeviceUtil.getPixelFromDip(mContext,17);
                checkBoxLp.width = DeviceUtil.getPixelFromDip(mContext,17);
                checkBoxOff.setVisibility(View.GONE);
                checkBoxOn.setVisibility(View.GONE);
                checkBox.setLayoutParams(checkBoxLp);
                break;
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
        /**在非编辑模式下，便签的状态要么就是-1，代表未读。要么就是0，代表正常*/
        if(!isEditMode){
            InnerMemoData memoData = mListData.get(position);
            if(memoData.getStatusCode() != 0 && memoData.getStatusCode() != -1){
                memoData.setStatusCode(0);
                return memoData;
            }
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
        /**监听事件*/
        memoCell.iconArea.setOnClickListener(this);
        memoCell.iconArea.setOnLongClickListener(this);
        /**icon*/
        setMemoIcon(memoCell.icon,memoData);
        /**日期*/
        Calendar create = DateUtil.getCalFromTimeMillis(memoData.getCreated());
        memoCell.update_date.setText(DateUtil.getCalStrBySDF(create,DateUtil.SIMPLEFORMATTYPE17));
        /**勾选状态*/
        if(isEditMode){
            /**在编辑模式的时候，要么是已经被勾选中的状态为2，其他的都是待勾选的*/
            if(memoData.getStatusCode() == 2){
                updateCheckBoxState(view,2);
            }else {
                updateCheckBoxState(view,1);
            }
        }else {
            /**正常模式显示时，只有两种状态，未读和已读。两者只有背景不一致*/
            updateCheckBoxState(view,memoData.getStatusCode());
        }
        /**根据类型填充其他内容*/
        if(memoData.getType() == InnerMemoData.TYPE_IMG){

        }else if(memoData.getType() == InnerMemoData.TYPE_TEXT){
            createTextMemo(memoData,memoCell);
        }else if(memoData.getType() == InnerMemoData.TYPE_SUMMARY){

        }else {
            LogUtil.e("memoData.getType()类型错误");
        }
    }

    private void createTextMemo(InnerMemoData memoData,MemoCellClass memoCell){
        memoCell.summary_cell.setVisibility(View.GONE);
        memoCell.summary_image_layout.setVisibility(View.GONE);
        int textLength = memoData.getText().length();
        memoCell.see_more.setVisibility(textLength >60?View.VISIBLE:View.GONE);
        memoCell.text_top.setText(memoData.getText());
    }

    private void setMemoIcon(ImageView icon,final InnerMemoData memoData){
        String type = memoData.getContentType();
        if(StringUtil.emptyOrNull(type)){
            icon.setImageDrawable(IconDataManager.getInstance().getDefaultIconBg().getDrawable(mContext));
            return;
        };
        List<IconBgData> listIcon = IconDataManager.getInstance().getmAllIconBgData();
        for (IconBgData iconBg:listIcon){
            if(type.equals(iconBg.getBackgroundColorOnStr())){
                icon.setImageDrawable(iconBg.getDrawable(mContext));
                break;
            }
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
