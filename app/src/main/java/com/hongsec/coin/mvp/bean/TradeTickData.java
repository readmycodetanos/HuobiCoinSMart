package com.hongsec.coin.mvp.bean;

import java.util.ArrayList;
import java.util.List;

public class TradeTickData {

    /*{
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

    /**
     * id : 19327484238
     * ts : 1536558816809
     * data : [{"amount":1,"ts":1536558816809,"id":1.9327484238121033E21,"price":197.81,"direction":"buy"}]
     */

    private long id;
    private long ts;
    private List<TradeData> data =new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public List<TradeData> getData() {
        return data;
    }

    public void setData(List<TradeData> data) {
        this.data = data;
    }


}
