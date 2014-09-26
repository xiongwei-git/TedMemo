package com.tedmemo.db;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.tedmemo.base.TMApplication;

import java.util.List;

/**
 * Created by Ted on 14-7-29.
 */
public class DBUtil {
    private static DBUtil self = null;

    private DBOpenHelper mDBHelper;
    private RuntimeExceptionDao<IconBgData, Integer> mIconDAO;
    private RuntimeExceptionDao<MemoItemInfo, Integer> mMemoRunDAO;

    public static DBUtil getInstance(){
        if(null == self){
            self = new DBUtil();
        }
        return self;
    }

    public DBUtil() {
        mDBHelper = OpenHelperManager.getHelper(TMApplication.getInstance(), DBOpenHelper.class);
        mIconDAO = mDBHelper.getIconBgRunDao();
        mMemoRunDAO = mDBHelper.getMemoRunDao();
    }


    public void insertMemo(MemoItemInfo memo) {
        mMemoRunDAO.createOrUpdate(memo);
    }

    public List<MemoItemInfo> queryAllMemos() {
        List<MemoItemInfo> allMemos = mMemoRunDAO.queryForAll();
        return allMemos;
    }

    public List<MemoItemInfo> queryMemosBy(){
        List<MemoItemInfo> allMemos = mMemoRunDAO.queryForAll();
        return allMemos;
    }


    public void insertIcon(IconBgData iconBgData) {
        mIconDAO.createOrUpdate(iconBgData);
    }

    public List<IconBgData> queryAllIconS() {
        List<IconBgData> allIcons = mIconDAO.queryForAll();
        return allIcons;
    }







    public void closeDB(){
        OpenHelperManager.releaseHelper();
        mIconDAO = null;
        self = null;
    }
}
