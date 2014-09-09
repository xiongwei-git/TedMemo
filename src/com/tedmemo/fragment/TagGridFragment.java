package com.tedmemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.CheckDoubleClick;
import com.android.TedFramework.util.DeviceUtil;
import com.android.TedFramework.util.StringUtil;
import com.android.tedwidget.view.ScaleFramelayout;
import com.tedmemo.activity.MainActivity;
import com.tedmemo.data.IconDataManager;
import com.tedmemo.db.IconBgData;
import com.tedmemo.dialog.CustomerFragmentCallBack;
import com.tedmemo.dialog.DialogExchangeModel;
import com.tedmemo.dialog.DialogType;
import com.tedmemo.dialog.TDialogManager;
import com.tedmemo.others.Constants;
import com.tedmemo.view.MemoIconChooseView;
import com.tedmemo.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ted on 14-8-11.
 */
public class TagGridFragment extends TFragment implements View.OnClickListener ,CustomerFragmentCallBack {
    /**
     * 呈现状态：正常、编辑
     */
    public static final int STATE_NORMAL = 0;
    public static final int STATE_EDIT = 1;
    private int mShowState = STATE_NORMAL;

    private static TagGridFragment mTagGridFragment = null;
    private GridView mGridViewNormal;
    private GridView mGridViewEdit;
    private TagGridAdapter mGridAdapterNormal;
    private TagGridAdapter mGridAdapterEdit;
    private View mIconEditBtn;
    private View mCustomDialogView;
    private IconBgData mSelectIcon;
    /**
     * 退出编辑模式的动画是否结束
     */
    private boolean bIsOutAnimEnd = true;

    public static TagGridFragment getInstance() {
        if (null == mTagGridFragment) {
            mTagGridFragment = new TagGridFragment();
        }
        return mTagGridFragment;
    }
    private MemoIconChooseView.SelecetIconCallBack mSelecetIconCallBack = new MemoIconChooseView.SelecetIconCallBack() {
        @Override
        public void onSelected(IconBgData mIconBgData) {
            ((DialogFragment)getFragmentManager().findFragmentByTag("CTEATE_ICON_DIALOG")).dismiss();
            updateIconDataToDB(mIconBgData);
            refreshEditePage();
        }
    };

    private IconClickInterface mIconClickInterface = new IconClickInterface() {
        @Override
        public void clickEditIcon(IconBgData iconBgData) {
            if(null != iconBgData){
                if(StringUtil.emptyOrNull(iconBgData.get_mName())){
                    popupIconCreateDialog(iconBgData);
                }else {
                    popupChooseDialog(iconBgData);
                }
            }
        }

        @Override
        public void longClickIcon(int position) {
            if(mShowState == STATE_EDIT){
                return;
            }
            Vibrator mShakeVibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            long [] pattern = {10,130};
            mShakeVibrator.vibrate(pattern, -1);
            openEditIconMode();
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /**缩放动画结束了，做该做的事情*/
            if (msg.what == Constants.WAIT_ANIM_OVER) {
                mGridViewNormal.setVisibility(View.VISIBLE);
                mGridViewEdit.setVisibility(View.GONE);
                mGridAdapterNormal.refreshDatas();
                mGridAdapterNormal.notifyDataSetChanged();
                ((TextView) mIconEditBtn.findViewById(R.id.iconEditButtonText)).setText(R.string.edit_icon);
                mShowState = STATE_NORMAL;
                bIsOutAnimEnd = true;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.tag_fragment, null);
        initView(mRootView);
        initData();
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.iconEditButton:
                if (mShowState == STATE_EDIT) {
                    ((MainActivity) getActivity()).setEditHeader(View.GONE);
                } else {
                    openEditIconMode();
                }
                break;
            case R.id.editSureDlg_Change:
                ((DialogFragment)getFragmentManager().findFragmentByTag("CHOOSE_ACTION_DIALOG")).dismiss();
                if(null != mSelectIcon){
                    popupIconCreateDialog(mSelectIcon);
                }
                break;
            case R.id.editSureDlg_Delete:
                ((DialogFragment)getFragmentManager().findFragmentByTag("CHOOSE_ACTION_DIALOG")).dismiss();
                mSelectIcon.set_mName("");
                updateIconDataToDB(mSelectIcon);
                refreshEditePage();
                break;
            default:
                break;
        }
    }

