package com.tedmemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.CheckDoubleClick;
import com.android.TedFramework.util.StringUtil;
import com.tedmemo.data.IconDataManager;
import com.tedmemo.db.IconBgData;
import com.tedmemo.others.Constants;
import com.tedmemo.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ted on 14-8-11.
 */
public class TagGridFragment extends TFragment implements View.OnClickListener{
    /**呈现状态：正常、编辑*/
    public static final int STATE_NORMAL = 0;
    public static final int STATE_EDIT = 1;
    private int mShowState = STATE_NORMAL;

    private static TagGridFragment mTagGridFragment = null;
    private GridView mGridViewNormal;
    private GridView mGridViewEdit;
    private TagGridAdapter mGridAdapterNormal;
    private TagGridAdapter mGridAdapterEdit;
    private View mIconEditBtn;
    /**退出编辑模式的动画是否结束*/
    private boolean bIsOutAnimEnd = true;

    public static TagGridFragment getInstance(){
        if(null == mTagGridFragment){
            mTagGridFragment = new TagGridFragment();
        }
        return mTagGridFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.tag_fragment,null);
        initView(mRootView);
        initData();
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (v.getId()){
            case R.id.iconEditButton:
                switchGrideViewPage();
                break;
            default:
                break;
        }
    }

    /**初始化视图*/
    private void initView(View rootView){
        mGridViewEdit = (GridView)rootView.findViewById(R.id.tabGridViewEdit);
        mGridViewNormal = (GridView)rootView.findViewById(R.id.tabGridViewNormal);
        mIconEditBtn = rootView.findViewById(R.id.iconEditButton);
    }

    /**初始化数据*/
    private void initData() {
        mIconEditBtn.setOnClickListener(this);
        mGridAdapterNormal = new TagGridAdapter(STATE_NORMAL);
        mGridAdapterEdit = new TagGridAdapter(STATE_EDIT);
        mGridViewNormal.setAdapter(mGridAdapterNormal);
        mGridViewEdit.setAdapter(mGridAdapterEdit);

    }

    private void switchGrideViewPage(){
        if(!bIsOutAnimEnd){
            return;
        }
        if(mShowState == STATE_NORMAL){
            ((TextView)mIconEditBtn.findViewById(R.id.iconEditButtonText)).setText(R.string.complete_icon);
            mGridViewNormal.setVisibility(View.GONE);
            mGridViewEdit.setVisibility(View.VISIBLE);
            mGridAdapterEdit.setmNowAnim(TagGridAdapter.ANIM_IN);
            mGridAdapterEdit.notifyDataSetChanged();
            mShowState = STATE_EDIT;
        }else {
            ((TextView)mIconEditBtn.findViewById(R.id.iconEditButtonText)).setText(R.string.edit_icon);
            mGridAdapterEdit.setmNowAnim(TagGridAdapter.ANIM_OUT);
            mGridAdapterEdit.notifyDataSetChanged();
            mHandler.sendEmptyMessageDelayed(Constants.WAIT_ANIM_OVER,TagGridAdapter.ANIM_TIME);
            bIsOutAnimEnd = false;
        }
    }

