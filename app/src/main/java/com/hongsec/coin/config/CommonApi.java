package com.hongsec.coin.config;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.starstudio.frame.net.extend.model.Result;
import com.starstudio.frame.net.request.base.RequestAbstract;

/**
 * Created by hongseok on 2017-01-16.
 */

public class CommonApi<R extends RequestAbstract> {
        //TODO 서버 주소 변경
    //http://dev.admin-sk.gameto.me/admin
    public static final String DEV_WEB_URL = "https://dev.tripdeal.kr";
    public static final String REAL_WEB_URL = "https://www.tripdeal.kr";

    public static final String DEV_URL = "https://dev.memorycode.me";
    public static final String REAL_URL = "https://api.tripdeal.kr";




    public void init(Context context) {

        if (context instanceof Application) {
            throw new UnsupportedOperationException("API context cant't create with application context");
        }

        if (Cconfig.getInstance().isDebug) {
            Cconfig.getInstance().baseUrl = CommonApi.DEV_URL;
        } else {
            Cconfig.getInstance().baseUrl = CommonApi.REAL_URL;
        }

    }

    public R getHeaders(R request) {
//        request.headers("DEVICE-ACCESS-TOKEN", "&lnm)791hva@@bu&dgxbt8q8vp!!)-b5(r)0tiy)!rdvf)@h");
//        if(!TextUtils.isEmpty(ses)){
//            request.headers("Authorization","HANGURA_ACCESS_TOKEN "+ses);
//        }
        return request;
    }

    //post   add url data .need encode
    public String param2String() {
        StringBuffer stringBuffer = new StringBuffer();

        return stringBuffer.toString();
    }

    // default auto add url dont need encode
    public R getCommonParams(R request) {

        return request;
    }


    public void errorStatusProcess(final Context context, int statusCode, String message, Result result) {


        switch (statusCode) {

            case 401://logout

//                LoginUtil.getInstance().logoutgoToLogin(context);

                break;
            case 409:

                if (!TextUtils.isEmpty(result.msg)) {
//                    Utils_Alert.showAlertDialog(context, 0, result.msg, false, android.R.string.ok, null, 0, null, null);
                }
                break;
            default:
                break;

        }

    }


}
