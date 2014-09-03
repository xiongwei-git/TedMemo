package com.tedmemo.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tedmemo.view.R;

public class SingleInfoDialogFragment extends BaseDialogFragment {

	private TextView mDlgContent, mDlgButton;

	public static SingleInfoDialogFragment getInstance(Bundle bundle) {
		SingleInfoDialogFragment baseDialogFragment = new SingleInfoDialogFragment();
		baseDialogFragment.setArguments(bundle);
		return baseDialogFragment;
	}

	public SingleInfoDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			Bundle bundle = getArguments();
			DialogExchangeModel ctripDialogExchangeModel = ((DialogExchangeModel.DialogExchangeModelBuilder) bundle
					.getSerializable(TAG)).creat();
			if (ctripDialogExchangeModel != null) {
				mDialogTag = ctripDialogExchangeModel.getTag();
				mSingleBtnTxt = ctripDialogExchangeModel.getSingleText();
				mContentTxt = ctripDialogExchangeModel.getDialogContext();
				gravity = ctripDialogExchangeModel.getGravity();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.dialog_error_layout, container, false);
		contentView.setOnClickListener(mSpaceClickListener);

		mDlgContent = (TextView) contentView.findViewById(R.id.content_text);
		if (!TextUtils.isEmpty(mContentTxt)) {
			mDlgContent.setText(mContentTxt);
			mDlgContent.setGravity(gravity);
		}

		mDlgButton = (TextView) contentView.findViewById(R.id.single_btn);
		mDlgButton.setClickable(true);
		if (!TextUtils.isEmpty(mSingleBtnTxt)) {
			mDlgButton.setText(mSingleBtnTxt);
		}

		mDlgButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Fragment tarFragment = getTargetFragment();
				Activity activity = getActivity();
				dismissSelf();
				if (tarFragment != null && tarFragment instanceof SingleDialogFragmentCallBack) {
					((SingleDialogFragmentCallBack) tarFragment).onSingleBtnClick(mDialogTag);
				} else if (activity != null && activity instanceof SingleDialogFragmentCallBack) {
					((SingleDialogFragmentCallBack) activity).onSingleBtnClick(mDialogTag);
				}
			}
		});
		return contentView;
	}
}
