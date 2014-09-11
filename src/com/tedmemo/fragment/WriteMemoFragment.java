package com.tedmemo.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.*;
import com.tedmemo.activity.MainActivity;
import com.tedmemo.others.Constants;
import com.tedmemo.view.ImageEditText;
import com.tedmemo.view.R;
import com.tedmemo.view.manager.TInputMethodManager;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by w_xiong on 2014/9/11.
 */
public class WriteMemoFragment extends TFragment implements View.OnClickListener{
    private ImageEditText mImageEditText;
    /**当前手机的宽高比例*/
    private float mWHRatio = 1080/1920;
    private String mPhotoPath;


    private static WriteMemoFragment self = null;
    public static WriteMemoFragment getInstance(){
        if(null == self){
            self = new WriteMemoFragment();
        }
        return self;
    }

    public WriteMemoFragment(){
        mWHRatio = DeviceUtil.getScreenSize()[0]/DeviceUtil.getScreenSize()[0];
    }

    @Override
    public void onClick(View v) {
        if(!CheckDoubleClick.isFastDoubleClick()){
            switch (v.getId()){
                case R.id.cancelButton:
                    TInputMethodManager.hideSoftInput(mImageEditText);
                    getActivity().onBackPressed();
                    break;
                case R.id.saveButton:
                    TInputMethodManager.hideSoftInput(mImageEditText);
                    saveNewMemo();
                    break;
                case R.id.galleryButton:
                    TInputMethodManager.hideSoftInput(mImageEditText);
                    callGallery();
                    break;
                case R.id.imageCaptureButton:
                    TInputMethodManager.hideSoftInput(mImageEditText);
                    callCamera();
                    break;
                case R.id.tagButton:
                    TInputMethodManager.hideSoftInput(mImageEditText);
                    selectIcon();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_memo_fragment,null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView){
        rootView.findViewById(R.id.cancelButton).setOnClickListener(this);
        rootView.findViewById(R.id.saveButton).setOnClickListener(this);
        rootView.findViewById(R.id.galleryButton).setOnClickListener(this);
        rootView.findViewById(R.id.imageCaptureButton).setOnClickListener(this);
        rootView.findViewById(R.id.tagButton).setOnClickListener(this);
        mImageEditText =(ImageEditText)rootView.findViewById(R.id.imageEditText);
    }

    private void saveNewMemo(){
        String jsonStr = mImageEditText.getJSONObject();
    }

    public void insertImageToEdit(){
        if(!StringUtil.emptyOrNull(mPhotoPath)){
            File file =  new File(mPhotoPath);
            if(file.exists()){
                insertImageToEdit(mPhotoPath);
            }
        }
    }
    public void insertImageToEdit(String imgUrl){
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgUrl,options);//此时返回bm为空
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        float hh = 250f;//这里设置高度为800f+
        float ww = hh*mWHRatio;//这里设置宽度为480f
        LogUtil.e("w="+w+":::h="+h);
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (w / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (h / hh);
        }
        if (be <= 0){
            be = 1;
        }
        options.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bm = BitmapFactory.decodeFile(imgUrl, options);
        mImageEditText.insertImage(bm,imgUrl);
    }
    private void selectIcon(){
        IconSelectFrament iconSelectFrament = new IconSelectFrament();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.anim_fragment_in, R.anim.anim_fragment_out, R.anim.anim_fragment_close_in, R.anim.anim_fragment_close_out);
        transaction.add(R.id.writeMemoSelectIcon, iconSelectFrament, "SELECT_ICON");
        transaction.addToBackStack("SELECT_ICON");
        transaction.commitAllowingStateLoss();
    }
    private void callGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(galleryIntent, MainActivity.REQUEST_CODE_GALLERY);
    }

    private void callCamera(){
        String photoDirPath = Constants.PHOTO_PATH + File.separator + "Camera";
        if(StringUtil.emptyOrNull(photoDirPath)){
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                LogUtil.e("SD card is not avaiable/writeable right now.");
                ToastUtil.show(getActivity(),"NO SDCARD！");
                return;
            }
        }
        File dir = new File(photoDirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String photoName = "IMG_"+new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        mPhotoPath = photoDirPath +File.separator+ photoName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mPhotoPath)));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        getActivity().startActivityForResult(intent, MainActivity.REQUEST_CODE_CAMERA);
    }
}
