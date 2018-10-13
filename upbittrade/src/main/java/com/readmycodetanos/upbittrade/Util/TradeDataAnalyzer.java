package com.readmycodetanos.upbittrade.Util;

import com.readmycodetanos.upbittrade.activity.MainActivity;
import com.readmycodetanos.upbittrade.api.data.CoinListData;
import com.readmycodetanos.upbittrade.api.data.UpbitData;
import com.readmycodetanos.upbittrade.bean.CoinAnalyzeBean;
import com.readmycodetanos.upbittrade.bean.TradeDirection;
import com.readmycodetanos.upbittrade.bean.TradeRecord;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * Date:2018/10/13
 * Description:
 * ================================================
 */
public class TradeDataAnalyzer {


    public void analyyzeCCI(MainActivity context, Map<String, CoinAnalyzeBean> coinAnalyzeBeans) {

        Set<String> strings = coinAnalyzeBeans.keySet();
        Iterator<String> iterator = strings.iterator();

        StringBuilder downMessage = new StringBuilder();
        downMessage.append("폭락시작한 코인:\n");

        StringBuilder needBuy = new StringBuilder();
        needBuy.append("매수 추천 코인:\n");

        StringBuilder needSell = new StringBuilder();
        needSell.append("매도 추천 코인:\n");

        while (iterator.hasNext()) {
            String next = iterator.next();
            CoinAnalyzeBean coinAnalyzeBean = coinAnalyzeBeans.get(next);
            CoinListData coinListData = coinAnalyzeBean.getCoinListData();
            TradeRecord tradeRecord240 = coinAnalyzeBean.getTradeRecord240();
            TradeRecord tradeRecord60 = coinAnalyzeBean.getTradeRecord60();
            TradeRecord tradeRecord30 = coinAnalyzeBean.getTradeRecord30();
            TradeRecord tradeRecord15 = coinAnalyzeBean.getTradeRecord15();
            TradeRecord tradeRecord10 = coinAnalyzeBean.getTradeRecord10();
            TradeRecord tradeRecord5 = coinAnalyzeBean.getTradeRecord5();
            TradeDirection direction240 = getDirection(tradeRecord240.getUpbitData(), tradeRecord240.getLastUpbitData());
            TradeDirection direction60 = getDirection(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData());
            TradeDirection direction30 = getDirection(tradeRecord30.getUpbitData(), tradeRecord30.getLastUpbitData());
            TradeDirection direction15 = getDirection(tradeRecord15.getUpbitData(), tradeRecord15.getLastUpbitData());
            TradeDirection direction10 = getDirection(tradeRecord10.getUpbitData(), tradeRecord10.getLastUpbitData());
            TradeDirection direction5 = getDirection(tradeRecord5.getUpbitData(), tradeRecord5.getLastUpbitData());

            //여기부터 정책
            //  240 , 60, 30 ,15는 장기혹은 단기적으로 상승세 예측 입니다.
            // 240 이 UP_FROM_DOWN일때  앞으로 8시간이내 혹은 2일내 상승이 있음을 예고합니다.(주간용)
            // 60 이 UP_FROM_DOWN일때  앞으로 6시간이내   상승이 있음을 예고합니다.  (1일,2일용)
            // 30 이 UP_FROM_DOWN일때  앞으로 2시간이내   상승이 있음을 예고합니다. (반나절용)
            // 15,10,5    (단타용)
            // 5 이 UP_FROM_DOWN일때  240 ,50, 30 ,15 이가


            //폭락알림.
            /**
             * (1)너무 잦은 알림 제한으로 30,60,120분만 알립니다.
             */
            boolean down_case1 = direction240 == TradeDirection.DOWN_FROM_NORMAL || direction60 == TradeDirection.DOWN_FROM_NORMAL || direction30 == TradeDirection.DOWN_FROM_NORMAL;
            if (down_case1) {
                downMessage.append("[" + coinListData.getKorean_name() + "]:"); //coin name
                if (direction240 == TradeDirection.DOWN_FROM_NORMAL) {
                    downMessage.append(getDataString(tradeRecord240.getUpbitData(), tradeRecord240.getLastUpbitData(), "240분"));
                    downMessage.append("\n");
                }
                if (direction60 == TradeDirection.DOWN_FROM_NORMAL) {
                    downMessage.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "60분"));
                    downMessage.append("\n");
                }
                if (direction30 == TradeDirection.DOWN_FROM_NORMAL) {
                    downMessage.append(getDataString(tradeRecord30.getUpbitData(), tradeRecord30.getLastUpbitData(), "30분"));
                    downMessage.append("\n");
                }
                downMessage.append("\n");
            }


