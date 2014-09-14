package com.tedmemo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.*;
import com.tedmemo.activity.MainActivity;
import com.tedmemo.dialog.DialogExchangeModel;
import com.tedmemo.dialog.DialogType;
import com.tedmemo.dialog.TDialogManager;
import com.tedmemo.others.Constants;
import com.tedmemo.others.ImageIOManager;
import com.tedmemo.view.ImageEditText;
import com.tedmemo.view.R;
import com.tedmemo.view.manager.TInputMethodManager;

import java.io.File;
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
                    if(checkCancel()){
                        getActivity().onBackPressed();
                    }
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

    public boolean checkCancel(){
        String jsonStr = mImageEditText.getJSONObject();
        if(StringUtil.emptyOrNull(jsonStr)){
            return true;
        }else {
            DialogExchangeModel.DialogExchangeModelBuilder builder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.EXCUTE,"LEAVE");
            builder.setBackable(true).setDialogContext(getTString(R.string.confirm_leave));
            TDialogManager.showDialogFragment(getFragmentManager(),builder.creat(),this);
            return false;
        }
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
        Bitmap bm = ImageIOManager.loadBitmap(imgUrl,mWHRatio);
        if(StringUtil.emptyOrNull(imgUrl) || null == bm){
            return;
        }
        mImageEditText.insertImage(bm,imgUrl);
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



    private void selectIcon(){
        IconSelectFrament iconSelectFrament = IconSelectFrament.getInstance();
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
