package com.starstudio.frame.base.util;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.starstudio.frame.base.BuildConfig;


/**
 * Created by Hongsec on 2016-07-21.
 */
public class UtilsLog {
    public final String TAG = UtilsLog.class.getSimpleName();
    /**
     * 控制变量，是否显示log日志
     */
//    public  boolean isShowLog = false;
    /**
     * 默认打印信息
     */
    private String defaultMsg = "";
    private final int V = 1;
    private final int D = 2;
    private final int I = 3;
    private final int W = 4;
    private final int E = 5;


    public void isMainThread() {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            v("thread", "it's main UI thread");
        } else {
            v("thread", "it's thread");
        }
    }

    private static UtilsLog utilsLog;

    public static UtilsLog getInstance() {

        if (utilsLog == null) {
            synchronized (UtilsLog.class) {
                if (utilsLog == null) {
                    utilsLog = new UtilsLog();
                }
                return utilsLog;
            }

        }
        return utilsLog;
    }


    public void v() {
        llog(V, null, defaultMsg);
    }

    public void v(Object obj) {
        llog(V, null, obj);
    }

    public void v(String tag, Object obj) {
        llog(V, tag, obj);
    }

    public void d() {
        llog(D, null, defaultMsg);
    }

    public void d(Object obj) {
        llog(D, null, obj);
    }

    public void d(String tag, Object obj) {
        llog(D, tag, obj);
    }

    public void i() {
        llog(I, null, defaultMsg);
    }

    public void i(Object obj) {
        llog(I, null, obj);
    }

    public void i(String tag, String obj) {
        llog(I, tag, obj);
    }

    public void w() {
        llog(W, null, defaultMsg);
    }

    public void w(Object obj) {
        llog(W, null, obj);
    }

    public void w(String tag, Object obj) {
        llog(W, tag, obj);
    }

    public void e() {
        llog(E, null, defaultMsg);
    }

    public void e(Object obj) {
        llog(E, null, obj);
    }

    public void e(String tag, Object obj) {
        llog(E, tag, obj);
    }


    /**
     * 执行打印方法
     *
     * @param type
     * @param tagStr
     * @param obj
     */
    public void llog(int type, String tagStr, Object obj) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        String msg;

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        int index = 4;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();

        String tag = (tagStr == null ? className : tagStr);
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        StringBuilder stringBuilder = new StringBuilder();
        String threa;
        if (Looper.myLooper() == Looper.getMainLooper()) {
            threa = "UI thread";
        } else {
            threa = "thread";
        }
        stringBuilder.append("[ (").append(className).append(":").append(lineNumber).append(")#").append(methodName).append(" ]["+threa+"]");

        if (obj == null) {
            msg = "Log with null Object";
        } else {
            msg = obj.toString();
        }
        if (msg != null) {
            stringBuilder.append(msg);
        }

        String logStr = stringBuilder.toString();

        switch (type) {
            case V:
                Log.v(tag, logStr);
                break;
            case D:
                Log.d(tag, logStr);
                break;
            case I:
                Log.i(tag, logStr);
                break;
            case W:
                Log.w(tag, logStr);
                break;
            case E:
                Log.e(tag, logStr);
                break;
            default:
                Log.v(tag, logStr);
                break;
        }
    }


    public void showExtras(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (BuildConfig.DEBUG) {
            for (String key : bundle.keySet()) {
                UtilsLog.getInstance().d("Extra key=" + key + ", value=" + bundle.get(key));
            }
        }
    }

}
