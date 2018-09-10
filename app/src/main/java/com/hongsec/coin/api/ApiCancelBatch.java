package com.hongsec.coin.api;

import android.content.Context;

import com.hongsec.coin.base.BaseJsonApiAbstract;
import com.starstudio.frame.net.extend.imp.OnJsonArrayCallBack;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.PostRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiCancelBatch extends BaseJsonApiAbstract<ApiCancelBatch,PostRequest> {
    public ApiCancelBatch(Context context) {
        super(context);
    }

    @Override
    public String getRequestUrl() {
        return "/v1/order/orders/batchcancel";
    }

    @Override
    public void setResponseData(Context context, JSONObject response) {
        successIdList.addAll(getJJsonArray(response, "data", new OnJsonArrayCallBack<String>() {
            @Override
            public String getItem(int position, Object itemjsonObject) {
                return itemjsonObject.toString();
            }
        }));

    }
    List<String> orderIds= new ArrayList<>();

    private void setOrderIds(String[] ids){

        for(String id:ids){

            orderIds.add(id);
        }

    }

    @Override
    public PostRequest getParams(PostRequest request) {
        PostRequest params = super.getParams(request);
        params.addUrlParams("orger-ids",orderIds);
        return params;
    }

    public List<String> successIdList =new ArrayList<>();

    @Override
    public void setResponseData(Context context, String response) {
    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.POST;
    }
}
