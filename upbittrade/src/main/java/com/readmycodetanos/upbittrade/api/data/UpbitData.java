package com.readmycodetanos.upbittrade.api.data;

public class UpbitData {


    /**
     * code : CRIX.UPBIT.KRW-ONT
     * candleDateTime : 2018-08-27T11:00:00+00:00
     * candleDateTimeKst : 2018-08-27T20:00:00+09:00
     * openingPrice : 2815
     * highPrice : 2840
     * lowPrice : 2810
     * tradePrice : 2810
     * candleAccTradeVolume : 32083.56891776
     * candleAccTradePrice : 9.03548321330509E7
     * timestamp : 1535369397124
     * unit : 30
     */

    private String code;
    private String candleDateTime;
    private String candleDateTimeKst;
    private double candleAccTradeVolume;
    private double candleAccTradePrice;
    private long timestamp;
    private int unit;
    private double mean_diviation;
    private double cciValue;
    /**
     * openingPrice : 26.7
     * highPrice : 27.1
     * lowPrice : 26.5
     * tradePrice : 26.9
     * candleAccTradeVolume : 4921443.65845523
     * candleAccTradePrice : 1.318878685372962E8
     */

    private double openingPrice;
    private double highPrice;
    private double lowPrice;
    private double tradePrice;

    public double getTypicalPrice() {
        if(typicalPrice==0){
            typicalPrice = (highPrice+lowPrice+tradePrice)/3.0;
        }
        return typicalPrice;
    }

    private double typicalPrice=0;

    public double getMean_diviation() {
        return mean_diviation;
    }

    public void setMean_diviation(double mean_diviation) {
        this.mean_diviation = mean_diviation;
    }

    public double getCciValue() {
        return cciValue;
    }

    public void setCciValue(double cciValue) {
        this.cciValue = cciValue;
    }

    public void setTypicalPrice(double typicalPrice) {
        this.typicalPrice = typicalPrice;
    }

    public double getSma_pt() {
        return sma_pt;
    }

    public void setSma_pt(double sma_pt) {
        this.sma_pt = sma_pt;
    }

    private double sma_pt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCandleDateTime() {
        return candleDateTime;
    }

    public void setCandleDateTime(String candleDateTime) {
        this.candleDateTime = candleDateTime;
    }

    public String getCandleDateTimeKst() {
        return candleDateTimeKst;
    }

    public void setCandleDateTimeKst(String candleDateTimeKst) {
        this.candleDateTimeKst = candleDateTimeKst;
    }



    public double getCandleAccTradeVolume() {
        return candleAccTradeVolume;
    }

    public void setCandleAccTradeVolume(double candleAccTradeVolume) {
        this.candleAccTradeVolume = candleAccTradeVolume;
    }

    public double getCandleAccTradePrice() {
        return candleAccTradePrice;
    }

    public void setCandleAccTradePrice(double candleAccTradePrice) {
        this.candleAccTradePrice = candleAccTradePrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public double getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(double openingPrice) {
        this.openingPrice = openingPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }
}