            //매수알림.
            /**
             * 로직 1:
             * (1)안전로직으로 30분 UP_FRON_DOWN 일떄 알립니다.
             * (2)장기가 하락중이면서 10,15분봉이 UP_FROM_DOWN일때 아립니다.
             */
            boolean case1 = direction30 == TradeDirection.UP_FROM_DOWN;
            boolean case2 = ((direction240 == TradeDirection.DOWNING || direction60 == TradeDirection.DOWNING || direction30 == TradeDirection.DOWNING) && (direction15 == TradeDirection.UP_FROM_DOWN || direction10 == TradeDirection.UP_FROM_DOWN));
            if (case1 || case2) {
                needBuy.append("[" + coinListData.getKorean_name() + "]:"); //coin name


                if (direction30 == TradeDirection.UP_FROM_DOWN) {
                    needBuy.append("[반나절 과매도 종료로 매수추천]");
                    needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "30분"));
                    needBuy.append("\n");
                }
                if (case2) {
                    needBuy.append("[단기 과매도 종료로 매수추천]");
                    if (direction15 == TradeDirection.UP_FROM_DOWN) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "15분"));
                    }
                    if (direction10 == TradeDirection.UP_FROM_DOWN) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "10분"));
                    }
                    needBuy.append("\n");
                }
                needBuy.append("\n");
            }


            //매도알림.
            /**
             * 로직:
             * (1) 10,15,30,60,240 이 DOWN_FROM_UP일떄 매도 추천합니다.
             */
            boolean sell_case1 = direction240 == TradeDirection.DOWN_FROM_UP || direction60 == TradeDirection.DOWN_FROM_UP || direction30 == TradeDirection.DOWN_FROM_UP || direction15 == TradeDirection.DOWN_FROM_UP || direction10 == TradeDirection.DOWN_FROM_UP;
            if (sell_case1) {
                needBuy.append("[" + coinListData.getKorean_name() + "]:"); //coin name
                if (direction240 == TradeDirection.DOWN_FROM_UP || direction60 == TradeDirection.DOWN_FROM_UP || direction30 == TradeDirection.DOWN_FROM_UP) {
                    needBuy.append("[장기적 과매수 종료로 매도추천]");
                    if (direction240 == TradeDirection.DOWN_FROM_UP) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "240분"));
                    }
                    if (direction60 == TradeDirection.DOWN_FROM_UP) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "60분"));
                    }
                    if (direction30 == TradeDirection.DOWN_FROM_UP) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "30분"));
                    }

                    needBuy.append("\n");
                }
                if ( direction15 == TradeDirection.DOWN_FROM_UP || direction10 == TradeDirection.DOWN_FROM_UP) {
                    needBuy.append("[단기적 과매수 종료로 매도추천]");
                    if (direction15 == TradeDirection.DOWN_FROM_UP) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "15분"));
                    }
                    if (direction10 == TradeDirection.DOWN_FROM_UP) {
                        needBuy.append(getDataString(tradeRecord60.getUpbitData(), tradeRecord60.getLastUpbitData(), "10분"));
                    }
                    needBuy.append("\n");
                }

                needBuy.append("\n");
            }

        }

        context.showMessage(downMessage,needBuy,needSell);

    }

    private String getDataString(UpbitData curData, UpbitData lastData, String minute) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[" + minute + "]");
        stringBuilder.append("[" + lastData.getCciValue() + "->" + curData.getCciValue() + "]");//cci change
        stringBuilder.append("[" + (Math.round((curData.getTradePrice() - lastData.getTradePrice()) / lastData.getTradePrice()) * 100) / 100.0 + "%]"); //percentage
        stringBuilder.append("[" + lastData.getTradePrice() + "->" + curData.getTradePrice() + "]");// price change
        return stringBuilder.toString();
    }


    private TradeDirection getDirection(UpbitData curData, UpbitData lastData) {

        if (Math.abs(curData.getCciValue()) < 100 && Math.abs(lastData.getCciValue()) < 100) {
            return TradeDirection.NORMAL;// all ways in  ---

        } else if (curData.getCciValue() > lastData.getCciValue()) {// up

            if (lastData.getCciValue() < -100 && curData.getCciValue() > -100) {
                return TradeDirection.UP_FROM_DOWN; // downUp
            }
            if (curData.getCciValue() > 100 && lastData.getCciValue() < 100) {
                return TradeDirection.UP_FROM_NORMAL;// topUp
            }

            return TradeDirection.UPING;
        } else if (curData.getCciValue() < lastData.getCciValue()) {  //down
            if (lastData.getCciValue() > 100 && curData.getCciValue() < 100) {
                return TradeDirection.DOWN_FROM_UP;// topDown
            }
            if (curData.getCciValue() < -100 && lastData.getCciValue() > -100) {
                return TradeDirection.DOWN_FROM_NORMAL;// bottomDown
            }
            return TradeDirection.DOWNING;
        }

        return TradeDirection.NORMAL;
    }


}