    @Override
    public View getCustomerView(String mTag) {
        return mCustomDialogView;
    }

    /***
     * 退出编辑模式
     */
    public void closeEditIconMode() {
        /**如果上次退出的动画尚未做完，则不执行，避免短时间内快速操作*/
        if (!bIsOutAnimEnd) {
            return;
        }
        bIsOutAnimEnd = false;
        mGridAdapterEdit.setmNowAnim(TagGridAdapter.ANIM_OUT);
        mGridAdapterEdit.notifyDataSetChanged();
        mHandler.sendEmptyMessageDelayed(Constants.WAIT_ANIM_OVER, TagGridAdapter.ANIM_TIME);
    }

    /***
     * 打开编辑模式
     */
    private void openEditIconMode() {
        /**如果上次退出的动画尚未做完，则不执行，避免短时间内快速操作*/
        if (!bIsOutAnimEnd) {
            return;
        }
        mShowState = STATE_EDIT;
        ((MainActivity) getActivity()).setEditHeader(View.VISIBLE);
        ((TextView) mIconEditBtn.findViewById(R.id.iconEditButtonText)).setText(R.string.complete_icon);
        mGridViewNormal.setVisibility(View.GONE);
        mGridViewEdit.setVisibility(View.VISIBLE);
        mGridAdapterEdit.setmNowAnim(TagGridAdapter.ANIM_IN);
        mGridAdapterEdit.notifyDataSetChanged();
    }

    /***
     * 将一个icon数据更新到数据库
     * @param data
     */
    private void updateIconDataToDB(IconBgData data){
        if(null != data){
            IconDataManager.getInstance(getActivity()).updateIconDBDatas(data);
        }
    }

    /***
     * 在更新完数据之后调用该方法刷新编辑页面显示
     */
    private void refreshEditePage() {
        mGridAdapterEdit.setmNowAnim(TagGridAdapter.ANIM_IN);
        mGridAdapterEdit.notifyDataSetChanged();
    }

    /**
     * 初始化视图
     */
    private void initView(View rootView) {
        mGridViewEdit = (GridView) rootView.findViewById(R.id.tabGridViewEdit);
        mGridViewNormal = (GridView) rootView.findViewById(R.id.tabGridViewNormal);
        mIconEditBtn = rootView.findViewById(R.id.iconEditButton);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIconEditBtn.setOnClickListener(this);
        mGridAdapterNormal = new TagGridAdapter(STATE_NORMAL);
        mGridAdapterEdit = new TagGridAdapter(STATE_EDIT);
        mGridViewNormal.setAdapter(mGridAdapterNormal);
        mGridViewEdit.setAdapter(mGridAdapterEdit);
        mGridAdapterNormal.notifyDataSetChanged();
        mGridAdapterEdit.setmIconClickInterface(this.mIconClickInterface);
        mGridAdapterNormal.setmIconClickInterface(this.mIconClickInterface);

    }

