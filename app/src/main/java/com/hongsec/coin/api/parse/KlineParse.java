package com.hongsec.coin.api.parse;

import com.hongsec.coin.base.BaseParser;
import com.hongsec.coin.mvp.bean.KLineData;

import java.util.ArrayList;
import java.util.List;

public class KlineParse extends BaseParser {



    public List<KLineData> data =new ArrayList<>();


    public List<KLineData> getData() {
        return data;
    }

    public void setData(List<KLineData> data) {
        this.data = data;
    }
}
