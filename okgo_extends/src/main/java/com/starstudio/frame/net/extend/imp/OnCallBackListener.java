package com.starstudio.frame.net.extend.imp;

import com.starstudio.frame.net.request.base.RequestAbstract;

/**
 * Created by Hongsec on 2016-08-21.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public interface OnCallBackListener<T> {
    public void showloadingUI(RequestAbstract<String, ? extends RequestAbstract> abstractRequest);
    public void cancleloadingUI();
    public void onResponse(T t);
    public void onErrorResponse(T t);
}
