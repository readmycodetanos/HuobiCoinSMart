package com.hongsec.coin.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starstudio.frame.base.BaseLazyFragmentAbstract;
import com.starstudio.frame.base.util.UtilsDensity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by hongseok on 2016-10-19.
 */
public abstract class BaseFragAbstract extends BaseLazyFragmentAbstract {

    protected ProgressDialog progressDialog;


    public AppController getAppController(){
        if(getActivity()==null)return null;
        return (AppController)getActivity().getApplication();
    }

    protected Unbinder unbinder;

    public abstract void initView();

    /**
     * 在此初始化ｖｉｅｗ的时候需要使用unbunder来绑定
     * @param savedInstanceState
     */
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(setContentLayoutResID());
        if(unbinder==null){
            unbinder = ButterKnife.bind(this, getContentView());
        }
        initView();
    }



    public void showLoading() {
        if (getBaseParent() == null || getBaseParent().isFinishing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getBaseParent());
        }
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }

    }

    public void cancleLoading() {
        if (getBaseParent() == null || getBaseParent().isFinishing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getBaseParent());
        }
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }

    }

    protected ProgressDialog progressDialog2 ;

    public void showMessageLoading(String message){
        if (getBaseParent() == null || getBaseParent().isFinishing()) {
            return;
        }

        try {
            if(progressDialog2==null){
                progressDialog2 = new ProgressDialog(getBaseParent());
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
        if (getBaseParent() == null || getBaseParent().isFinishing()) {
            return;
        }

        try {
            if(progressDialog2==null){
                progressDialog2 = new ProgressDialog(getBaseParent());
            }
            if(progressDialog2.isShowing()){
                progressDialog2.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public ViewGroup getEmptyLayout() {
        ViewGroup emptyLayout = super.getEmptyLayout();
        TextView textView = new TextView(getBaseApp());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
//        textView.setText(R.string.gr_loading_view);
//        textView.setTextColor(UtilsVersionMC.getColor(getResources(),R.color.txt_dark_default));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,16);
        ProgressBar progressBar = new ProgressBar(getBaseApp());
        progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressBar.setPadding(0,0,0, UtilsDensity.dp2px(getActivity(),16));
        emptyLayout.addView(progressBar);
        emptyLayout.addView(textView);
        return  emptyLayout;
    }


    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        try {
            if(unbinder!=null){
                unbinder.unbind();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
