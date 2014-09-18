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
    private DBOpenHelper mIconDBHelper;
    RuntimeExceptionDao<IconBgData, Integer> mIconDAO;

    public IconDBUtil(Context context) {
        this.mContext = context;
        mIconDBHelper = OpenHelperManager.getHelper(mContext, DBOpenHelper.class);
        mIconDAO = mIconDBHelper.getIconBgRunDao();
    }

    /**
     * 插入
     */
    public void insert(IconBgData iconBgData) {
        mIconDAO.createOrUpdate(iconBgData);
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
            deleteBuilder.where().eq("id", icon.get_mName());
            return deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 按照指定的id 删除一项
     *
     * @param id
     * @return 删除成功返回1 ，失败返回0
     */
    public int delete(long id) {
        try {
            DeleteBuilder<IconBgData, Integer> deleteBuilder = mIconDAO.deleteBuilder();
            deleteBuilder.where().eq("id", id);
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
