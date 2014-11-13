package com.tedmemo.event;

/**
 * Created by Ted on 2014/9/23.
 */
public class CreateMemoIconEvent extends BaseEvent{
    int iconId = -1;

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
