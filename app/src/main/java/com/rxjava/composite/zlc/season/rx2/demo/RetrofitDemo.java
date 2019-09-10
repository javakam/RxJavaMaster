package com.rxjava.composite.zlc.season.rx2.demo;

import android.content.Context;
import android.util.Log;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import zlc.season.rx2.Api;
import zlc.season.rx2.RetrofitProvider;

import static zlc.season.rx2.SeasonActivity.TAG;


/**
 * Author: Season(ssseasonnn@gmail.com)
 * Date: 2016/12/14
 * Time: 11:08
 * FIXME
 */
public class RetrofitDemo {
    public static void demo1(final Context context) {
        final Api api = RetrofitProvider.get().create(Api.class);
        api.getTop250(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(Response<ResponseBody> response) throws Exception {
                        Log.d(TAG, response.body().string());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.w("Error", throwable);
                    }
                });
    }
}
