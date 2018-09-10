package com.hongsec.coin.mvp.bean;

public enum Period {

    MIN1("1min"),
    MIN5("5min"),
    MIN15("15min"),
    MIN30("30min"),
    MIN60("60min"),
    DAY1("1day"),
    MON1("1mon"),
    WEEK1("1week"),
    YEAR1("1year")
    ;


    private final String text;
    Period(final String text){
        this.text =text;
    }

    @Override
    public String toString() {
        return text;
    }
}
