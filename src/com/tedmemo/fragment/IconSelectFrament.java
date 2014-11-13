package com.tedmemo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.CheckDoubleClick;
import com.android.TedFramework.util.StringUtil;
import com.android.TedFramework.util.ToastUtil;
import com.tedmemo.data.IconDataManager;
import com.tedmemo.db.IconBgData;
import com.tedmemo.event.CreateMemoIconEvent;
import com.tedmemo.event.SelectIconEvent;
import com.tedmemo.view.R;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w_xiong on 2014/9/12.
 */
public class IconSelectFrament extends TFragment implements View.OnClickListener {
    private boolean bIsCreatMemo = false;
    private ArrayList<IconBgData> iconBgDatas = new ArrayList<IconBgData>();
    private GridView mGridView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelButton:
                getActivity().onBackPressed();
                break;
            case R.id.temporarySaveButton:
                ToastUtil.show(getActivity(),"创建了便签");
                getActivity().onBackPressed();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != getArguments()){
            bIsCreatMemo = getArguments().getBoolean("CREATE_MEMO",false);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.icon_selection_fragment, container,false);
        initView(rootView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView.setAdapter(new IconGridAdapter());
    }

    private void initView(View rootView) {
        mGridView = (GridView)rootView.findViewById(R.id.icon_selection_gridview);
        rootView.findViewById(R.id.cancelButton).setOnClickListener(this);
        rootView.findViewById(R.id.temporarySaveButton).setVisibility(bIsCreatMemo?View.VISIBLE:View.GONE);
        rootView.findViewById(R.id.temporarySaveButton).setOnClickListener(this);
    }

    /***
     * 点击了icon
     * @param event
     */
    public void onEventMainThread(CreateMemoIconEvent event) {
        ToastUtil.show(getActivity(),"创建了便签");
        getActivity().onBackPressed();
    }

    class IconGridAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<IconBgData> iconBgDatas = new ArrayList<IconBgData>();


        private View.OnClickListener mEditIconOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if(null != v && v.getTag() instanceof Integer){
                    int position = ((Integer)v.getTag()).intValue();
                    if(position>=0 && position < getCount()){
                        IconBgData iconBgData = (IconBgData)getItem(position);
                        if(bIsCreatMemo){
                            CreateMemoIconEvent event = new CreateMemoIconEvent();
                            event.setIconId(iconBgData.get_id());
                            EventBus.getDefault().post(event);
                        }else {
                            SelectIconEvent event = new SelectIconEvent();
                            event.setIconId(iconBgData.get_id());
                            EventBus.getDefault().post(event);
                        }
                    }
                }
            }
        };


        public IconGridAdapter() {
            mContext = getActivity();
            refreshDatas();
        }

        public void refreshDatas(){
            iconBgDatas.clear();
            List<IconBgData> allIcons = IconDataManager.getInstance().getmAllIconBgData();
            for (IconBgData iconBgData : allIcons) {
                if (StringUtil.emptyOrNull(iconBgData.get_mName())) {
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
            itemView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_grid, null);
            setItemData(itemView, position);
            return itemView;
        }

        private View setItemData(View itemView, int position) {
            if (null != getItem(position) && getItem(position) instanceof IconBgData) {
                IconBgData iconBgData = (IconBgData) getItem(position);
                ((ImageView) itemView.findViewById(R.id.icon)).setImageDrawable(iconBgData.getDrawable(mContext, IconBgData.ICON_TYPE_LARGE));
                long memoCount = iconBgData.getMemoCount(mContext);
                if (memoCount > 0) {
                    itemView.findViewById(R.id.memo_count).setVisibility(View.VISIBLE);
                    ((TextView) itemView.findViewById(R.id.memo_count)).setText(String.valueOf(memoCount));
                } else {
                    itemView.findViewById(R.id.memo_count).setVisibility(View.INVISIBLE);
                }
            }
            itemView.setOnClickListener(mEditIconOnClickListener);
            itemView.setTag(position);
            return itemView;
        }
    }
}
