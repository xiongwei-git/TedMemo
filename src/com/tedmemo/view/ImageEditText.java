package com.tedmemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;
import com.android.TedFramework.util.StringUtil;
import com.tedmemo.data.IncludedUrlInfo;
import com.tedmemo.others.TComparator;
import com.tedmemo.others.TImageGetter;
import com.tedmemo.others.URLAnalysis;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by Ted on 2014/9/10.
 */
public class ImageEditText extends EditText {

    public static final String mLength = String.valueOf(65532);

    private Map<String, String> mMap = null;
    private Context mContext = null;
    private JSONArray mJsonArray = null;
    private int mNum = 0;
    private String mString = "";

    public ImageEditText(Context context) {
        super(context);
        this.mContext = context;
        this.mMap = new HashMap();
    }

    public ImageEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mMap = new HashMap();
    }

    public ImageEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        this.mMap = new HashMap();

    }

    private void analysisString(String string, int index) {
        List localList = URLAnalysis.getUrl(string);
        if (localList.size() == 0) {
            if (!StringUtil.emptyOrNull(string)) {
                addTextObject(string);
            }
            return;
        }
        int i = this.mNum;
        Iterator localIterator = localList.iterator();
        int j = 0;
        while (localIterator.hasNext()){
            IncludedUrlInfo includedUrlInfo = (IncludedUrlInfo) localIterator.next();
            if (this.mNum < i + includedUrlInfo.getStartPosition()){
                addTextObject(this.mString.substring(this.mNum, i + includedUrlInfo.getStartPosition()));
            }
            addUrlObject(includedUrlInfo.getUrl());
            if(j == localList.size()-1){
                if(i + includedUrlInfo.getEndPosition() < index){
                    String str = this.mString.substring(this.mNum, index);
                    if(!StringUtil.emptyOrNull(str)){
                        addTextObject(str);
                    }
                }
            }
            j++;
        }
    }

    private void addTextObject(String text) {
        JSONObject localJSONObject = new JSONObject();
        try {
            localJSONObject.put("type", 1);
            localJSONObject.put("text", text);
            this.mJsonArray.put(localJSONObject);
            this.mNum += text.length();
        } catch (JSONException localJSONException) {
            //k.a(localJSONException);
        }
    }

    private void addUrlObject(String url) {
        JSONObject localJSONObject = new JSONObject();
        try {
            localJSONObject.put("type", 2);
            localJSONObject.put("url", url);
            localJSONObject.put("description", "");
            localJSONObject.put("title", "");
            localJSONObject.put("image", "");
            localJSONObject.put("contentType", "");
            localJSONObject.put("created", -1);
            localJSONObject.put("updated", -1);
            localJSONObject.put("status", -1);
            this.mJsonArray.put(localJSONObject);
            this.mNum += url.length();
        } catch (JSONException localJSONException) {
            //k.a(localJSONException);
        }
    }

    private void addImageObject(String imgUri) {
        JSONObject localJSONObject = new JSONObject();
        try {
            localJSONObject.put("type", 3);
            localJSONObject.put("uri", imgUri);
            this.mJsonArray.put(localJSONObject);
            this.mNum = (1 + this.mNum);
        } catch (JSONException localJSONException) {
            //k.a(localJSONException);
        }
    }

    public void insertImage(Bitmap paramBitmap, String imageUri) {
        if (paramBitmap != null){
            BitmapDrawable localBitmapDrawable = new BitmapDrawable(this.mContext.getResources(), paramBitmap);
            this.mMap.put(localBitmapDrawable.toString(), imageUri);
            addImgToEdit(localBitmapDrawable);
        }
    }

    public void addImgToEdit(Drawable paramDrawable) {
        TImageGetter locale = new TImageGetter(this, paramDrawable);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("<img src=\"");
        localStringBuilder.append(paramDrawable.toString());
        localStringBuilder.append("\" />");
        addSpanned(Html.fromHtml(localStringBuilder.toString(), locale, null));
    }

    public void addSpanned(Spanned paramSpanned) {
        int i = getSelectionStart();
        int j = getSelectionEnd();
        getText().replace(Math.min(i, j), Math.max(i, j), paramSpanned, 0, paramSpanned.length());
    }

    public void insertString(String paramString) {
        int i = getSelectionStart();
        int j = getSelectionEnd();
        getText().replace(Math.min(i, j), Math.max(i, j), paramString, 0, paramString.length());
    }

    public int getImageSpanCount() {
        Editable localEditable = getText();
        this.mString = localEditable.toString();
        return Arrays.asList((ImageSpan[]) localEditable.getSpans(0, this.mString.length(), ImageSpan.class)).size();
    }

    public String getJSONObject() {
        int i = 0;
        JSONObject localJSONObject = new JSONObject();
        Editable localEditable = getText();
        this.mJsonArray = new JSONArray();
        this.mString = localEditable.toString();
        this.mNum = 0;

        List localList = Arrays.asList((ImageSpan[]) localEditable.getSpans(0, this.mString.length(), ImageSpan.class));

        Collections.sort(localList, new TComparator(this, localEditable));

        try {
            if (localList.size() == 0) {
                String str = this.mString.substring(0, this.mString.length());
                analysisString(str, str.length());
                localJSONObject.put("memo", this.mJsonArray);
                return localJSONObject.toString();
            }else {
                Iterator localIterator = localList.iterator();
                while (localIterator.hasNext()){
                    ImageSpan localImageSpan = (ImageSpan) localIterator.next();
                    int j = localEditable.getSpanStart(localImageSpan);
                    int k = localEditable.getSpanEnd(localImageSpan);
                    /**图片之前有字符串*/
                    if (this.mNum < j) {
                        analysisString(this.mString.substring(this.mNum, j), j);
                    }
                    addImageObject(this.mMap.get(localImageSpan.getSource()));
                    if(i == localList.size()-1){
                        analysisString(this.mString.substring(k, this.mString.length()), this.mString.length());
                    }
                    i++;
                }
                localJSONObject.put("memo", this.mJsonArray);
                return localJSONObject.toString();
            }

        }catch (JSONException localJSONException) {
            //k.a(localJSONException);
            return null;
        }

//        while (true) {
//            try {
//                Iterator localIterator = localList.iterator();
//                if (!localIterator.hasNext()) {
//                    localJSONObject.put("memo", this.mJsonArray);
//                    return localJSONObject.toString();
//                }
//
//                ImageSpan localImageSpan = (ImageSpan) localIterator.next();
//                int j = localEditable.getSpanStart(localImageSpan);
//                int k = localEditable.getSpanEnd(localImageSpan);
//                if (this.mNum < j) {
//                    analysisString(this.mString.substring(this.mNum, j), j);
//                    addImageObject(this.mMap.get(localImageSpan.getSource()));
//                }
//                do {
//                    if (i != -1 + localList.size())
//                        break;
//                    analysisString(this.mString.substring(k, this.mString.length()), this.mString.length());
//                    break;
//                }
//                while (this.mNum != j);
//                addImageObject(this.mMap.get(localImageSpan.getSource()));
//            } catch (JSONException localJSONException) {
//                //k.a(localJSONException);
//                return null;
//            }
//            ++i;
//        }
    }

    public void setJson(String jsonString) {
        setText("");
        try {
            JSONObject localJSONObject;
            int j;
            JSONArray localJSONArray = new JSONObject(jsonString).getJSONArray("memo");
            if (null != localJSONArray && localJSONArray.length() > 0) {
                int count = localJSONArray.length();
                for (int i = 0; i < count; i++) {
                    localJSONObject = localJSONArray.getJSONObject(i);
                    j = Integer.parseInt(localJSONObject.get("type").toString());
                    switch (j) {
                        case 1:
                            String str1 = localJSONObject.get("text").toString();
                            if (!TextUtils.isEmpty(str1)) {
                                insertString(str1);
                            }
                            break;
                        case 2:
                            insertString(localJSONObject.get("url").toString());
                            break;
                        case 3:
//                            String str2 = localJSONObject.get("uri").toString();
//                            DisplayMetrics localDisplayMetrics = ((TActivity)mContext).getDM(this.mContext);
//                            insertImage(b.a(this.mContext, Uri.parse(str2), (int)(0.3F * localDisplayMetrics.widthPixels), (int)(0.3F * localDisplayMetrics.heightPixels)), str2);
                            break;
                        default:
                            break;
                    }
                }
            }

        } catch (JSONException localJSONException) {

        }
    }


}
