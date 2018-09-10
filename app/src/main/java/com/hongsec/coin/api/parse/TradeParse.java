package com.hongsec.coin.api.parse;

import com.hongsec.coin.base.BaseParser;
import com.hongsec.coin.mvp.bean.TradeTickData;

import java.util.ArrayList;
import java.util.List;

public class TradeParse extends BaseParser{


    List<TradeTickData> data =new ArrayList<>();

    public void setData(List<TradeTickData> data) {
        this.data = data;
    }

    public List<TradeTickData> getData() {
        return data;
    }
}
