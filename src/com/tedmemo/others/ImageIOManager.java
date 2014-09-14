package com.tedmemo.others;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

/**
 * Created by w_xiong on 2014/9/15.
 */
public class ImageIOManager {
    /** 从给定路径加载图片*/
    public static Bitmap loadBitmap(String imgpath,BitmapFactory.Options options) {
        return BitmapFactory.decodeFile(imgpath,options);
    }


    /** 从给定的路径加载图片，并指定是否自动旋转方向*/
    public static Bitmap loadBitmap(String imgpath, boolean adjustOritation, BitmapFactory.Options options) {
        if (!adjustOritation) {
            return loadBitmap(imgpath,options);
        } else {
            Bitmap bm = loadBitmap(imgpath,options);
            int digree = 0;
            ExifInterface exif;
            try {
                exif = new ExifInterface(imgpath);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;
                }
            }
            if (digree != 0) {
                Matrix m = new Matrix();
                m.postRotate(digree);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
            }
            return bm;
        }
    }

    /***
     * 带有缩放比例的加载图片，默认自动旋转
     * @param imgpath 路径
     * @param whRatio 屏幕宽高比例
     * @return
     */
    public static Bitmap loadBitmap(String imgpath, float whRatio) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgpath,options);
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        int be = 1;
        float hh = 300f;
        float ww = hh*whRatio;
        if (w > h && w > ww) {
            be = (int) (w / ww);
        } else if (w < h && h > hh) {
            be = (int) (h / hh);
        }
        if (be <= 0){
            be = 1;
        }
        options.inSampleSize = be;
        return loadBitmap(imgpath,true,options);
    }
}
