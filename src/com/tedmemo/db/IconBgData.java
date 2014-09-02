package com.tedmemo.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Ted on 14-8-19.
 */
@DatabaseTable(tableName = "memoinfo")
public class IconBgData {
    /**颜色ID 范围1-16代表不同颜色*/
    @DatabaseField(allowGeneratedIdInsert=true, columnName="id", generatedId=false)
    private int _mColorId;



}
