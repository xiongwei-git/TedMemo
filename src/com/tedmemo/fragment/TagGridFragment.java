package com.tedmemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.StringUtil;
import com.tedmemo.data.IconDataManager;
import com.tedmemo.db.IconBgData;
import com.tedmemo.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ted on 14-8-11.
 */
public class TagGridFragment extends TFragment {

    private static TagGridFragment mTagGridFragment = null;

    public static TagGridFragment getInstance(){
        if(null == mTagGridFragment){
            mTagGridFragment = new TagGridFragment();
        }
        return mTagGridFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.tag_fragment,null);
        return mRootView;
    }

    class TagGridAdapter extends BaseAdapter{
        private Context mContext;
        private ArrayList<IconBgData> iconBgDatas = new ArrayList<IconBgData>();

        public TagGridAdapter(){
            mContext = getActivity();
            List<IconBgData> allIcons = IconDataManager.getInstance(getActivity()).getmAllIconBgData();
            for (IconBgData iconBgData:allIcons){
                if(!StringUtil.emptyOrNull(iconBgData.get_mName())){
                    iconBgDatas.add(iconBgData);
                }
            }
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
            if(null == convertView){
                itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid, null);
            }else {
                itemView = convertView;
            }
            setItemData(itemView,position);
            return itemView;
        }

        private View setItemData(View itemView,int position) {
            if(null != getItem(position) && getItem(position) instanceof IconBgData){
                IconBgData iconBgData = (IconBgData)getItem(position);
                ((ImageView)itemView.findViewById(R.id.icon)).setImageDrawable(iconBgData.getDrawable(mContext));
                (itemView.findViewById(R.id.iconArea)).setBackground(iconBgData.getBackgroundDrawable(mContext,IconBgData.ICON_TYPE_LARGE));
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
    }
}
