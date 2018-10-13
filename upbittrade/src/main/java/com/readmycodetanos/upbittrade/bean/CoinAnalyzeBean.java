package com.readmycodetanos.upbittrade.bean;

import com.readmycodetanos.upbittrade.api.data.CoinListData;

/**
 * ================================================
 * Date:2018/10/13
 * Description:
 * ================================================
 */
public class CoinAnalyzeBean {
    CoinListData coinListData;

    TradeRecord tradeRecord5 =new TradeRecord();
    TradeRecord tradeRecord10 =new TradeRecord();
    TradeRecord tradeRecord15 =new TradeRecord();
    TradeRecord tradeRecord30 =new TradeRecord();
    TradeRecord tradeRecord60 =new TradeRecord();
    TradeRecord tradeRecord240 =new TradeRecord();

    public CoinListData getCoinListData() {
        return coinListData;
    }

    public void setCoinListData(CoinListData coinListData) {
        this.coinListData = coinListData;
    }

    public TradeRecord getTradeRecord5() {
        return tradeRecord5;
    }

    public void setTradeRecord5(TradeRecord tradeRecord5) {
        this.tradeRecord5 = tradeRecord5;
    }

    public TradeRecord getTradeRecord10() {
        return tradeRecord10;
    }

    public void setTradeRecord10(TradeRecord tradeRecord10) {
        this.tradeRecord10 = tradeRecord10;
    }

    public TradeRecord getTradeRecord15() {
        return tradeRecord15;
    }

    public void setTradeRecord15(TradeRecord tradeRecord15) {
        this.tradeRecord15 = tradeRecord15;
    }

    public TradeRecord getTradeRecord30() {
        return tradeRecord30;
    }

    public void setTradeRecord30(TradeRecord tradeRecord30) {
        this.tradeRecord30 = tradeRecord30;
    }

    public TradeRecord getTradeRecord60() {
        return tradeRecord60;
    }

    public void setTradeRecord60(TradeRecord tradeRecord60) {
        this.tradeRecord60 = tradeRecord60;
    }

    public TradeRecord getTradeRecord240() {
        return tradeRecord240;
    }

    public void setTradeRecord240(TradeRecord tradeRecord240) {
        this.tradeRecord240 = tradeRecord240;
    }
}
