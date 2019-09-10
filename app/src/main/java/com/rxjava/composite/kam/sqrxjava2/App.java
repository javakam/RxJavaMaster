package com.rxjava.composite.kam.sqrxjava2;

import android.app.Application;
import android.content.Context;

import kam.sqrxjava2.net.api.IRequestApi;
import kam.sqrxjava2.net.http.RetrofitModule;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author javakam
 * @date 2018/5/24
 */
public class App extends Application {
    private static Context mContext;

    private static IRequestApi mRequestApi;

    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mRequestApi = RetrofitModule.getRequestApi();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);

    }

    public static Context getInstance() {
        return mContext;
    }

    public static IRequestApi getRetrofitApi() {
        return mRequestApi;
    }

    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

}
