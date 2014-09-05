package com.tedmemo.db;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ted on 14-7-29.
 */
public class IconDBUtil {
    private Context mContext;
    IconDBHelper mIconDBHelper;
    RuntimeExceptionDao<IconBgData, Integer> mIconDAO;

    public IconDBUtil(Context context) {
        this.mContext = context;
        mIconDBHelper = OpenHelperManager.getHelper(mContext, IconDBHelper.class);
        mIconDAO = mIconDBHelper.getHouseInfoDataDao();
    }

    /**
     * 插入
     */
    public void insert(IconBgData houseInfo) {
        mIconDAO.createOrUpdate(houseInfo);
    }

    /**
     * 按照指定的id 删除一项
     *
     * @param icon
     * @return 删除成功返回1 ，失败返回0
     */
    public int delete(IconBgData icon) {
        try {
            DeleteBuilder<IconBgData, Integer> deleteBuilder = mIconDAO.deleteBuilder();
            deleteBuilder.where().eq("name", icon.get_mName());
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 按照指定的id 删除一项
     *
     * @param infoID
     * @return 删除成功返回1 ，失败返回0
     */
    public int delete(long infoID) {
        try {
            DeleteBuilder<IconBgData, Integer> deleteBuilder = mIconDAO.deleteBuilder();
            deleteBuilder.where().eq("mUserInfoID", infoID);
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 删除全部
     */
    public void deleteAll() {
        mIconDAO.delete(queryAll());
    }

    /**
     * 查询所有的
     */
    public List<IconBgData> queryAll() {
        List<IconBgData> allIcons = mIconDAO.queryForAll();
        return allIcons;
    }

    public void closeDB(){
        mIconDBHelper.close();
    }
}
