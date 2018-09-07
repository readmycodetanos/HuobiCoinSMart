package com.starstudio.frame.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.starstudio.frame.base.bus.BusMessage;
import com.starstudio.frame.base.bus.BusTool;
import com.starstudio.frame.base.util.UtilsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * Created by Hongsec on 2016-07-21.
 */
public abstract class BaseActivityAbstract extends AppCompatActivity {


    protected final String TAG = this.getClass().getSimpleName();

    /**
     * 앱티비티가 로드 완료되였는지 표기하는 필더 onWindowFocusChanged 참고
     */
    private boolean loaded = false;

    /**
     * ContentView ResourceID
     *
     * @return
     */
    protected abstract int setContentLayoutResID();

    /**
     * 액티비티 뷰가 완전히 로드되였을때 호출됨 <br>
     * (액티비티가 완전히 로드되였는지는 onWindowsFocused가 호출될때 완료되였다고 판단함 )
     */
    protected abstract void viewLoadFinished();


    /**
     * 뷰초기화  in onCreate
     */
    protected abstract void initViews();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {

        } else {
            //remove state fragments
            savedInstanceState.remove("android.support.fragments");
        }

        if (setContentLayoutResID() != 0) {
            setContentView(setContentLayoutResID());
        }
        registerBus();

        //activity관리를 위해 register 하여 저장
        UtilsActivity.getInstance().registerActivity(this, getClass().getSimpleName());

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            if (!loaded) {
                //로드완료
                loaded = true;

                viewLoadFinished();
            }


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaded = false;
        unRegisterBus();
        //activity 관리자에서 제거
        UtilsActivity.getInstance().unregisterActivity(this, getClass().getSimpleName());

    }


    /**
     * findViewById를 다시 만듬
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewBId(@IdRes int id) {
        return (T) super.findViewById(id);
    }


    private void unRegisterBus() {
        try {
            //이벤트 버스 해제
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void registerBus() {
        //이벤트 버스 등록
        try {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    @Subscribe
    public void onEvent(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEvent, this.getClass().getSimpleName())) {
            return;
        }
        busOnEvent(myBus);
    }

    ;

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    @Subscribe
    public void onEventMainThread(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventMainThread, this.getClass().getSimpleName())) {
            return;
        }
        busOnEventMainThread(myBus);
    }

    ;

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    @Subscribe
    public void onEventBackgroundThread(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventBackgroundThread, this.getClass().getSimpleName())) {
            return;
        }
        busOnEventBackgroundThread(myBus);
    }

    ;

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    @Subscribe
    public void onEventAsync(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventAsync, this.getClass().getSimpleName())) {
            return;
        }
        busOnEventAsync(myBus);


    }


    /**
     * 如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，
     * onEvent就会在这个线程中运行，也就是说发布事件和接收事件线程在同一个线程。
     * 使用这个方法时，在onEvent方法中不能执行耗时操作
     * ，如果执行耗时操作容易导致事件分发延迟。
     *
     * @param myBus
     */
    protected void busOnEvent(BusMessage myBus) {
    }


    /**
     * 如果使用onEventMainThread作为订阅函数，
     * 那么不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行
     * ，接收事件就会在UI线程中运行，这个在Android中是非常有用的，
     * 因为在Android中只能在UI线程中跟新UI，所以在onEvnetMainThread方法中是不能执行耗时操作的。
     *
     * @param myBus
     */
    protected void busOnEventMainThread(BusMessage myBus) {
    }


    /**
     * 如果使用onEventBackgrond作为订阅函数，那么如果事件是在UI线程中发布出来的，那么
     * onEventBackground就会在子线程中运行，
     * 如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行。
     *
     * @param myBus
     */
    protected void busOnEventBackgroundThread(BusMessage myBus) {
    }



    /**
     * 使用这个函数作为订阅函数，
     * 那么无论事件在哪个线程发布，都会创建新的子线程在执行onEventAsync.
     *
     * @param myBus
     */
    protected void busOnEventAsync(BusMessage myBus) {
    }



}
