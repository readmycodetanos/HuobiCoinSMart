package com.starstudio.frame.net.extend.imp;


/**
 * 成功回调处理
 * Created by WZG on 2016/10/20.
 */
public interface OnDownloadProgressListener {
    /**
     * 下载进度
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);
}
