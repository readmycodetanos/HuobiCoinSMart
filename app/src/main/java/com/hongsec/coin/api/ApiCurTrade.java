package com.hongsec.coin.api;

import android.content.Context;

import com.hongsec.coin.base.BaseJsonApiAbstract;
import com.hongsec.coin.mvp.bean.TradeTickData;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.GetRequest;

import org.json.JSONObject;
// 获取最近的交易记录
//https://api.huobipro.com/market/trade?symbol=ethusdt
public class ApiCurTrade extends BaseJsonApiAbstract<ApiCurTrade,GetRequest> {

    public ApiCurTrade(Context context) {
        super(context);
    }
    private String symbol;


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /* "tick": {
    "id": 消息id,
    "ts": 最新成交时间,
    "data": [
      {
        "id": 成交id,
        "price": 成交价钱,
        "amount": 成交量,
        "direction": 主动成交方向,
        "ts": 成交时间
      }
    ]
  }*/
    @Override
    public String getRequestUrl() {
        return "https://api.huobipro.com/market/trade?symbol="+symbol;
    }

    TradeTickData tradeTickData=new TradeTickData();

    @Override
    public void setResponseData(Context context, JSONObject response) {

        tradeTickData = getGson().fromJson(getJJsonObject(response,"tick",new JSONObject()).toString(),TradeTickData.class);
    }

    @Override
    public void setResponseData(Context context, String response) {

    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.GET;
    }
}
