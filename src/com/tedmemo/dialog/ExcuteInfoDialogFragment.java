package com.tedmemo.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.TedFramework.util.StringUtil;
import com.tedmemo.view.R;

public class ExcuteInfoDialogFragment extends BaseDialogFragment {

	private TextView mDlgContent, mBtnLeft, mRightBtn;
	private OnClickListener mExcuitePositiveListener, mExcuiteNegativeListener;

	public static ExcuteInfoDialogFragment getInstance(Bundle bundle) {
		ExcuteInfoDialogFragment baseDialogFragment = new ExcuteInfoDialogFragment();
		baseDialogFragment.setArguments(bundle);
		return baseDialogFragment;
	}

	public ExcuteInfoDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			Bundle bundle = getArguments();
			DialogExchangeModel ctripDialogExchangeModel = ((DialogExchangeModel.DialogExchangeModelBuilder) bundle.getSerializable(TAG))
					.creat();
			if (ctripDialogExchangeModel != null) {
				mDialogTag = ctripDialogExchangeModel.getTag();
				mPositiveBtnTxt = ctripDialogExchangeModel.getPostiveText();
				mNegativeBtnTxt = ctripDialogExchangeModel.getNegativeText();
				mContentTxt = ctripDialogExchangeModel.getDialogContext();
				gravity = ctripDialogExchangeModel.getGravity();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_excute_layout, container, false);
		view.setOnClickListener(mSpaceClickListener);
		mDlgContent = (TextView) view.findViewById(R.id.content_text);
		if (!StringUtil.emptyOrNull(mContentTxt)) {
			mDlgContent.setText(mContentTxt);
			if (gravity != -1) {
				mDlgContent.setGravity(gravity);
			}
			if (getActivity() != null && mContentTxt.contains("确认退出")) {
				mDlgContent.setTextAppearance(getActivity(), R.style.text_22_666666_sdw);
			}
		}
		mBtnLeft = (TextView) view.findViewById(R.id.lef_btn);
		mRightBtn = (TextView) view.findViewById(R.id.right_btn);

		mExcuitePositiveListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment tarFragment = getTargetFragment();
				Activity activity = getActivity();
				dismissSelf();
				if (tarFragment != null && tarFragment instanceof ExcuteDialogFragmentCallBack) {
					((ExcuteDialogFragmentCallBack) tarFragment).onPositiveBtnClick(mDialogTag);
				} else if (activity != null && activity instanceof ExcuteDialogFragmentCallBack) {
					((ExcuteDialogFragmentCallBack) activity).onPositiveBtnClick(mDialogTag);
				}
			}
		};

		mExcuiteNegativeListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment tarFragment = getTargetFragment();
				Activity activity = getActivity();
				dismissSelf();
				if (tarFragment != null && tarFragment instanceof ExcuteDialogFragmentCallBack) {
					((ExcuteDialogFragmentCallBack) tarFragment).onNegtiveBtnClick(mDialogTag);
				} else if (activity != null && activity instanceof ExcuteDialogFragmentCallBack) {
					((ExcuteDialogFragmentCallBack) activity).onNegtiveBtnClick(mDialogTag);
				}
			}
		};

		if (!StringUtil.emptyOrNull(mPositiveBtnTxt)) {
			mRightBtn.setText(mPositiveBtnTxt);
		} else {
			mRightBtn.setText(R.string.ok);
		}
		mRightBtn.setOnClickListener(mExcuitePositiveListener);
		//mRightBtn.setBackgroundResource(R.drawable.btn_dialog_selector);

		if (!StringUtil.emptyOrNull(mNegativeBtnTxt)) {
			mBtnLeft.setText(mNegativeBtnTxt);
		} else {
			mBtnLeft.setText(R.string.cancel);
		}
		mBtnLeft.setOnClickListener(mExcuiteNegativeListener);
		//mBtnLeft.setBackgroundResource(R.drawable.btn_dialog_selector);
		return view;
	}
}
