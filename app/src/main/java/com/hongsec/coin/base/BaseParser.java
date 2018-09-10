package com.hongsec.coin.base;

import com.google.gson.Gson;
import com.starstudio.frame.net.extend.utils.JsonParseUtilAbstract;

public abstract class  BaseParser extends JsonParseUtilAbstract {

    private Gson gson;

    public Gson getGson() {
        if(gson==null){
            gson =new Gson();
        }
        return gson;
    }

}
