package com.hongsec.coin.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.hongsec.coin.config.Cconfig;
import com.hongsec.coin.config.CommonApi;
import com.starstudio.frame.net.extend.imp.OnCallBackListener;
import com.starstudio.frame.net.extend.req.BaseJsonRequestAbstract;
import com.starstudio.frame.net.request.base.RequestAbstract;

/**
 * Created by hongsec on 17. 7. 3.
 */

public abstract class BaseJsonApiAbstract<T, R extends RequestAbstract> extends BaseJsonRequestAbstract<T, R> {

    protected CommonApi<R> commonApi = new CommonApi<R>();

    @SuppressLint("MissingPermission")
    public boolean isConnectNet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

         NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public R getParams(R request) {
        return request;
    }

    public BaseJsonApiAbstract(Context context) {
        super();
        //        setCacheKey();
//        setCacheTime();
//        setRetryCount();
//    setCacheMode();
        commonApi.init(context);
    }

    @Override
    public R postRequest(Context context, OnCallBackListener<T> tOnCallBackListener) {
        R request = super.postRequest(context, tOnCallBackListener);
        if (context instanceof BaseAct) {
            ((BaseAct) context).putApiStack(BaseJsonApiAbstract.class.getSimpleName(), request);
        }
        return request;
    }


    @Override
    public String baseUrl() {
        return Cconfig.getInstance().baseUrl;
    }

    @Override
    public R getHeaders(R request) {
        return commonApi.getHeaders(request);
    }


    @Override
    public R getUrlParams(R request) {
        return commonApi.getCommonParams(request);
    }

    @Override
    public void errorStatusProcess(final Context context, int statusCode, String message) {

        commonApi.errorStatusProcess(context, statusCode, message, result);

    }


}
