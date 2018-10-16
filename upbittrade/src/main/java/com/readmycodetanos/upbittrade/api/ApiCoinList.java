package com.readmycodetanos.upbittrade.api;

import android.content.Context;

import com.readmycodetanos.upbittrade.api.data.CoinListData;
import com.readmycodetanos.upbittrade.base.BaseJsonApiAbstract;
import com.starstudio.frame.net.extend.imp.OnJsonArrayCallBack;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.request.GetRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Date:2018/10/13
 * Description: Coin List
 * ================================================
 */
public class ApiCoinList extends BaseJsonApiAbstract<ApiCoinList,GetRequest> {

    public ApiCoinList(Context context) {
        super(context);
    }

    @Override
    public String getRequestUrl() {
        return "https://api.upbit.com/v1/market/all";
    }

    public List<CoinListData> coinListDataList =new ArrayList<>();

    @Override
    public void setResponseData(Context context, JSONObject response) {
          List<CoinListData> coinListDataList_temp =new ArrayList<>();
        coinListDataList_temp.addAll(getJJsonArray(response, "list", new OnJsonArrayCallBack<CoinListData>() {
            @Override
            public CoinListData getItem(int position, Object itemjsonObject) {
                return getGson().fromJson(itemjsonObject.toString(),CoinListData.class);
            }
        }));
        for(CoinListData coinListData :coinListDataList_temp){
            if(coinListData.getMarket().startsWith("KRW-XRP")){
                coinListDataList.add(coinListData);
            }
            if(coinListData.getMarket().startsWith("KRW-ADA")){
                coinListDataList.add(coinListData);
            }
            if(coinListData.getMarket().startsWith("KRW-TRON")){
                coinListDataList.add(coinListData);
            }
            if(coinListData.getMarket().startsWith("KRW-ETH")){
                coinListDataList.add(coinListData);
            }
            if(coinListData.getMarket().startsWith("KRW-STORJ")){
                coinListDataList.add(coinListData);
            }
            if(coinListData.getMarket().startsWith("KRW-ETH")){
                coinListDataList.add(coinListData);
            }
        }
//        coinListDataList.add(coinListDataList_temp.get(0));
//        coinListDataList.add(coinListDataList_temp.get(2));
    }

    @Override
    public void setResponseData(Context context, String response) {

    }

    @Override
    public HttpMethod getCustomRequestType() {
        return HttpMethod.GET;
    }
}
