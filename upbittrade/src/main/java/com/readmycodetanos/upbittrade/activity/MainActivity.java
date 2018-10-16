package com.readmycodetanos.upbittrade.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.readmycodetanos.upbittrade.R;
import com.readmycodetanos.upbittrade.Util.RobotUtil;
import com.readmycodetanos.upbittrade.api.ApiSendTelegramMessage;
import com.readmycodetanos.upbittrade.base.BaseAct;
import com.starstudio.frame.net.extend.imp.OnCallBackListener;
import com.starstudio.frame.net.request.base.RequestAbstract;

import java.util.Calendar;


public class MainActivity extends BaseAct {

    private TextView textView;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void viewLoadFinished() {

    }

    @Override
    protected void initViews() {
        textView = findViewBId(R.id.textView);
        robotUtil = new RobotUtil(this);
        robotUtil.loadCoinList(this);
    }


    public void startTrade(View view) {

        handler2.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
        robotUtil.clearLock();
        handler.sendEmptyMessage(1);

    }

    RobotUtil robotUtil ;

    Handler handler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==1){
                robotUtil.trade();
                handler2.sendEmptyMessageDelayed(1,10*1000);

            }else{
                robotUtil.clearLock();
            }

        }
    };


    Handler handler2 =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what ==1){
                robotUtil.check();
                handler.sendEmptyMessageDelayed(1,10*1000);
            }
        }
    };


    public void coinedit(View view) {
    }

    public void showMessage(StringBuilder downMessage, StringBuilder needBuy, StringBuilder needSell, StringBuilder suddenlyUp, StringBuilder suddenlyDown) {
        Log.v("showMessage",downMessage.toString());
        Log.v("showMessage",needBuy.toString());
        Log.v("showMessage",needSell.toString());
        Log.v("showMessage",suddenlyUp.toString());
        Log.v("showMessage",suddenlyDown.toString());
        String message=downMessage.toString()+""+needBuy.toString()+needSell.toString()+suddenlyUp.toString()+suddenlyDown.toString() ;
        textView.setText(Calendar.getInstance().get(Calendar.HOUR)+":" +Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND)+"\n"+message);
        ApiSendTelegramMessage apiSendTelegramMessage = new ApiSendTelegramMessage(this);
        apiSendTelegramMessage.setMessage(message);
        apiSendTelegramMessage.postRequest(this, new OnCallBackListener<ApiSendTelegramMessage>() {
            @Override
            public void showloadingUI(RequestAbstract<String, ? extends RequestAbstract> abstractRequest) {

            }

            @Override
            public void cancleloadingUI() {

            }

            @Override
            public void onResponse(ApiSendTelegramMessage apiSendTelegramMessage) {

            }

            @Override
            public void onErrorResponse(ApiSendTelegramMessage apiSendTelegramMessage) {

            }
        });
    }

    public void showHeart() {
        Log.v("showMessage","beat");
        textView.setText(Calendar.getInstance().get(Calendar.HOUR)+":" +Calendar.getInstance().get(Calendar.MINUTE)+":"+Calendar.getInstance().get(Calendar.SECOND)+"beat");

    }
}
