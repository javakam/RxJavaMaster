package com.rxjava.composite.kam.sqrxjava2.net.http;

import kam.sqrxjava2.Constant;
import kam.sqrxjava2.net.api.IRequestApi;
import kam.sqrxjava2.net.api.IRetrofitDownloadApi;
import kam.sqrxjava2.net.api.IRetrofitUploadApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author javakam
 * @date 2018/5/24
 */
public class RetrofitModule {
    private static IRequestApi request;

    /**
     * @return 一般网络请求 get/post/...
     */
    public static IRequestApi getRequestApi() {
        if (request == null) {
            //拦截器
            Interceptor interceptor = chain -> {
                Request request = chain.request();
                Request authorised = request
                        .newBuilder()
                        .header("registeredChannels", "2")//来自1:iOS; 2:Android ;3:web
                        .build();
                return chain.proceed(authorised);
            };
            //打印拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)//添加拦截器
                    .addInterceptor(logging)//添加打印拦截器
                    .connectTimeout(15, TimeUnit.SECONDS)//设置请求超时时间
                    .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL_WANANDROID)//使用WanAndroid接口
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            request = retrofit.create(IRequestApi.class);
        }
        return request;
    }

    /**
     * @return 下载文件
     */
    public static IRetrofitDownloadApi getDownloadApi() {
        //打印拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body()))
                                .build();
                    }
                })
                .connectTimeout(15, TimeUnit.SECONDS)//设置请求超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(IRetrofitDownloadApi.class);
    }

    /**
     * @return
     */
    /**
     * @return 上传文件/图片
     */
    public static IRetrofitUploadApi getUploadApi() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)//设置请求超时时间
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build();
        return retrofit.create(IRetrofitUploadApi.class);
    }
}
