package com.tedmemo.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ted on 14-8-6.
 */
@DatabaseTable(tableName = "memoinfo")
public class MemoItemInfo {
    /**Memo Id,Add by self*/
    @DatabaseField(generatedId = true)
    int mInfoId;

}