    /***
     * 弹出icon小图片选择对话框
     * @param mIconBgData
     */
    private void popupIconCreateDialog(IconBgData mIconBgData){
        MemoIconChooseView chooseView = new MemoIconChooseView(getActivity(),mIconBgData);
        chooseView.setmSelecetIconCallBack(mSelecetIconCallBack);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        int marginTop = getResources().getDimensionPixelSize(R.dimen.action_bar_height)+ DeviceUtil.getPixelFromDip(getActivity(), 15.0f);
        int marginSide = DeviceUtil.getPixelFromDip(getActivity(),25.0f);
        lp.setMargins(marginSide, marginTop, marginSide, marginTop);
        chooseView.setLayoutParams(lp);
        setmCustomDialogView(chooseView);
        DialogExchangeModel.DialogExchangeModelBuilder dialogExchangeModelBuilder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.CUSTOMER,"CTEATE_ICON_DIALOG");
        dialogExchangeModelBuilder.setBackable(true);
        TDialogManager.showDialogFragment(getFragmentManager(), dialogExchangeModelBuilder.creat(), this);
    }

    /***
     * 弹出编辑选项对话框 编辑和删除
     * @param mIconBgData
     */
    private void popupChooseDialog(IconBgData mIconBgData) {
        this.mSelectIcon = mIconBgData;
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.edit_icon_sure_dialog_view, null);
        dialogView.findViewById(R.id.editSureDlg_Change).setOnClickListener(this);
        dialogView.findViewById(R.id.editSureDlg_Delete).setOnClickListener(this);
        int marginSide = DeviceUtil.getPixelFromDip(getActivity(),25.0f);
        lp.setMargins(marginSide, 0, marginSide, marginSide);
        dialogView.setLayoutParams(lp);
        setmCustomDialogView(dialogView);
        DialogExchangeModel.DialogExchangeModelBuilder dialogExchangeModelBuilder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.CUSTOMER,"CHOOSE_ACTION_DIALOG");
        dialogExchangeModelBuilder.setBackable(true);
        TDialogManager.showDialogFragment(getFragmentManager(), dialogExchangeModelBuilder.creat(), this);
    }



    class TagGridAdapter extends BaseAdapter {
        /**
         * 动画状态：打开、收起
         */
        public static final int ANIM_IN = 0;
        public static final int ANIM_OUT = 1;
        public static final int NO_ANIM = -1;
        public static final long ANIM_TIME = 150;
        /**
         * 当前要做的动画
         */
        private int mNowAnim = ANIM_IN;

        private Context mContext;
        private int mShowState = 0;
        private ArrayList<IconBgData> iconBgDatas = new ArrayList<IconBgData>();
        private ScaleAnimation mScaleInAnim;
        private ScaleAnimation mScaleOutAnim;
        private RotateAnimation mRotateInAnim;
        private RotateAnimation mRotateOutAnim;

        /**图标点击回调接口*/
        private IconClickInterface mIconClickInterface = null;


        private View.OnClickListener mEditIconOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if(null != v && v.getTag() instanceof Integer && null != mIconClickInterface){
                    int position = ((Integer)v.getTag()).intValue();
                    mIconClickInterface.clickEditIcon((IconBgData)getItem(position));
                }
            }
        };

        private ScaleFramelayout.onLongClickCallBack mLongClickCallBack = new ScaleFramelayout.onLongClickCallBack() {
            @Override
            public void onLongClick(View view) {
                mIconClickInterface.longClickIcon(0);
            }
        };

        public TagGridAdapter(int state) {
            this.mShowState = state;
            mContext = getActivity();
            refreshDatas();
            createAnim();
        }

        public void refreshDatas(){
            iconBgDatas.clear();
            List<IconBgData> allIcons = IconDataManager.getInstance(getActivity()).getmAllIconBgData();
            for (IconBgData iconBgData : allIcons) {
                if (mShowState == STATE_NORMAL && StringUtil.emptyOrNull(iconBgData.get_mName())) {
                    continue;
                }
                if (mShowState == STATE_EDIT && Constants.ICON_NAME_S.equalsIgnoreCase(iconBgData.get_mName())) {
                    continue;
                }
                iconBgDatas.add(iconBgData);
            }
        }

        @Override
        public int getCount() {
            if (null != iconBgDatas) {
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
            if (mShowState == STATE_NORMAL) {
                itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid, null);
//                if (null == convertView) {
//                    itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid, null);
//                } else {
//                    itemView = convertView;
//                }
                setNormalItemData(itemView, position);
            } else {
                itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid_edit, null);
//                if (null == convertView) {
//                    itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid_edit, null);
//                } else {
//                    itemView = convertView;
//                }
                setEditItemData(itemView, position);
            }

            return itemView;
        }

        private View setNormalItemData(View itemView, int position) {
            if (null != getItem(position) && getItem(position) instanceof IconBgData) {
                IconBgData iconBgData = (IconBgData) getItem(position);
                ((ImageView) itemView.findViewById(R.id.icon)).setImageDrawable(iconBgData.getMixDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
                long memoCount = iconBgData.getMemoCount(mContext);
                if (memoCount > 0) {
                    itemView.findViewById(R.id.memo_count).setVisibility(View.VISIBLE);
                    ((TextView) itemView.findViewById(R.id.memo_count)).setText(String.valueOf(memoCount));
                } else {
                    itemView.findViewById(R.id.memo_count).setVisibility(View.INVISIBLE);
                }
            }
            ((ScaleFramelayout)itemView).setmLongClickCallBack(mLongClickCallBack);
            itemView.setTag(position);
            return itemView;
        }

        private View setEditItemData(View itemView, int position) {
            IconBgData iconBgData = (IconBgData) getItem(position);
            String iconName = iconBgData.get_mName();
            if (StringUtil.emptyOrNull(iconName)) {
                itemView.findViewById(R.id.iconEditPlus).setVisibility(View.VISIBLE);
                ((ImageView) itemView.findViewById(R.id.iconEdit)).setImageDrawable(iconBgData.getBackgroundDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
            } else {
                itemView.findViewById(R.id.iconEditPlus).setVisibility(View.INVISIBLE);
                ((ImageView) itemView.findViewById(R.id.iconEdit)).setImageDrawable(iconBgData.getMixDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
            }
            if (mNowAnim == ANIM_IN) {
                itemView.startAnimation(StringUtil.emptyOrNull(iconName) ? mScaleInAnim : mRotateInAnim);
            } else if(mNowAnim == ANIM_OUT){
                itemView.startAnimation(StringUtil.emptyOrNull(iconName) ? mScaleOutAnim : mRotateOutAnim);
            }
            itemView.setTag(position);
            itemView.setOnClickListener(mEditIconOnClickListener);
            return itemView;
        }

        /**
         * 创建动画
         */
        private void createAnim() {
            ScaleAnimation scaleInAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
            scaleInAnim.setFillAfter(true);
            scaleInAnim.setDuration(ANIM_TIME);
            mScaleInAnim = scaleInAnim;

            ScaleAnimation scaleOutAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
            scaleOutAnim.setFillAfter(true);
            scaleOutAnim.setDuration(ANIM_TIME);
            mScaleOutAnim = scaleOutAnim;

            RotateAnimation rotateInAnim = new RotateAnimation(0f, -30f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateInAnim.setFillAfter(true);
            rotateInAnim.setDuration(ANIM_TIME);
            mRotateInAnim = rotateInAnim;

            RotateAnimation rotateOutAnim = new RotateAnimation(-30f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateOutAnim.setFillAfter(true);
            rotateOutAnim.setDuration(ANIM_TIME);
            mRotateOutAnim = rotateOutAnim;
        }

        public void setmNowAnim(int mNowAnim) {
            this.mNowAnim = mNowAnim;
        }

        public void setmIconClickInterface(IconClickInterface mIconClickInterface) {
            this.mIconClickInterface = mIconClickInterface;
        }
    }

    public interface IconClickInterface{
        void clickEditIcon(IconBgData iconBgData);
        void longClickIcon(int position);
    };

    /****set+get method****/
    public void setmCustomDialogView(View mCustomDialogView) {
        this.mCustomDialogView = mCustomDialogView;
    }
}
