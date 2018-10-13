package com.readmycodetanos.upbittrade.Util;

import android.content.Context;

import com.readmycodetanos.upbittrade.activity.MainActivity;
import com.readmycodetanos.upbittrade.api.ApiCoinList;
import com.readmycodetanos.upbittrade.api.ApiTradeCoin;
import com.readmycodetanos.upbittrade.api.data.CoinListData;
import com.readmycodetanos.upbittrade.bean.CoinAnalyzeBean;
import com.readmycodetanos.upbittrade.bean.TradeRecord;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * Date:2018/10/13
 * Description:
 * ================================================
 */
public class RobotUtil {

    private MainActivity context;
    private String last240;
    private String last60;
    private String last30;
    private String last15;
    private String last10;
    private String last5;

    public RobotUtil(MainActivity context) {
        this.context = context;
    }

    private String getLastString(int hour, int minute) {
        return hour + "." + minute;
    }


    private boolean isLooping = false;

    /**
     * Need check per by second
     */
    public void check() {
        if (isLooping) {
            return;
        }
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        int hour = instance.getMinimum(Calendar.HOUR_OF_DAY);
        int minimum = instance.getMinimum(Calendar.MINUTE);
        int second = instance.getMinimum(Calendar.SECOND);
        //240  , 60 , 30 , 15, 10 , 5
        is240 = hour % 4 == 0 && !getLastString(hour, minimum).equalsIgnoreCase(last240);
        is60 = minimum == 0 && !getLastString(hour, minimum).equalsIgnoreCase(last60);
        is30 = minimum % 30 == 0 && !getLastString(hour, minimum).equalsIgnoreCase(last30);
        is15 = minimum % 15 == 0 && !getLastString(hour, minimum).equalsIgnoreCase(last15);
        is10 = minimum % 10 == 0 && !getLastString(hour, minimum).equalsIgnoreCase(last10);
        is5 = minimum % 5 == 0 && !getLastString(hour, minimum).equalsIgnoreCase(last5);
        for (CoinListData coinListData : coinListDataList) {
            loopCall(coinListData, 240);
        }
    }

    boolean is240;
    boolean is60;
    boolean is30;
    boolean is15;
    boolean is10;
    boolean is5;

    public void clearLock() {
        isLooping = false;
    }

