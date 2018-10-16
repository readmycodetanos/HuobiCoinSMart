package com.readmycodetanos.upbittrade.api;

import android.content.Context;

import com.readmycodetanos.upbittrade.api.data.UpbitData;
import com.readmycodetanos.upbittrade.base.BaseJsonApiAbstract;
import com.starstudio.frame.base.util.UtilsLog;
import com.starstudio.frame.net.extend.imp.OnJsonArrayCallBack;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.GetRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * Date:2018/10/13
 * Description: Coin Trading
 * ================================================
 */
public class ApiTradeCoin extends BaseJsonApiAbstract<ApiTradeCoin, GetRequest> {

    //KRW-ONT
    private String code = "";

    public static final int MINUTE5 = 5;
    public static final int MINUTE10 = 1;
    public static final int MINUTE15 = 15;
    public static final int MINUTE30 = 30;
    public static final int MINUTE60 = 60;
    public static final int MINUTE240 = 240;

    private int minute;

    public ApiTradeCoin(Context context, String code, int minute) {
        super(context);
        this.code = code;
        this.minute = minute;
    }

    @Override
    public String getRequestUrl() {
        return "https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/" + minute + "?code=CRIX.UPBIT." + code + "&count=30";
    }


    public List<UpbitData> candleDatas_remake = new ArrayList<>();


    public UpbitData candleData ;
    public  UpbitData candleData_last ;


    @Override
    public void setResponseData(Context context, JSONObject response) {

        List<UpbitData> candleDatas = new ArrayList<>();
        candleDatas.addAll(getJJsonArray(response, "list", new OnJsonArrayCallBack<UpbitData>() {

            @Override
            public UpbitData getItem(int position, Object itemjsonObject) {
                UpbitData candleData =getGson().fromJson(itemjsonObject.toString(),UpbitData.class);
                return candleData;
            }
        }));
        Collections.reverse(candleDatas);


        for(int i=0;i<candleDatas.size();i++){
            UpbitData candleData =candleDatas.get(i);
            if(candleDatas_remake.size()>=19){
                candleData.setSma_pt((getSumTypical()+candleData.getTypicalPrice())/20);
                candleData.setMean_diviation((getSumDeviation(candleData.getSma_pt())+Math.abs(candleData.getSma_pt()-candleData.getTypicalPrice()))/20);
                candleData.setCciValue((candleData.getTypicalPrice()-candleData.getSma_pt())/(0.015*candleData.getMean_diviation()));
              UtilsLog.getInstance().v("hongsegi:"+code+minute+":"+candleData.getCandleDateTimeKst()+"price:"+candleData.getTradePrice()+"   cci:"+candleData.getCciValue());
            }
            candleDatas_remake.add(candleData);
        }
          candleData =  candleDatas_remake.get(candleDatas_remake.size() - 1);
          candleData_last =  candleDatas_remake.get(candleDatas_remake.size() - 2);
    }

    private double getSumDeviation(double cursma) {
        double result = 0;
        for (int j = (candleDatas_remake.size() - 19); j < candleDatas_remake.size(); j++) {
            UpbitData candleData1 = candleDatas_remake.get(j);
            result += Math.abs(cursma - candleData1.getTypicalPrice());
        }
        return result;
    }

    private double getSumTypical() {
        double result = 0;
        for (int j = (candleDatas_remake.size() - 19); j < candleDatas_remake.size(); j++) {
            UpbitData candleData1 = candleDatas_remake.get(j);
            result += candleData1.getTypicalPrice();
        }
        return result;
    }


    @Override
    public void setResponseData(Context context, String response) {

    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.GET;
    }
}
