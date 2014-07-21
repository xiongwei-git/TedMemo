package com.tedmemo.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.tedmemo.base.TMApplication;

/**
 * Created by Ted on 14-7-21.
 */
public class DeviceUtil {
    /**
     * 获取屏幕的宽高
     *
     * @param dm
     *            设备显示对象描述
     * @return int数组, int[0] - width, int[1] - height
     */
    public static int[] getScreenSize(DisplayMetrics dm) {
        int[] result = new int[2];
        result[0] = dm.widthPixels;
        result[1] = dm.heightPixels;
        return result;
    }
    /**
     * Dip转换为实际屏幕的像素值
     *
     * @param dm
     *            设备显示对象描述
     * @param dip
     *            dip值
     * @return 匹配当前屏幕的像素值
     */
    public static int getPixelFromDip(DisplayMetrics dm, float dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm) + 0.5f);
    }
    /**
     * 判断当前设备的数据服务是否有效
     *
     * @return true - 有效，false - 无效
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectMgr == null) {
            return false;
        }

        NetworkInfo nwInfo = connectMgr.getActiveNetworkInfo();
        if (nwInfo == null || !nwInfo.isAvailable()) {
            return false;
        }
        return true;
    }
    /**Dip转换为实际屏幕的像素值*/
    public static int getPixelFromDip(float f) {
        return getPixelFromDip(TMApplication.getInstance().getResources().getDisplayMetrics(),f);
    }
}
