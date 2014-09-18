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
    private static final String TAG = "IconDBHelper";

    private Dao<IconBgData, Integer> iconDBDao = null;
    private RuntimeExceptionDao<IconBgData, Integer> iconDBRuntimeDao = null;

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
            iconDBDao = getIconBgDao();
            iconDBRuntimeDao = getIconBgRunDao();
        } catch (SQLException e) {
            Log.e(TAG, e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, IconBgData.class, true);
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
    /**
     * 释放 DAO
     */
    @Override
    public void close() {
        super.close();
        iconDBRuntimeDao = null;
    }
}
