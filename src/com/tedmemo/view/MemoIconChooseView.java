package com.tedmemo.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.TedFramework.util.CheckDoubleClick;
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
public class MemoIconChooseView extends RelativeLayout implements View.OnClickListener,AdapterView.OnItemClickListener {

    private Context mContext;
    private IconBgData mIconBgData;
    private GridView mGridView;
    private ImageView mIconSelected;
    private IconGridAdapter mIconGridAdapter;
    private int mCurrentPage = 0;
    private SelecetIconCallBack mSelecetIconCallBack;

    @Override
    public void onClick(View v) {
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (v.getId()){
            case R.id.editIconCloseDialogButton:
                if(null!= mSelecetIconCallBack){
                    mSelecetIconCallBack.onSelected(null);
                }
                break;
            case R.id.editIconOkButton:
                if(null!= mSelecetIconCallBack){
                    mSelecetIconCallBack.onSelected(mIconBgData);
                }
                break;
            case R.id.editIconCategoryButton1:
                setCurrentPage(0);
                mIconGridAdapter.setDatas(getIcondatas());
                mIconGridAdapter.notifyDataSetChanged();
                break;
            case R.id.editIconCategoryButton2:
                setCurrentPage(1);
                mIconGridAdapter.setDatas(getIcondatas());
                mIconGridAdapter.notifyDataSetChanged();
                break;
            case R.id.editIconCategoryButton3:
                setCurrentPage(2);
                mIconGridAdapter.setDatas(getIcondatas());
                mIconGridAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void setmSelecetIconCallBack(SelecetIconCallBack mSelecetIconCallBack) {
        this.mSelecetIconCallBack = mSelecetIconCallBack;
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
        mIconSelected = (ImageView)findViewById(R.id.editIconSelected);
        findViewById(R.id.editIconCloseDialogButton).setOnClickListener(this);
        findViewById(R.id.editIconOkButton).setOnClickListener(this);
        findViewById(R.id.editIconCategoryButton1).setOnClickListener(this);
        findViewById(R.id.editIconCategoryButton2).setOnClickListener(this);
        findViewById(R.id.editIconCategoryButton3).setOnClickListener(this);
    }

    private void initData(){
        setCurrentPage(0);
        refreshSelectedIcon();
        mIconGridAdapter = new IconGridAdapter(mContext);
        mIconGridAdapter.setDatas(getIcondatas());
        mIconGridAdapter.setIconBgData(mIconBgData);
        mGridView.setAdapter(mIconGridAdapter);
        //mGridView.setOnItemClickListener(this);
    }

    private void refreshSelectedIcon(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            mIconSelected.setImageDrawable(mIconBgData.getDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
        } else {
            mIconSelected.setImageDrawable(mIconBgData.getDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
        }
    }

    private String[] getIcondatas(){
        switch (mCurrentPage){
            case 0:
                return Constants.ICON_NAME_A;
            case 1:
                return Constants.ICON_NAME_B;
            case 2:
                return Constants.ICON_NAME_C;
            default:
                break;
        }
        return null;
    }

    private void setCurrentPage(int page){
        mCurrentPage = page;
        findViewById(R.id.editIconCategoryButton1).setSelected(page==0);
        findViewById(R.id.editIconCategoryButton2).setSelected(page==1);
        findViewById(R.id.editIconCategoryButton3).setSelected(page==2);
    }

    private OnClickListener mItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(CheckDoubleClick.isFastDoubleClick()){
                return;
            }
            if(view instanceof TImageView && view.getTag() instanceof String){
                String iconName = (String)view.getTag();
                mIconBgData.set_mName(iconName);
                mIconGridAdapter.setIconBgData(mIconBgData);
                mIconGridAdapter.notifyDataSetChanged();
                refreshSelectedIcon();
            }
        }
    };

    public class IconGridAdapter extends BaseAdapter {
        private ArrayList<String> mIconDatas = new ArrayList<String>();
        private Context mContext;
        private IconBgData mIconBgData;

        public IconGridAdapter(Context context){
            this.mContext = context;
        }

        public void setDatas(String[] datas){
            mIconDatas.clear();
            if(null !=datas && datas.length >0){
                for(String data:datas){
                    mIconDatas.add(data);
                }
            }
        }

        public void setIconBgData(IconBgData iconBgData){
            this.mIconBgData = iconBgData;
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
            String iconName= (String)getItem(position);
            TImageView imageView = new TImageView(mContext);
            imageView.setImageDrawable(getIconDrawable(iconName));
            imageView.setFilterColor(Constants.GRAY_ICON_COLOR, mIconBgData.getBackgroundColorOnStr());
            if(iconName.equalsIgnoreCase(mIconBgData.get_mName())){
                imageView.setFilterState(TImageView.FILTER_OFF);
            }
            imageView.setTag(iconName);
            imageView.setOnClickListener(mItemClickListener);
            return imageView;
        }

        private Drawable getIconDrawable(String iconName){
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("tag_");
            localStringBuilder.append("large_");
            localStringBuilder.append(iconName);
            int sourceId = mContext.getResources().getIdentifier(localStringBuilder.toString(), "drawable", mContext.getPackageName());
            if(sourceId <= 0 || StringUtil.emptyOrNull(iconName)){
                sourceId = R.drawable.tag_large_s_none;
            }
            return mContext.getResources().getDrawable(sourceId);
        }
    }
    public interface SelecetIconCallBack{
        public void onSelected(IconBgData mIconBgData);
    }
}
