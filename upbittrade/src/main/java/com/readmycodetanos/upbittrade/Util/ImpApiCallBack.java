package com.readmycodetanos.upbittrade.Util;

import com.starstudio.frame.net.extend.imp.OnCallBackListener;
import com.starstudio.frame.net.request.base.RequestAbstract;

/**
 * ================================================
 * Date:2018/10/13
 * Description:
 * ================================================
 */
public abstract class  ImpApiCallBack<T> implements OnCallBackListener<T> {

    @Override
    public void showloadingUI(RequestAbstract<String, ? extends RequestAbstract> abstractRequest) {

    }

    @Override
    public void cancleloadingUI() {

    }

    @Override
    public void onErrorResponse(T t) {

    }
}
