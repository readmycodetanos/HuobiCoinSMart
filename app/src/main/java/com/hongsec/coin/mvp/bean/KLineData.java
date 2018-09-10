package com.hongsec.coin.mvp.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class KLineData {

    /**
     * id : 1536508800
     * open : 6375.07
     * close : 6310.26
     * low : 6215
     * high : 6394.95
     * amount : 7269.358800489588
     * vol : 4.6137238347606234E7
     * count : 30565
     */
    @Id
    private int id;
    private double open;
    private double close;
    private int low;
    private double high;
    private double amount;
    private double vol;
    private int count;

    @Generated(hash = 693458504)
    public KLineData(int id, double open, double close, int low, double high,
            double amount, double vol, int count) {
        this.id = id;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
        this.amount = amount;
        this.vol = vol;
        this.count = count;
    }

    @Generated(hash = 1227447334)
    public KLineData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
