package com.hongsec.coin.api;

import android.content.Context;

import com.hongsec.coin.api.parse.TradeParse;
import com.hongsec.coin.base.BaseJsonApiAbstract;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.GetRequest;

import org.json.JSONObject;

// 获取最近的交易记录
//https://api.huobipro.com/market/history/trade?symbol=ethusdt&size=2
public class ApiHistoryTrade extends BaseJsonApiAbstract<ApiHistoryTrade,GetRequest> {

    public ApiHistoryTrade(Context context) {
        super(context);
    }
    private String symbol;


    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    int pageSize =100;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    /*"data": {
    "id": 消息id,
    "ts": 最新成交时间,
    "data": [
      {
        "id": 成交id,
        "price": 成交价,
        "amount": 成交量,
        "direction": 主动成交方向,
        "ts": 成交时间
      }
    ]
  }*/
    @Override
    public String getRequestUrl() {
        return "https://api.huobipro.com/market/history/trade?symbol="+symbol+"&size="+pageSize;
    }

    TradeParse tradeTickData= new TradeParse();
    @Override
    public void setResponseData(Context context, JSONObject response) {

        tradeTickData = getGson().fromJson(response.toString(),TradeParse.class);
    }

    @Override
    public void setResponseData(Context context, String response) {

    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.GET;
    }
}
