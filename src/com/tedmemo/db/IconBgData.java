package com.tedmemo.db;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tedmemo.view.R;

/**
 * Created by Ted on 14-8-19.
 */
@DatabaseTable(tableName = "memoinfo")
public class IconBgData {

    private static final String FILE_NAME_LARGE_PREFIX = "large_";
    private static final String FILE_NAME_PREFIX = "tag_";
    private static final String FILE_NAME_SMALL_PREFIX = "small_";
    public static final int ICON_TYPE_LARGE = 1;
    public static final int ICON_TYPE_SMALL = 2;

    @DatabaseField(columnName="color_off")
    private String _mBackgroundColorOffStr;
    @DatabaseField(columnName="color_on")
    private String _mBackgroundColorOnStr;
    @DatabaseField(columnName="order")
    private int _mOrder = -1;
    @DatabaseField(columnName="name", id=true)
    private String _mName;

    private Drawable _mDrawable = null;
    private long _mMemoCount = -1L;
    private int _mResourceId = -1;
    private int _mSizeGroup = -1;

    public String getBackgroundColorOffStr()
    {
        return this._mBackgroundColorOffStr;
    }

    public String getBackgroundColorOnStr()
    {
        return this._mBackgroundColorOnStr;
    }

    public Drawable getBackgroundDrawable(Context paramContext, int iconType)
    {
        Resources localResources = paramContext.getResources();
        GradientDrawable localGradientDrawableOn = (GradientDrawable)localResources.getDrawable(R.drawable.icon_white_stroke_bg);
        localGradientDrawableOn.setColor(Color.parseColor(getBackgroundColorOnStr()));

//        GradientDrawable localGradientDrawableOff = (GradientDrawable)localResources.getDrawable(R.drawable.icon_white_stroke_bg);
//        localGradientDrawableOff.setColor(Color.parseColor(getBackgroundColorOffStr()));
        StateListDrawable localStateListDrawable = new StateListDrawable();
        localStateListDrawable.addState(new int[] {}, localGradientDrawableOn);
        this._mDrawable = new LayerDrawable(new Drawable[] { localStateListDrawable });
        return this._mDrawable;
    }

    public Drawable getDrawable(Context paramContext)
    {
        return getDrawable(paramContext, ICON_TYPE_LARGE);
    }

    /**获取到背景加图片的drawable*/
    public Drawable getMixDrawable(Context paramContext, int iconType)
    {
        Resources localResources = paramContext.getResources();

        GradientDrawable localGradientDrawableOn = ((GradientDrawable)localResources.getDrawable(R.drawable.icon_white_stroke_bg));
        localGradientDrawableOn.setColor(Color.parseColor(getBackgroundColorOnStr()));

//        GradientDrawable localGradientDrawableOff = (GradientDrawable)localResources.getDrawable(R.drawable.icon_bg2);
//        localGradientDrawableOff.setColor(Color.parseColor(getBackgroundColorOffStr()));

        StateListDrawable localStateListDrawable = new StateListDrawable();
        //localStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, localGradientDrawableOff);
        localStateListDrawable.addState(new int[]{}, localGradientDrawableOn);
        int i = getResourceId(paramContext, iconType);
        if (i == 0) {
            i = getResourceId(paramContext);
        }
        this._mDrawable = new LayerDrawable(new Drawable[] { localStateListDrawable, localResources.getDrawable(i) });
        return this._mDrawable;
    }

    public Drawable getDrawable(Context paramContext, int iconType)
    {
        Resources localResources = paramContext.getResources();

        GradientDrawable localGradientDrawableOn = ((GradientDrawable)localResources.getDrawable(R.drawable.icon_bg));
        localGradientDrawableOn.setColor(Color.parseColor(getBackgroundColorOnStr()));

//        GradientDrawable localGradientDrawableOff = (GradientDrawable)localResources.getDrawable(R.drawable.icon_bg2);
//        localGradientDrawableOff.setColor(Color.parseColor(getBackgroundColorOffStr()));

        StateListDrawable localStateListDrawable = new StateListDrawable();
        //localStateListDrawable.addState(new int[]{android.R.attr.state_pressed}, localGradientDrawableOff);
        localStateListDrawable.addState(new int[]{}, localGradientDrawableOn);
        int i = getResourceId(paramContext, iconType);
        if (i == 0) {
            i = getResourceId(paramContext);
        }
        this._mDrawable = new LayerDrawable(new Drawable[] { localStateListDrawable, localResources.getDrawable(i) });
        return this._mDrawable;
    }

    public long getMemoCount(Context paramContext)
    {
        //this._mMemoCount = new o(paramContext).a(this);
        return this._mMemoCount;
    }

    public int getOrder()
    {
        return this._mOrder;
    }

    public int getResourceId(Context paramContext)
    {
        return getResourceId(paramContext, ICON_TYPE_LARGE);
    }

    public int getResourceId(Context paramContext, int iconType)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("tag_");
        if(iconType == ICON_TYPE_LARGE){
            localStringBuilder.append("large_");
        }else {
            localStringBuilder.append("small_");
        }
        localStringBuilder.append(get_mName());
        this._mResourceId = paramContext.getResources().getIdentifier(localStringBuilder.toString(), "drawable", paramContext.getPackageName());
        return this._mResourceId;
    }

    public int getSizeGroup()
    {
        return this._mSizeGroup;
    }

    public String get_mName() {
        return _mName;
    }

    public void set_mName(String _mName) {
        this._mName = _mName;
    }

    public void setBackgroundColorOffStr(String paramString)
    {
        this._mBackgroundColorOffStr = paramString;
    }

    public void setBackgroundColorOnStr(String paramString)
    {
        this._mBackgroundColorOnStr = paramString;
    }

    public void setDrawable(Drawable paramDrawable)
    {
        this._mDrawable = paramDrawable;
    }

    public void setMemoCount(long paramLong)
    {
        this._mMemoCount = paramLong;
    }

    public void setOrder(int paramInt)
    {
        this._mOrder = paramInt;
    }

    public void setResourceId(int paramInt)
    {
        this._mResourceId = paramInt;
    }

    public void setSizeGroup(int paramInt)
    {
        this._mSizeGroup = paramInt;
    }

    public String toString()
    {
        return new StringBuilder().toString();
    }

}
