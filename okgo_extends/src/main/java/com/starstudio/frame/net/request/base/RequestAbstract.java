/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.starstudio.frame.net.request.base;

import android.text.TextUtils;

import com.starstudio.frame.net.OkGo;
import com.starstudio.frame.net.adapter.AdapterParam;
import com.starstudio.frame.net.adapter.CacheCall;
import com.starstudio.frame.net.adapter.OnCallAdapter;
import com.starstudio.frame.net.cache.CacheEntity;
import com.starstudio.frame.net.cache.CacheMode;
import com.starstudio.frame.net.cache.policy.OnCachePolicy;
import com.starstudio.frame.net.model.HttpHeaders;
import com.starstudio.frame.net.model.HttpMethod;
import com.starstudio.frame.net.model.HttpParams;
import com.starstudio.frame.net.utils.HttpUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/1/12
 * 描    述：所有请求的基类，其中泛型 R 主要用于属性设置方法后，返回对应的子类型，以便于实现链式调用
 * 修订历史：
 * ================================================
 */
public abstract class RequestAbstract<T, R extends RequestAbstract> implements Serializable {
    private static final long serialVersionUID = -7174118653689916252L;

    protected String url;
    protected String baseUrl;
    protected transient OkHttpClient client;
    protected transient Object tag;
    protected int retryCount;
    protected CacheMode cacheMode;
    protected String cacheKey;
    protected long cacheTime;                           //默认缓存的超时时间
    protected HttpParams params = new HttpParams();     //添加的param
    protected HttpHeaders headers = new HttpHeaders();  //添加的header

    protected transient okhttp3.Request mRequest;
    protected transient com.starstudio.frame.net.adapter.OnCall<T> OnCall;
    protected transient com.starstudio.frame.net.callback.OnCallback<T> OnCallback;
    protected transient com.starstudio.frame.net.convert.OnConverter<T> OnConverter;
    protected transient OnCachePolicy<T> onCachePolicy;
    protected transient ProgressRequestBody.UploadInterceptor uploadInterceptor;

    public RequestAbstract(String url) {
        this.url = url;
        baseUrl = url;
        OkGo go = OkGo.getInstance();
        //默认添加 Accept-Language
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) headers(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        //默认添加 User-Agent
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) headers(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
        //添加公共请求参数
        if (go.getCommonParams() != null) params(go.getCommonParams());
        if (go.getCommonHeaders() != null) headers(go.getCommonHeaders());
        //添加缓存模式
        retryCount = go.getRetryCount();
        cacheMode = go.getCacheMode();
        cacheTime = go.getCacheTime();
    }

