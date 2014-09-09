package com.tedmemo.data;

import android.content.Context;
import com.tedmemo.db.IconBgData;
import com.tedmemo.db.IconDBUtil;
import com.tedmemo.others.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ted on 2014/9/5.
 * 管理icon资源的类
 */
public class IconDataManager {
    private Context mContext;
    private IconDBUtil iconDBUtil;
    private List<IconBgData> mAllIconBgData = new ArrayList<IconBgData>();
    public static IconDataManager mManager;
    public static IconDataManager getInstance(Context context){
        if(null == mManager){
            mManager = new IconDataManager(context);
        }
        return mManager;
    }
    public IconDataManager(Context context){
        this.mContext = context;
    }

    public void initIconDBDatas(){
        iconDBUtil = new IconDBUtil(mContext);
        mAllIconBgData = iconDBUtil.queryAll();
        if(null == mAllIconBgData || mAllIconBgData.size() == 0){
            createDefaultData();
        }
    }

    public void updateIconDBDatas(IconBgData iconBgData){
        iconDBUtil.insert(iconBgData);
    }

    public void clostIconDB(){
        if(null != iconDBUtil){
            iconDBUtil.closeDB();
        }
    }

    /**创建默认的数据，一般只在第一次启动app时调用*/
    private void createDefaultData(){
        ArrayList<String> colorData = new ArrayList<String>();
        if(null == mAllIconBgData){
            mAllIconBgData = new ArrayList<IconBgData>();
        }
        for (String color: Constants.ICON_COLORS){
            colorData.add(color);
        }
        if(colorData.size() > 0){
            for(int i = 0;i < colorData.size();i++){
                IconBgData iconBgData = new IconBgData();
                iconBgData.setBackgroundColorOnStr(colorData.get(i));
                if(i == colorData.size()-1){
                    iconBgData.set_mName(Constants.ICON_NAME_S);
                }
                iconBgData.set_id(i);
                iconBgData.setMemoCount(0);
                mAllIconBgData.add(iconBgData);
                updateIconDBDatas(iconBgData);
            }
        }
    }

    public List<IconBgData> getmAllIconBgData() {
        return mAllIconBgData;
    }
}
