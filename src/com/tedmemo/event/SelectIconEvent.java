package com.tedmemo.event;

import com.tedmemo.others.Constants;

/**
 * Created by Ted on 2014/9/23.
 */
public class SelectIconEvent extends BaseEvent{
    private int iconId = -1;
    private int fromWhere = Constants.FROM_CREATE;

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(int fromWhere) {
        this.fromWhere = fromWhere;
    }
}
