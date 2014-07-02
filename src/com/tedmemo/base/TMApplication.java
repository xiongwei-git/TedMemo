package com.tedmemo.base;

import android.app.Application;

public class TMApplication extends Application {
	private static TMApplication self = new TMApplication();
	public static TMApplication getInstance() {
		return self;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	}
}
