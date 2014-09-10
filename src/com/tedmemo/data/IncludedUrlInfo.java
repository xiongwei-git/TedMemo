package com.tedmemo.data;

/**
 * Created by Ted on 2014/9/10.
 */
public class IncludedUrlInfo {
    private int _mEndPosition = 0;
    private int _mStartPosition = 0;
    private String _mUrl = "";

    public int getEndPosition() {
        return this._mEndPosition;
    }

    public int getStartPosition() {
        return this._mStartPosition;
    }

    public String getUrl() {
        return this._mUrl;
    }

    public void setEndPosition(int paramInt) {
        this._mEndPosition = paramInt;
    }

    public void setStartPosition(int paramInt) {
        this._mStartPosition = paramInt;
    }

    public void setUrl(String paramString) {
        this._mUrl = paramString;
    }
}
