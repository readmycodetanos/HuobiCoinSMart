package com.readmycodetanos.upbittrade.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.starstudio.frame.base.BaseActivityAbstract;
import com.starstudio.frame.net.OkGo;
import com.starstudio.frame.net.request.base.RequestAbstract;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hongsec on 2016-09-05.
 */
public abstract class BaseAct extends BaseActivityAbstract {

    protected final long DeLAYUI = 500L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public AppController getAppController(){
        return (AppController) getApplication();
    }
    private Map<String,RequestAbstract> requestList=new HashMap<>();

    public void putApiStack(String key ,RequestAbstract abstractRequest){
        if(!TextUtils.isEmpty(key) &&abstractRequest !=null && abstractRequest.getClass()!=null){
            if(requestList.containsKey(key)){
                RequestAbstract requestAbstract = requestList.get(abstractRequest.getClass().getSimpleName());
                if(requestAbstract!=null){
                    OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(),requestAbstract);
                }
            }
            requestList.put(key, abstractRequest);
        }
    }

    protected ProgressDialog progressDialog2 ;

    public void setMessgeDialogCancleable(boolean cancleable){
        if( isFinishing()) {
            return;
        }

        try {
            if(progressDialog2==null){
                progressDialog2 = new ProgressDialog(this);
                progressDialog2.setCancelable(cancleable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showMessageLoading(String message){
        if( isFinishing()) {
            return;
        }

        try {
            if(progressDialog2==null){
                progressDialog2 = new ProgressDialog(this);
                progressDialog2.setCancelable(false);
            }
            progressDialog2.setMessage(message);
            if(!progressDialog2.isShowing()){
                progressDialog2.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void cancleMessageLoading(){
        if( isFinishing()) {
            return;
        }

        try {
            if(progressDialog2==null){
                progressDialog2 = new ProgressDialog(this);
            }
            if(progressDialog2.isShowing()){
                progressDialog2.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Set<String> strings = requestList.keySet();
        for(String request: strings){
            RequestAbstract abstractRequest1 = requestList.get(request);
            if(abstractRequest1 !=null){
                OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), abstractRequest1);
            }
        }
    }

}
