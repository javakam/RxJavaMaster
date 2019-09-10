package com.rxjava.composite.kam.sqrxjava2.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;

import butterknife.OnClick;
import io.reactivex.Flowable;
import kam.sqrxjava2.R;


/**
 * @author javakam
 * @date 2018-5-27 Sun.
 */
public class Practice2Activity extends BasePracticeActivity {
    private String[] mSendSignals;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mSendSignals = getDefaultStringArray();

        mTvSend.setText(mTvSend.getText() + "\n" + Arrays.asList(mSendSignals));
    }

    /**
     * 练习二  Flowable-Subscriber.
     * <p>
     * Flowable是RxJava2.x中新增的类，专门用于应对背压（Backpressure）问题，但这并不是RxJava2.x中新引入的概念。
     * 所谓背压，即生产者的速度大于消费者的速度带来的问题，比如在Android中常见的点击事件，点击过快则会造成点击两次的效果。
     * 先看一个简单的例子
     *
     * @param v
     */
    @OnClick(R.id.btStart)
    void practice2(View v) {
       Flowable.fromArray(mSendSignals)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //这里相比于Rx1.x多出了一个onSubscribe方法.cancel直接可以取消本次订阅，request方法设置
                        //该流可以请求的次数。该流发送了1-10 9个数，如果设置request(2)那么onNext只能接受到两个数字
                        //当然如果你什么也不做，那么恭喜你，不会打印任何东西。
                        s.request(2);

                        //onError = §3.9 violated: positive request amount required but it was 0
//                        s.request(0);
                    }

                    @Override
                    public void onNext(String s) {
                        mTvReceiver.setText(mTvReceiver.getText() + "\n" + "onNext; " + "value = " + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError = " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mTvReceiver.setText(mTvReceiver.getText() + "\n" + "onComplete");
                    }
                });
    }
}
