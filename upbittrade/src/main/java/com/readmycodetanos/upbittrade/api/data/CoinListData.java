package com.readmycodetanos.upbittrade.api.data;

/**
 * ================================================
 * Date:2018/10/13
 * Description:
 * ================================================
 */
public class CoinListData {

    /**
     * market : KRW-BTC
     * korean_name : 비트코인
     * english_name : Bitcoin
     */

    private String market;
    private String korean_name;
    private String english_name;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getKorean_name() {
        return korean_name;
    }

    public void setKorean_name(String korean_name) {
        this.korean_name = korean_name;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }
}
