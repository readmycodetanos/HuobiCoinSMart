package com.starstudio.frame.base.bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hongsec on 2016-04-17.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public class BusMessage {

    public enum BUSTYPE{
        /**
         * default thread
         */
        onEvent ,
        /**
         * do in main thread
         */
        onEventMainThread,
        /**
         * do in backgound
         */
        onEventBackgroundThread,
        /**
         * do aysnc
         */
        onEventAsync};

    private BUSTYPE bustype;

    /**
     * Target class Name <br/>
     */
    private List<String> targetName =new ArrayList<>();

    /**
     * Target to all
     */
    private boolean targetAll =false;


    /**
     * Can save anything
     */
    private Object object = null;
    /**
     * Can save anything
     */
    private Object object2 = null;

    private int actionCode = 0;


    public Object getObject2() {
        return object2;
    }

    public void setObject2(Object object2) {
        this.object2 = object2;
    }

    public BUSTYPE getBustype() {
        return bustype;
    }

    public List<String> getTargetName() {
        return targetName;
    }

    public boolean isTargetAll() {
        return targetAll;
    }

    public Object getObject() {
        return object;
    }

    public int getActionCode() {
        return actionCode;
    }

    public BusMessage setBustype(BUSTYPE bustype) {
        this.bustype = bustype;
        return  this;
    }

    public BusMessage setTargetName(List<String> targetName) {
        this.targetName = targetName;
        return this;
    }

    public BusMessage setTargetAll(boolean targetAll) {
        this.targetAll = targetAll;
        return  this;
    }

    public BusMessage addTargetName(String targetName) {
        this.targetName.add(targetName);
        return  this;
    }

    public BusMessage setObject(Object object) {
        this.object = object;
        return  this;
    }

    public BusMessage setActionCode(int actionCode) {
        this.actionCode = actionCode;
        return  this;
    }

    public BusMessage() {
    }

    public BusMessage(BUSTYPE bustype, boolean targetAll, Object object, int actionCode) {
        this.bustype = bustype;
        this.targetAll = targetAll;
        this.object = object;
        this.actionCode = actionCode;
    }
    public BusMessage(BUSTYPE bustype, boolean targetAll, Object object, int actionCode, String... name) {
        this.bustype = bustype;
        this.targetAll = targetAll;
        this.object = object;
        this.actionCode = actionCode;
        if(name!=null ){
            for(String str: name){
                targetName.add(str);
            }
        }
    }

    public BusMessage(BUSTYPE bustype, boolean targetAll, Object object , int actionCode, List<String> targetName) {
        this.bustype = bustype;
        this.targetName = targetName;
        this.targetAll = targetAll;
        this.actionCode = actionCode;
        this.object = object;
    }
}
