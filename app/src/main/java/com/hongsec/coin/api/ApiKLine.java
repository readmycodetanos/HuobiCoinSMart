package com.hongsec.coin.api;

import android.content.Context;

import com.hongsec.coin.api.parse.KlineParse;
import com.hongsec.coin.base.BaseJsonApiAbstract;
import com.hongsec.coin.mvp.bean.Period;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.GetRequest;

import org.json.JSONObject;

/**
 *https://github.com/huobiapi/API_Docs/wiki/REST_api_reference#get-markethistorykline-%E8%8E%B7%E5%8F%96k%E7%BA%BF%E6%95%B0%E6%8D%AE
 * https://api.huobipro.com/market/history/kline?period=1day&size=200&symbol=btcusdt
 * get-markethistorykline-获取k线数据
 */
public class ApiKLine extends BaseJsonApiAbstract<ApiKLine,GetRequest> {

    public ApiKLine(Context context) {
        super(context);
    }

    private Period period=Period.MIN5;

    public void setPeriod(Period period) {
        this.period = period;
    }
    private int pageSize=200;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private String symbol;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    /*
      "data": [
    {
        "id": K线id,
            "amount": 成交量,
            "count": 成交笔数,
            "open": 开盘价,
            "close": 收盘价,当K线为最晚的一根时，是最新成交价
        "low": 最低价,
            "high": 最高价,
            "vol": 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
    }
]*/
    @Override
    public String getRequestUrl() {
        return "https://api.huobipro.com/market/history/kline?period="+period.toString()+"&size="+pageSize+"&symbol="+symbol;
    }

    public KlineParse klineParse = new KlineParse();

    @Override
    public void setResponseData(Context context, JSONObject response) {
        klineParse = getGson().fromJson(response.toString(),KlineParse.class);

    }

    @Override
    public void setResponseData(Context context, String response) {

    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.GET;
    }
}
