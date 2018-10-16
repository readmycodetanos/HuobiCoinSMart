package com.readmycodetanos.upbittrade.api;

import android.content.Context;

import com.readmycodetanos.upbittrade.base.BaseJsonApiAbstract;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.GetRequest;

import org.json.JSONObject;

public class ApiSendTelegramMessage extends BaseJsonApiAbstract<ApiSendTelegramMessage,GetRequest> {
    public ApiSendTelegramMessage(Context context) {
        super(context);
    }

    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public GetRequest getParams(GetRequest request) {
        GetRequest params = super.getParams(request);
        params.params("text",message);
        params.params("chat_id","-297201890");
        return params;
    }

    @Override
    public String getRequestUrl() {
        return "https://api.telegram.org/bot666108757:AAEsGKaJgJdGKmUcMIf3soKd_2lW1pq3fp0/sendMessage";//?chat_id=-297201890&text="+message;
    }

    @Override
    public void setResponseData(Context context, JSONObject response) {

    }

    @Override
    public void setResponseData(Context context, String response) {

    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.GET;
    }
}
