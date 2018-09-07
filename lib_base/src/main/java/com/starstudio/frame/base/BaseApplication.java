package com.starstudio.frame.base;

import android.support.multidex.MultiDexApplication;


/**
 * Created by Hongsec on 2016-07-21.
 */
public class BaseApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

    }

//    public boolean isMainProcess() {
//        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
//        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//        String mainProcessName = getPackageName();
//        int myPid = android.os.Process.myPid();
//        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
//            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }

}
