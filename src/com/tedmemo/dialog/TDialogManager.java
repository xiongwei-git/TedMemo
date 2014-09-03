package com.tedmemo.dialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class TDialogManager {
	public final static int DIALOG_REQUEST_CODE = 0x2001;

	/**
	 * 弹框方法
	 * 
	 * @param fragmentManager
	 *            (必传字段)
	 * @param ctripDialogExchangeModel
	 *            (必传字段)
	 * @param fragment
	 *            (选传)
	 * @return CtripBaseDialogFragmentV2对象
	 */
	public static BaseDialogFragment showDialogFragment(FragmentManager fragmentManager, DialogExchangeModel ctripDialogExchangeModel,
			Fragment fragment) {

		BaseDialogFragment baseDialogFragment = null;
		if (ctripDialogExchangeModel != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable(BaseDialogFragment.TAG, ctripDialogExchangeModel.dialogExchangeModelBuilder);
			DialogType ctripHDDialogType = ctripDialogExchangeModel.getDialogType();
			if (ctripHDDialogType == DialogType.SINGLE) {
				baseDialogFragment = SingleInfoDialogFragment.getInstance(bundle);
			} else if (ctripHDDialogType == DialogType.EXCUTE) {
				baseDialogFragment = ExcuteInfoDialogFragment.getInstance(bundle);
			} else if (ctripHDDialogType == DialogType.CUSTOMER) {
				baseDialogFragment = CustomerDialogFragment.getInstance(bundle);
			}
			// else if (ctripHDDialogType == DialogType.PROGRESS) {
			// baseDialogFragment = ProcessDialogFragment.getInstance(bundle);
			// }
		}
		if (baseDialogFragment != null) {
			if (fragment != null) {
				baseDialogFragment.setTargetFragment(fragment, DIALOG_REQUEST_CODE);
			}
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(baseDialogFragment, ctripDialogExchangeModel.getTag());
			ft.commitAllowingStateLoss();
		}
		return baseDialogFragment;
	}
}
