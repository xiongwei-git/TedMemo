package com.tedmemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tedmemo.others.Constants;

import java.sql.SQLException;

/**
 * Created by Ted on 14-7-29.
 */
public class DBOpenHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = "DBOpenHelper";

    private Dao<IconBgData, Integer> iconDBDao = null;
    private Dao<MemoItemInfo, Integer> memoDBDao = null;
    private RuntimeExceptionDao<IconBgData, Integer> iconDBRuntimeDao = null;
    private RuntimeExceptionDao<MemoItemInfo, Integer> memoDBRuntimeDao = null;

    public DBOpenHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    public DBOpenHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, IconBgData.class);
            TableUtils.createTable(connectionSource, MemoItemInfo.class);
            iconDBDao = getIconBgDao();
            memoDBDao = getMemoDao();
            iconDBRuntimeDao = getIconBgRunDao();
            memoDBRuntimeDao = getMemoRunDao();
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, IconBgData.class, true);
            TableUtils.dropTable(connectionSource, MemoItemInfo.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DBOpenHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new " + newVer, e);
        }
    }

    private Dao<IconBgData, Integer> getIconBgDao() throws SQLException {
        if (iconDBDao == null)
            iconDBDao = getDao(IconBgData.class);
        return iconDBDao;
    }

    public RuntimeExceptionDao<IconBgData, Integer> getIconBgRunDao() {
        if (iconDBRuntimeDao == null) {
            iconDBRuntimeDao = getRuntimeExceptionDao(IconBgData.class);
        }
        return iconDBRuntimeDao;
    }
    private Dao<MemoItemInfo, Integer> getMemoDao() throws SQLException {
        if (memoDBDao == null)
            memoDBDao = getDao(MemoItemInfo.class);
        return memoDBDao;
    }

    public RuntimeExceptionDao<MemoItemInfo, Integer> getMemoRunDao() {
        if (memoDBRuntimeDao == null) {
            memoDBRuntimeDao = getRuntimeExceptionDao(MemoItemInfo.class);
        }
        return memoDBRuntimeDao;
    }
}
