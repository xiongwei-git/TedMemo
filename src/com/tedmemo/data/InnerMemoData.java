package com.tedmemo.data;

/**
 * Created by w_xiong on 2014/8/11.
 */
public class InnerMemoData {
    public static final int TYPE_IMG = 3;
    public static final int TYPE_SUMMARY = 2;
    public static final int TYPE_TEXT = 1;
    private String _mContentType = null;
    private long _mCreated = -1L;
    private String _mDescription = null;
    private int _mId;
    private String _mImageThumbnail = null;
    private int _mStatusCode = -1;
    private String _mText = null;
    private String _mTitle = null;
    private int _mType = -1;
    private long _mUpdated = -1L;
    private String _mUri = null;
    private String _mUrl = null;

    public String getContentType()
    {
        return this._mContentType;
    }

    public long getCreated()
    {
        return this._mCreated;
    }

    public String getDescription()
    {
        return this._mDescription;
    }

    public int getId()
    {
        return this._mId;
    }

    public String getImageThumbnail()
    {
        return this._mImageThumbnail;
    }

    public int getStatusCode()
    {
        return this._mStatusCode;
    }

    public String getText()
    {
        return this._mText;
    }

    public String getTitle()
    {
        return this._mTitle;
    }

    public int getType()
    {
        return this._mType;
    }

    public long getUpdated()
    {
        return this._mUpdated;
    }

    public String getUri()
    {
        return this._mUri;
    }

    public String getUrl()
    {
        return this._mUrl;
    }

    public void setContentType(String paramString)
    {
        this._mContentType = paramString;
    }

    public void setCreated(long paramLong)
    {
        this._mCreated = paramLong;
    }

    public void setDescription(String paramString)
    {
        this._mDescription = paramString;
    }

    public void setId(int paramInt)
    {
        this._mId = paramInt;
    }

    public void setImageThumbnail(String paramString)
    {
        this._mImageThumbnail = paramString;
    }

    public void setStatusCode(int paramInt)
    {
        this._mStatusCode = paramInt;
    }

    public void setText(String paramString)
    {
        this._mText = paramString;
    }

    public void setTitle(String paramString)
    {
        this._mTitle = paramString;
    }

    public void setType(int paramInt)
    {
        this._mType = paramInt;
    }

    public void setUpdated(long paramLong)
    {
        this._mUpdated = paramLong;
    }

    public void setUri(String paramString)
    {
        this._mUri = paramString;
    }

    public void setUrl(String paramString)
    {
        this._mUrl = paramString;
    }

    public String toString()
    {
        return "";
    }
}
