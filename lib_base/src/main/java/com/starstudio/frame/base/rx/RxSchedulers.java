package com.starstudio.frame.base.rx;

import rx.Scheduler;

/**
 * Created by hongsec on 17. 11. 13.
 */

public interface RxSchedulers {
    Scheduler io();
    Scheduler compute();
    Scheduler androidThread();
    Scheduler internet();
    Scheduler runOnBackground();
}