private Handler mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        /**缩放动画结束了，做该做的事情*/
        if(msg.what == Constants.WAIT_ANIM_OVER){
            mGridViewNormal.setVisibility(View.VISIBLE);
            mGridViewEdit.setVisibility(View.GONE);
            mGridAdapterNormal.notifyDataSetChanged();
            mShowState = STATE_NORMAL;
            bIsOutAnimEnd = true;
        }
    }
};











    class TagGridAdapter extends BaseAdapter{
        /**动画状态：打开、收起*/
        public static final int ANIM_IN = 0;
        public static final int ANIM_OUT = 1;
        public static final long ANIM_TIME = 150;
        /**当前要做的动画*/
        private int mNowAnim = ANIM_IN;

        private Context mContext;
        private int mShowState = 0;
        private ArrayList<IconBgData> iconBgDatas = new ArrayList<IconBgData>();
        private ScaleAnimation mScaleInAnim;
        private ScaleAnimation mScaleOutAnim;
        private RotateAnimation mRotateInAnim;
        private RotateAnimation mRotateOutAnim;


        public TagGridAdapter(int state){
            this.mShowState = state;
            mContext = getActivity();
            List<IconBgData> allIcons = IconDataManager.getInstance(getActivity()).getmAllIconBgData();
            for (IconBgData iconBgData:allIcons){
                if(mShowState == STATE_NORMAL && StringUtil.emptyOrNull(iconBgData.get_mName())){
                    continue;
                }
                if(mShowState == STATE_EDIT && Constants.ICON_NAME_S.equalsIgnoreCase(iconBgData.get_mName())){
                    continue;
                }
                iconBgDatas.add(iconBgData);
            }
            createAnim();
        }

        @Override
        public int getCount() {
            if(null != iconBgDatas){
                return iconBgDatas.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return iconBgDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView;
            if(mShowState == STATE_NORMAL){
                if(null == convertView){
                    itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid, null);
                }else {
                    itemView = convertView;
                }
                setNormalItemData(itemView, position);
            }else {
                if(null == convertView){
                    itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid_edit, null);
                }else {
                    itemView = convertView;
                }
                setEditItemData(itemView,position);
            }

            return itemView;
        }

        private View setNormalItemData(View itemView,int position) {
            if(null != getItem(position) && getItem(position) instanceof IconBgData){
                IconBgData iconBgData = (IconBgData)getItem(position);
                ((ImageView)itemView.findViewById(R.id.icon)).setImageDrawable(iconBgData.getMixDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
                long memoCount = iconBgData.getMemoCount(mContext);
                if(memoCount > 0){
                    itemView.findViewById(R.id.memo_count).setVisibility(View.VISIBLE);
                    ((TextView)itemView.findViewById(R.id.memo_count)).setText(String.valueOf(memoCount));
                }else{
                    itemView.findViewById(R.id.memo_count).setVisibility(View.GONE);
                }
            }
            return itemView;
        }

        private View setEditItemData(View itemView,int position) {
            IconBgData iconBgData = (IconBgData)getItem(position);
            String iconName = iconBgData.get_mName();
            if(StringUtil.emptyOrNull(iconName)){
                itemView.findViewById(R.id.iconEditPlus).setVisibility(View.VISIBLE);
                ((ImageView)itemView.findViewById(R.id.iconEdit)).setImageDrawable(iconBgData.getBackgroundDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
            }else {
                itemView.findViewById(R.id.iconEditPlus).setVisibility(View.GONE);
                ((ImageView)itemView.findViewById(R.id.iconEdit)).setImageDrawable(iconBgData.getMixDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
            }
            if(mNowAnim == ANIM_IN){
                itemView.startAnimation(StringUtil.emptyOrNull(iconName)?mScaleInAnim:mRotateInAnim);
            }else {
                itemView.startAnimation(StringUtil.emptyOrNull(iconName)?mScaleOutAnim:mRotateOutAnim);
            }
            return itemView;
        }

        /**创建动画*/
        private void createAnim(){
            ScaleAnimation scaleInAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
            scaleInAnim.setFillAfter(true);
            scaleInAnim.setDuration(150);
            mScaleInAnim = scaleInAnim;

            ScaleAnimation scaleOutAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
            scaleOutAnim.setFillAfter(true);
            scaleOutAnim.setDuration(150);
            mScaleOutAnim = scaleOutAnim;

            RotateAnimation rotateInAnim = new RotateAnimation(0f,-30f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateInAnim.setFillAfter(true);
            rotateInAnim.setDuration(150);
            mRotateInAnim =  rotateInAnim;

            RotateAnimation rotateOutAnim = new RotateAnimation(-30f,0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
            rotateOutAnim.setFillAfter(true);
            rotateOutAnim.setDuration(150);
            mRotateOutAnim = rotateOutAnim;
        }

        public void setmNowAnim(int mNowAnim) {
            this.mNowAnim = mNowAnim;
        }
    }
}
