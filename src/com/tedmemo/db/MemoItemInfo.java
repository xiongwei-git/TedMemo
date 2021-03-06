package com.tedmemo.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Ted on 14-8-6.
 */
@DatabaseTable(tableName = "memoinfo")
public class MemoItemInfo implements Serializable {
    private static final long serialVersionUID = -6919461967497580385L;

    /**Memo Id,Add by self*/
    @DatabaseField(allowGeneratedIdInsert=true, columnName="id", generatedId=true)
    private int _mId;

    @DatabaseField(columnName="is_read")
    private boolean _mIsRead;

    @DatabaseField(columnName="json_text")
    private String _mJsonText;

    @DatabaseField(columnName="text")
    private String _mText;

    /**对应的标签id*/
    @DatabaseField(columnName="icon_id")
    private int _mIconId;

    /**时间*/
    @DatabaseField(columnName="updated")
    private long _mUpdated;


    public MemoItemInfo(){

    }

    public int get_mId() {
        return _mId;
    }

    public void set_mId(int _mId) {
        this._mId = _mId;
    }

    public boolean is_mIsRead() {
        return _mIsRead;
    }

    public void set_mIsRead(boolean _mIsRead) {
        this._mIsRead = _mIsRead;
    }

    public String get_mJsonText() {
        return _mJsonText;
    }

    public void set_mJsonText(String _mJsonText) {
        this._mJsonText = _mJsonText;
    }

    public String get_mText() {
        return _mText;
    }

    public void set_mText(String _mText) {
        this._mText = _mText;
    }

    public long get_mUpdated() {
        return _mUpdated;
    }

    public int get_mIconId() {
        return _mIconId;
    }

    public void set_mIconId(int _mIconId) {
        this._mIconId = _mIconId;
    }

    public void set_mUpdated(long _mUpdated) {
        this._mUpdated = _mUpdated;
    }
}