    @SuppressWarnings("unchecked")
    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R retryCount(int retryCount) {
        if (retryCount < 0) {
            throw new IllegalArgumentException("retryCount must > 0");
        }
        this.retryCount = retryCount;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R client(OkHttpClient client) {
        HttpUtils.checkNotNull(client, "OkHttpClient == null");

        this.client = client;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R call(com.starstudio.frame.net.adapter.OnCall<T> OnCall) {
        HttpUtils.checkNotNull(OnCall, "call == null");

        this.OnCall = OnCall;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R converter(com.starstudio.frame.net.convert.OnConverter<T> OnConverter) {
        HttpUtils.checkNotNull(OnConverter, "converter == null");

        this.OnConverter = OnConverter;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cachePolicy(OnCachePolicy<T> onCachePolicy) {
        HttpUtils.checkNotNull(onCachePolicy, "cachePolicy == null");

        this.onCachePolicy = onCachePolicy;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R cacheKey(String cacheKey) {
        HttpUtils.checkNotNull(cacheKey, "cacheKey == null");

        this.cacheKey = cacheKey;
        return (R) this;
    }

    /** 传入 -1 表示永久有效,默认值即为 -1 */
    @SuppressWarnings("unchecked")
    public R cacheTime(long cacheTime) {
        if (cacheTime <= -1) cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
        this.cacheTime = cacheTime;
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R headers(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeHeader(String key) {
        headers.remove(key);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeAllHeaders() {
        headers.clear();
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(Map<String, String> params, boolean... isReplace) {
        this.params.put(params, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, String value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, int value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, float value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, double value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, long value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, char value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R params(String key, boolean value, boolean... isReplace) {
        params.put(key, value, isReplace);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R addUrlParams(String key, List<String> values) {
        params.putUrlParams(key, values);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeParam(String key) {
        params.remove(key);
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    @SuppressWarnings("unchecked")
    public R uploadInterceptor(ProgressRequestBody.UploadInterceptor uploadInterceptor) {
        this.uploadInterceptor = uploadInterceptor;
        return (R) this;
    }

    /** 默认返回第一个参数 */
    public String getUrlParam(String key) {
        List<String> values = params.urlParamsMap.get(key);
        if (values != null && values.size() > 0) return values.get(0);
        return null;
    }

    /** 默认返回第一个参数 */
    public HttpParams.FileWrapper getFileParam(String key) {
        List<HttpParams.FileWrapper> values = params.fileParamsMap.get(key);
        if (values != null && values.size() > 0) return values.get(0);
        return null;
    }

    public HttpParams getParams() {
        return params;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Object getTag() {
        return tag;
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public OnCachePolicy<T> getOnCachePolicy() {
        return onCachePolicy;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public okhttp3.Request getRequest() {
        return mRequest;
    }

    public void setOnCallback(com.starstudio.frame.net.callback.OnCallback<T> onCallback) {
        this.OnCallback = onCallback;
    }

    public com.starstudio.frame.net.convert.OnConverter<T> getOnConverter() {
        // converter 优先级高于 callback
        if (OnConverter == null) OnConverter = OnCallback;
        HttpUtils.checkNotNull(OnConverter, "converter == null, do you forget call Request#converter(Converter<T>) ?");
        return OnConverter;
    }

    public abstract HttpMethod getMethod();

    /** 根据不同的请求方式和参数，生成不同的RequestBody */
    protected abstract RequestBody generateRequestBody();

    /** 根据不同的请求方式，将RequestBody转换成Request对象 */
    public abstract okhttp3.Request generateRequest(RequestBody requestBody);

    /** 获取okhttp的同步call对象 */
    public okhttp3.Call getRawCall() {
        //构建请求体，返回call对象
        RequestBody requestBody = generateRequestBody();
        if (requestBody != null) {
            ProgressRequestBody<T> progressRequestBody = new ProgressRequestBody<>(requestBody, OnCallback);
            progressRequestBody.setInterceptor(uploadInterceptor);
            mRequest = generateRequest(progressRequestBody);
        } else {
            mRequest = generateRequest(null);
        }
        if (client == null) client = OkGo.getInstance().getOkHttpClient();
        return client.newCall(mRequest);
    }

    /** Rx支持，获取同步call对象 */
    public com.starstudio.frame.net.adapter.OnCall<T> adapt() {
        if (OnCall == null) {
            return new CacheCall<>(this);
        } else {
            return OnCall;
        }
    }

    /** Rx支持,获取同步call对象 */
    public <E> E adapt(OnCallAdapter<T, E> adapter) {
        com.starstudio.frame.net.adapter.OnCall<T> innerOnCall = OnCall;
        if (innerOnCall == null) {
            innerOnCall = new CacheCall<>(this);
        }
        return adapter.adapt(innerOnCall, null);
    }

    /** Rx支持,获取同步call对象 */
    public <E> E adapt(AdapterParam param, OnCallAdapter<T, E> adapter) {
        com.starstudio.frame.net.adapter.OnCall<T> innerOnCall = OnCall;
        if (innerOnCall == null) {
            innerOnCall = new CacheCall<>(this);
        }
        return adapter.adapt(innerOnCall, param);
    }

    /** 普通调用，阻塞方法，同步请求执行 */
    public Response execute() throws IOException {
        return getRawCall().execute();
    }

    /** 非阻塞方法，异步请求，但是回调在子线程中执行 */
    public void execute(com.starstudio.frame.net.callback.OnCallback<T> OnCallback) {
        HttpUtils.checkNotNull(OnCallback, "callback == null");

        this.OnCallback = OnCallback;
        com.starstudio.frame.net.adapter.OnCall<T> OnCall = adapt();
        OnCall.execute(OnCallback);
    }
}
