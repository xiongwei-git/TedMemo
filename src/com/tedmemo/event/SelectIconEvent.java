package com.tedmemo.event;

import com.tedmemo.fragment.IconSelectFrament.SlectIconSrc;
/**
 * Created by Ted on 2014/9/23.
 */
public class SelectIconEvent extends BaseEvent{
    private int iconId = -1;
    private SlectIconSrc src = SlectIconSrc.Null;

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public SlectIconSrc getSrc() {
        return src;
    }

    public void setSrc(SlectIconSrc src) {
        this.src = src;
    }
}
