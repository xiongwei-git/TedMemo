package com.tedmemo.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.TedFramework.Fragment.TFragment;
import com.android.TedFramework.util.*;
import com.tedmemo.activity.MainActivity;
import com.tedmemo.data.IconDataManager;
import com.tedmemo.db.IconBgData;
import com.tedmemo.db.MemoItemInfo;
import com.tedmemo.dialog.DialogExchangeModel;
import com.tedmemo.dialog.DialogType;
import com.tedmemo.dialog.ExcuteDialogFragmentCallBack;
import com.tedmemo.dialog.TDialogManager;
import com.tedmemo.event.SelectIconEvent;
import com.tedmemo.others.Constants;
import com.tedmemo.others.ImageIOManager;
import com.tedmemo.view.ImageEditText;
import com.tedmemo.view.R;
import com.tedmemo.view.manager.TInputMethodManager;
import de.greenrobot.event.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by w_xiong on 2014/9/11.
 */
public class EditMemoFragment extends TFragment implements View.OnClickListener , ExcuteDialogFragmentCallBack{
    private ImageEditText mImageEditText;
    /**当前手机的宽高比例*/
    private float mWHRatio = 1080/1920;
    private String mPhotoPath;
    /**标签的icon*/
    private ImageView mMemoIcon;
    /**编辑页面对应的icon*/
    private MemoItemInfo mMemo;


//    private static WriteMemoFragment self = null;
//    public static WriteMemoFragment getInstance(){
//        if(null == self){
//            self = new WriteMemoFragment();
//        }
//        return self;
//    }

    public EditMemoFragment(){
        mWHRatio = DeviceUtil.getScreenSize()[0]/DeviceUtil.getScreenSize()[0];
    }

    @Override
    public void onPositiveBtnClick(String tag) {
        if(!CheckDoubleClick.isFastDoubleClick()){
            if("LEAVE".equalsIgnoreCase(tag)){
                getActivity().onBackPressed();
            }
        }
    }

    @Override
    public void onNegtiveBtnClick(String tag) {
        if(!CheckDoubleClick.isFastDoubleClick()){

        }
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if(null != getArguments()){
            Bundle ars = getArguments();
            mMemo = (MemoItemInfo)ars.getSerializable("MEMO");
            mMemo.set_mIconId(Constants.DEFAULT_NONE_ICON_ID);
        }else {
            mMemo = new MemoItemInfo();
            mMemo.set_mIconId(Constants.DEFAULT_NONE_ICON_ID);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setMemoIcon();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public boolean checkCancel(){
        String jsonStr = mImageEditText.getJSONObject();
        boolean isHaveMemo = false;
        try {
            JSONArray jsonArray = (new JSONObject(jsonStr)).getJSONArray("memo");
            isHaveMemo = jsonArray.length() > 0;
        }catch (JSONException localJSONException) {

        }
        if(isHaveMemo){
            DialogExchangeModel.DialogExchangeModelBuilder builder = new DialogExchangeModel.DialogExchangeModelBuilder(DialogType.EXCUTE,"LEAVE");
            builder.setBackable(true).setDialogContext(getTString(R.string.confirm_leave));
            TDialogManager.showDialogFragment(getFragmentManager(),builder.creat(),this);
            return false;
        }else {
            return true;
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
        mMemoIcon = (ImageView)rootView.findViewById(R.id.tagButtonImg);

        mImageEditText =(ImageEditText)rootView.findViewById(R.id.imageEditText);
    }

    private void setMemoIcon(){
        int iconID = mMemo.get_mIconId();
        List<IconBgData> listIcon = IconDataManager.getInstance().getmAllIconBgData();
        for (IconBgData iconBg:listIcon){
            if(iconBg.get_id() == iconID){
                mMemoIcon.setImageDrawable(iconBg.getDrawable(getActivity()));
                break;
            }
        }
    }

    private void saveNewMemo(){
        String jsonStr = mImageEditText.getJSONObject();
    }

    /***
     * 选择了icon
     * @param event
     */
    public void onEventMainThread(SelectIconEvent event) {
        mMemo.set_mIconId(event.getIconId());
        setMemoIcon();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    private void selectIcon(){
        IconSelectFrament iconSelectFrament = new IconSelectFrament();
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("CREATE_MEMO",true);
//        iconSelectFrament.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
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
