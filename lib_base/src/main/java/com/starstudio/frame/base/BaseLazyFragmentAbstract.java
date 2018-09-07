package com.starstudio.frame.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.starstudio.frame.base.bus.BusMessage;
import com.starstudio.frame.base.bus.BusTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;


/**
 * <h1>懒加载Fragment</h1> 只有创建并显示的时候才会调用onCreateViewLazy方法<br>
 * <br>
 * <p>
 * 懒加载的原理onCreateView的时候Fragment有可能没有显示出来。<br>
 * 但是调用到setUserVisibleHint(boolean isVisibleToUser),isVisibleToUser =
 * true的时候就说明有显示出来<br>
 * 但是要考虑onCreateView和setUserVisibleHint的先后问题所以才有了下面的代码
 * <p>
 * 注意：<br>
 * 《1》原先的Fragment的回调方法名字后面要加个Lazy，比如Fragment的onCreateView方法， 就写成onCreateViewLazy <br>
 * 《2》使用该LazyFragment会导致多一层布局深度
 *
 * @author Hongsec
 */
public abstract class BaseLazyFragmentAbstract extends Fragment {

    /**
     * ContentView ResourceID
     *
     * @return
     */
    protected abstract int setContentLayoutResID();


    public ViewGroup getEmptyLayout() {
        layout = new LinearLayout(getBaseApp());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        return layout;
    }




    protected BaseActivityAbstract getBaseParent() {
        return getActivity() == null ? null : (BaseActivityAbstract) getActivity();
    }

    protected BaseApplication getBaseApp() {
        return getBaseParent() == null ? null : (getBaseParent().getApplication() == null ? null : (BaseApplication) getBaseParent().getApplication());
    }

    /**
     * findViewById를 다시 만듬
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewBId(@IdRes int id) {
        return (T) contentView.findViewById(id);
    }

    protected LayoutInflater inflater;
    private View contentView;
    private ViewGroup container;


    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }


    public View getContentView() {
        return contentView;
    }


    private boolean isInit = false;
    private Bundle savedInstanceState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    private boolean isLazyLoad = true;
    private LinearLayout layout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBus();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unRegisterBus();
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
        try {
            //이벤트 버스 해제
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 子文件中可以用次来做特殊处理。 layout inflate 正常做,在这里必须要做setContentView
     *
     * @param savedInstanceState
     */
    protected void onCreateView(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        }

        /**
         * activity에서  remove fragement 시켜서 뷰초기화가 필요할경우
         */
        isInit = false;


        if (isLazyLoad) {
            if (getUserVisibleHint() && !isInit) {
                isInit = true;
                this.savedInstanceState = savedInstanceState;
                onCreateViewLazy(savedInstanceState);
            } else {
                contentView = getEmptyLayout();
            }
        } else {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
        }

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isInit && getContentView() != null) {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
            onResumeLazy();
        }
        if (isInit && getContentView() != null) {
            if (isVisibleToUser) {
                isStart = true;
                onFragmentStartLazy();
            } else {
                isStart = false;
                onFragmentStopLazy();
            }
        }
    }

    @Deprecated
    @Override
    public void onStart() {
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onFragmentStartLazy();
        }
    }

    @Deprecated
    @Override
    public void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onFragmentStopLazy();
        }
    }


    public void setContentView(int layoutResID) {
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            View view = inflater.inflate(layoutResID, layout, false);
            layout.addView(view);
        } else {
            contentView = (ViewGroup) inflater.inflate(layoutResID, container, false);
        }
    }

    public void setContentView(View view) {
        if (isLazyLoad && getContentView() != null && getContentView().getParent() != null) {
            layout.removeAllViews();
            layout.addView(view);
        } else {
            contentView = view;
        }
    }

    @Override
    @Deprecated
    public void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public void onDestroyView() {
        super.onDestroyView();
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }

    private boolean isStart = false;





    protected void onFragmentStartLazy() {

    }

    protected void onFragmentStopLazy() {

    }

    protected void onCreateViewLazy(Bundle savedInstanceState) {

    }

    protected void onResumeLazy() {

    }

    protected void onPauseLazy() {

    }

    protected void onDestroyViewLazy() {

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
}
