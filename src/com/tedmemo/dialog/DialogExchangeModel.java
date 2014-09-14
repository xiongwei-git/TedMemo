package com.tedmemo.dialog;

import android.view.Gravity;

import java.io.Serializable;

public class DialogExchangeModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3685432164096360692L;
	public DialogExchangeModelBuilder dialogExchangeModelBuilder;

	public DialogExchangeModel(DialogExchangeModelBuilder dialogExchangeModelBuilder) {
		this.dialogExchangeModelBuilder = dialogExchangeModelBuilder;
	}

	public DialogType getDialogType() {
		return dialogExchangeModelBuilder.dialogType;
	}

	public String getDialogContext() {
		return dialogExchangeModelBuilder.dialogContext;
	}

	public String getPostiveText() {
		return dialogExchangeModelBuilder.postiveText;
	}

	public String getNegativeText() {
		return dialogExchangeModelBuilder.negativeText;
	}

	public String getTag() {
		return dialogExchangeModelBuilder.tag;
	}

	public String getSingleText() {
		return dialogExchangeModelBuilder.singleText;
	}

	public boolean isBackable() {
		return dialogExchangeModelBuilder.isBackable;
	}

	public boolean isSpaceable() {
		return dialogExchangeModelBuilder.isSpaceable;
	}

	public int getGravity() {
		return dialogExchangeModelBuilder.gravity;
	}

	public static class DialogExchangeModelBuilder implements Serializable {
		/**
         * 
         */
		private static final long serialVersionUID = -3685432164096360693L;
		/**
		 * 弹出框类型
		 */
		private DialogType dialogType = DialogType.SINGLE;
		/**
		 * 弹出框内容
		 */
		private String dialogContext = "";
		/**
		 * 确认按键
		 */
		private String postiveText = "OK";
		/**
		 * 取消按键
		 */
		private String negativeText = "Cancel";
		/**
		 * 单按键
		 */
		private String singleText = "";
		/**
		 * tag
		 */
		private String tag = "";
		/**
		 * back可点（默认可点）
		 */
		private boolean isBackable = true;
		/**
		 * 空白可点（默认可点）
		 */
		private boolean isSpaceable = true;

		private int gravity = Gravity.CENTER;

		public DialogExchangeModelBuilder(DialogType dialogType, String tag) {
			this.dialogType = dialogType;
			this.tag = tag;
		}

		public DialogExchangeModelBuilder setTag(String tag) {
			this.tag = tag;
			return this;
		}

		public DialogExchangeModelBuilder setDialogContext(String dialogContext) {
			this.dialogContext = dialogContext;
			return this;
		}

		public DialogExchangeModelBuilder setPostiveText(String postiveText) {
			this.postiveText = postiveText;
			return this;
		}

		public DialogExchangeModelBuilder setNegativeText(String negativeText) {
			this.negativeText = negativeText;
			return this;
		}

		public DialogExchangeModelBuilder setSingleText(String singleText) {
			this.singleText = singleText;
			return this;
		}

		public DialogExchangeModelBuilder setBackable(boolean isBackable) {
			this.isBackable = isBackable;
			return this;
		}

		public DialogExchangeModelBuilder setSpaceable(boolean isSpaceable) {
			this.isSpaceable = isSpaceable;
			return this;
		}

		public DialogExchangeModelBuilder setGravity(int gravity) {
			this.gravity = gravity;
			return this;
		}

		public DialogExchangeModel creat() {
			return new DialogExchangeModel(this);
		}
	}
}
