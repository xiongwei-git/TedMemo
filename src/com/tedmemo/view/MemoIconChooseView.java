package com.tedmemo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.TedFramework.util.StringUtil;
import com.android.TedFramework.util.ToastUtil;
import com.android.tedwidget.view.TImageView;
import com.tedmemo.db.IconBgData;
import com.tedmemo.others.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ted on 2014/9/3.
 */
public class MemoIconChooseView extends RelativeLayout implements View.OnClickListener{

    private Context mContext;
    private IconBgData mIconBgData;
    private GridView mGridView;
    private TImageView mIconSelected;
    private IconGridAdapter mIconGridAdapter;

    @Override
    public void onClick(View v) {
        ToastUtil.show(getContext(),"点击了");
    }

    public MemoIconChooseView(Context context,IconBgData iconBgData) {
        super(context);
        this.mContext = context;
        this.mIconBgData = iconBgData;
        initView();
        initData();
    }

    private void initView(){
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        this.setBackgroundResource(R.drawable.mtmm_dialog_bg);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.edit_icon_dialog_view, this);
        mGridView = (GridView)contentView.findViewById(R.id.editIconGridView);
        mIconSelected = (TImageView)findViewById(R.id.editIconSelected);
        mIconSelected.setOnClickListener(this);
    }

    private void initData(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mIconSelected.setBackground(mIconBgData.getDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
        } else {
            mIconSelected.setBackgroundDrawable(mIconBgData.getDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
        }
        mIconGridAdapter = new IconGridAdapter(mContext);
        mIconGridAdapter.setDatas(Constants.ICON_NAME_1);
        mIconGridAdapter.setIconFilterColor(Constants.GRAY_COLOR,mIconBgData.getBackgroundColorOnStr());
        mGridView.setAdapter(mIconGridAdapter);
        mIconGridAdapter.notifyDataSetChanged();
    }

    public class IconGridAdapter extends BaseAdapter {
        private ArrayList<String> mIconDatas = new ArrayList<String>();
        private Context mContext;
        private String mFilterColorOffStr;
        private String mFilterColorOnStr;

        public IconGridAdapter(Context context){
            this.mContext = context;
        }

        public void setIconFilterColor(String colorOn,String colorOff){
            this.mFilterColorOnStr = colorOn;
            this.mFilterColorOffStr = colorOff;
        }

        public void setDatas(String[] datas){
            if(null !=datas && datas.length >0){
                for(String data:datas){
                    mIconDatas.add(data);
                }
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mIconDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mIconDatas.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TImageView imageView = new TImageView(mContext);
            imageView.setImageDrawable(getIconDrawable((String)getItem(position)));
            imageView.setFilterColor(mFilterColorOnStr,mFilterColorOffStr);
            return imageView;
        }

        private Drawable getIconDrawable(String iconName){
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("tag_");
            localStringBuilder.append("large_");
            localStringBuilder.append(iconName);
            int sourceId = mContext.getResources().getIdentifier(localStringBuilder.toString(), "drawable", mContext.getPackageName());
            if(sourceId <= 0 || StringUtil.emptyOrNull(iconName)){
                sourceId = R.drawable.tag_large_none;
            }
            return mContext.getResources().getDrawable(sourceId);
        }
    }

}
