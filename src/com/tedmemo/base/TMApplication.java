package com.tedmemo.base;

import android.app.Application;
import com.crittercism.app.Crittercism;
import com.tedmemo.app.CrashReportSender;
import com.tedmemo.view.R;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "", // will not be used
        mailTo = "315981826@qq.com",
        customReportContent = {
                ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
                ReportField.CUSTOM_DATA, ReportField.STACK_TRACE,
                ReportField.LOGCAT
        },
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class TMApplication extends Application {
	private static TMApplication self = new TMApplication();
	public static TMApplication getInstance() {
        if(null==self){
            self = new TMApplication();
        }
        return self;
	}
	@Override
	public void onCreate() {
        super.onCreate();
//        ACRA.init(this);
//        CrashReportSender crashReportSender = new CrashReportSender(this);
//        ACRA.getErrorReporter().setReportSender(crashReportSender);
	}
}
