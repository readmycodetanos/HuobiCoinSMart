package com.readmycodetanos.upbittrade.bean;

import com.readmycodetanos.upbittrade.api.data.UpbitData;

/**
 * ================================================
 * Date:2018/10/13
 * Description:
 * ================================================
 */
public class TradeRecord {

    private UpbitData upbitData = new UpbitData();
    private UpbitData lastUpbitData = new UpbitData();

    public UpbitData getUpbitData() {
        return upbitData;
    }

    public void setUpbitData(UpbitData upbitData) {
        this.upbitData = upbitData;
    }

    public UpbitData getLastUpbitData() {
        return lastUpbitData;
    }

    public void setLastUpbitData(UpbitData lastUpbitData) {
        this.lastUpbitData = lastUpbitData;
    }
}
