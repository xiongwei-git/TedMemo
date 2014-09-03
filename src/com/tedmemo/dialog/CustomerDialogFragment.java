package com.tedmemo.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.tedmemo.view.R;

public class CustomerDialogFragment extends BaseDialogFragment {
	public static CustomerDialogFragment getInstance(Bundle bundle) {
		CustomerDialogFragment baseDialogFragment = new CustomerDialogFragment();
		baseDialogFragment.setArguments(bundle);
		return baseDialogFragment;
	}

	public CustomerDialogFragment() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			Bundle bundle = getArguments();
			DialogExchangeModel ctripDialogExchangeModel = ((DialogExchangeModel.DialogExchangeModelBuilder) bundle
					.getSerializable(TAG)).creat();
			if (ctripDialogExchangeModel != null) {
				mDialogTag = ctripDialogExchangeModel.getTag();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mCtripContentView = null;
		if (getTargetFragment() != null && getTargetFragment() instanceof CustomerFragmentCallBack) {
			mCtripContentView = ((CustomerFragmentCallBack) getTargetFragment()).getCustomerView(mDialogTag);
		} else if (getActivity() != null && getActivity() instanceof CustomerFragmentCallBack) {
			mCtripContentView = ((CustomerFragmentCallBack) getActivity()).getCustomerView(mDialogTag);
		}
		FrameLayout layout = new FrameLayout(getActivity());
		layout.setClickable(true);
		layout.setOnClickListener(mSpaceClickListener);
		if (mCtripContentView != null && mCtripContentView.getLayoutParams() != null) {
			layout.addView(mCtripContentView, mCtripContentView.getLayoutParams());
			mCtripContentView.setClickable(true);
		}
		return layout;
	}
}