    private void loopCall(final CoinListData coinListData, int code) {

        switch (code) {
            case 240:
                if (is240) {
                    ApiTradeCoin apiTradeCoin = new ApiTradeCoin(context, coinListData.getMarket(), ApiTradeCoin.MINUTE240);
                    apiTradeCoin.postRequest(context, new ImpApiCallBack<ApiTradeCoin>() {
                        @Override
                        public void onResponse(ApiTradeCoin apiTradeCoin) {

                            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(coinListData.getMarket());
                            TradeRecord tradeRecord = new TradeRecord();
                            tradeRecord.setLastUpbitData(apiTradeCoin.candleData_last);
                            tradeRecord.setUpbitData(apiTradeCoin.candleData);
                            coinAnalyzeBean.setTradeRecord240(tradeRecord);
                            coinAnalyzeBeans.put(coinListData.getMarket(), coinAnalyzeBean);

                            loopCall(coinListData, 60);
                        }
                    });
                } else {
                    loopCall(coinListData, 60);
                }

                break;
            case 60:
                if (is60) {
                    ApiTradeCoin apiTradeCoin = new ApiTradeCoin(context, coinListData.getMarket(), ApiTradeCoin.MINUTE60);
                    apiTradeCoin.postRequest(context, new ImpApiCallBack<ApiTradeCoin>() {
                        @Override
                        public void onResponse(ApiTradeCoin apiTradeCoin) {
                            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(coinListData.getMarket());
                            TradeRecord tradeRecord = new TradeRecord();
                            tradeRecord.setLastUpbitData(apiTradeCoin.candleData_last);
                            tradeRecord.setUpbitData(apiTradeCoin.candleData);
                            coinAnalyzeBean.setTradeRecord60(tradeRecord);
                            coinAnalyzeBeans.put(coinListData.getMarket(), coinAnalyzeBean);

                            loopCall(coinListData, 30);
                        }
                    });
                } else {
                    loopCall(coinListData, 30);
                }
                break;
            case 30:
                if (is30) {
                    ApiTradeCoin apiTradeCoin = new ApiTradeCoin(context, coinListData.getMarket(), ApiTradeCoin.MINUTE30);
                    apiTradeCoin.postRequest(context, new ImpApiCallBack<ApiTradeCoin>() {
                        @Override
                        public void onResponse(ApiTradeCoin apiTradeCoin) {
                            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(coinListData.getMarket());
                            TradeRecord tradeRecord = new TradeRecord();
                            tradeRecord.setLastUpbitData(apiTradeCoin.candleData_last);
                            tradeRecord.setUpbitData(apiTradeCoin.candleData);
                            coinAnalyzeBean.setTradeRecord30(tradeRecord);
                            coinAnalyzeBeans.put(coinListData.getMarket(), coinAnalyzeBean);
                            loopCall(coinListData, 15);
                        }
                    });
                } else {
                    loopCall(coinListData, 15);
                }
                break;
            case 15:
                if (is15) {
                    ApiTradeCoin apiTradeCoin = new ApiTradeCoin(context, coinListData.getMarket(), ApiTradeCoin.MINUTE15);
                    apiTradeCoin.postRequest(context, new ImpApiCallBack<ApiTradeCoin>() {
                        @Override
                        public void onResponse(ApiTradeCoin apiTradeCoin) {
                            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(coinListData.getMarket());
                            TradeRecord tradeRecord = new TradeRecord();
                            tradeRecord.setLastUpbitData(apiTradeCoin.candleData_last);
                            tradeRecord.setUpbitData(apiTradeCoin.candleData);
                            coinAnalyzeBean.setTradeRecord15(tradeRecord);
                            coinAnalyzeBeans.put(coinListData.getMarket(), coinAnalyzeBean);
                            loopCall(coinListData, 10);
                        }
                    });
                } else {
                    loopCall(coinListData, 10);
                }
                break;
            case 10:
                if (is10) {
                    ApiTradeCoin apiTradeCoin = new ApiTradeCoin(context, coinListData.getMarket(), ApiTradeCoin.MINUTE10);
                    apiTradeCoin.postRequest(context, new ImpApiCallBack<ApiTradeCoin>() {
                        @Override
                        public void onResponse(ApiTradeCoin apiTradeCoin) {
                            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(coinListData.getMarket());
                            TradeRecord tradeRecord = new TradeRecord();
                            tradeRecord.setLastUpbitData(apiTradeCoin.candleData_last);
                            tradeRecord.setUpbitData(apiTradeCoin.candleData);
                            coinAnalyzeBean.setTradeRecord10(tradeRecord);
                            coinAnalyzeBeans.put(coinListData.getMarket(), coinAnalyzeBean);

                            loopCall(coinListData, 5);
                        }
                    });
                } else {
                    loopCall(coinListData, 5);
                }
                break;
            case 5:
                if (is5) {
                    ApiTradeCoin apiTradeCoin = new ApiTradeCoin(context, coinListData.getMarket(), ApiTradeCoin.MINUTE5);
                    apiTradeCoin.postRequest(context, new ImpApiCallBack<ApiTradeCoin>() {
                        @Override
                        public void onResponse(ApiTradeCoin apiTradeCoin) {
                            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(coinListData.getMarket());
                            TradeRecord tradeRecord = new TradeRecord();
                            tradeRecord.setLastUpbitData(apiTradeCoin.candleData_last);
                            tradeRecord.setUpbitData(apiTradeCoin.candleData);
                            coinAnalyzeBean.setTradeRecord5(tradeRecord);
                            coinAnalyzeBeans.put(coinListData.getMarket(), coinAnalyzeBean);

                            new TradeDataAnalyzer().analyyzeCCI(context,coinAnalyzeBeans);

                        }
                    });
                } else {



                }


                break;
        }
    }


    public List<CoinListData> coinListDataList = new ArrayList<CoinListData>();
    public Map<String, CoinAnalyzeBean> coinAnalyzeBeans = new HashMap<>();

    public void loadCoinList(Context context) {
        ApiCoinList apiCoinList = new ApiCoinList(context);
        apiCoinList.postRequest(context, new ImpApiCallBack<ApiCoinList>() {
            @Override
            public void onResponse(ApiCoinList apiCoinList) {
                coinListDataList.clear();
                coinListDataList.addAll(apiCoinList.coinListDataList);

                coinAnalyzeBeans.clear();
                for (CoinListData coinListData : coinListDataList) {
                    CoinAnalyzeBean coinAnalyzeBean = new CoinAnalyzeBean();
                    coinAnalyzeBean.setCoinListData(coinListData);
                    coinAnalyzeBeans.put(coinAnalyzeBean.getCoinListData().getMarket(), coinAnalyzeBean);
                }

            }
        });

    }

}
