package com.readmycodetanos.upbittrade.config;

import android.Manifest;
import android.content.Context;

import com.starstudio.frame.base.PreferenceUtil;


/**
 * Created by Hongsec on 2016-09-05.
 */
public class Cconfig {
    private static Cconfig cconfig =null;

    public static Cconfig getInstance() {
        if(cconfig ==null){
            synchronized (Cconfig.class){
                if(cconfig ==null){
                    cconfig = new Cconfig();
                }
            }
        }
        return cconfig;
    }

    //TODOC
    public   boolean isDebug = true;

    public   String baseUrl = "";
    public   String baseWebUrl ="";

    public  void init(Context context){
        isDebug = PreferenceUtil.getInstance(context).getValue(Cstatic.SP_SERVER, Cconfig.getInstance().isDebug);
//        if(isDebug){
//            baseWebUrl = CommonApi.DEV_WEB_URL;
//        }else{
//            baseWebUrl = CommonApi.REAL_WEB_URL;
//        }

    }


    //필수　필요권한들
    public String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.GET_ACCOUNTS
    };
}
