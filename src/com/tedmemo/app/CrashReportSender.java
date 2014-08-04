package com.tedmemo.app;

import android.content.Context;
import org.acra.collector.CrashReportData;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

/**
 * Created by w_xiong on 2014/8/2.
 */
public class CrashReportSender implements ReportSender {
    private Context mContext;

    public CrashReportSender(Context context){
        this.mContext = context;
    }

    @Override
    public void send(CrashReportData crashReportData) throws ReportSenderException {
        sendMailReport(crashReportData);
    }
    private void sendMailReport(CrashReportData errorContent) throws ReportSenderException {
        new EmailIntentSender(mContext).send(errorContent);
    }
}
