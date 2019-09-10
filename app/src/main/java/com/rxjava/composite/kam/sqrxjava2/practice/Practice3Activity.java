package com.rxjava.composite.kam.sqrxjava2.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kam.sqrxjava2.R;
import kam.sqrxjava2.rxbus.TransformerHelper;


/**
 * @author javakam
 * @date 2018-5-27 Sun.
 */
public class Practice3Activity extends BasePracticeActivity {
    private List<Integer> mReceivedList;


    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mReceivedList = new ArrayList<>();

        mTvSend.setText(mTvSend.getText() + "\n" + "发送1-999 一共1000个信号"
                + "采用的被压策略是 BackpressureStrategy.LATEST");
    }

    /**
     * 测试Flowable的被压。注意ActionX，和FunctionX都支持抛出异常
     */
    @OnClick(R.id.btStart)
    void practice3(View v) {
        Flowable.create((FlowableOnSubscribe<Integer>) emitter -> {
            for (int i = 0; i < 1000; i++) {
                emitter.onNext(i);
            }
            emitter.onComplete();
          /*如果是LAST那么将999发出，然后不再发送。不同的被压策略最终在onNext的结果都不同，只可以自己测试
        * 这里采用BUFFER目的是想测试一下Dispose是否可以成功的取消*/
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
//                        s.request(128);
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, integer.toString());
                        mReceivedList.add(integer);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError: " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        Flowable.just(1)
                                .compose(TransformerHelper.io2main())
                                .subscribe(a -> mTvReceiver.setText(mTvReceiver.getText() + "\n" + mReceivedList
                                ));
                    }
                });
    }

    @OnClick(R.id.btStop)
    void practiceGetDisponsable(View v) {

        Disposable disposable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 1001; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                               @Override
                               public void accept(Integer integer) throws Exception {
                                   mReceivedList.add(integer);
                                   Thread.sleep(10);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable t) throws Exception {
                                   Log.d(TAG, "onError: " + t.getMessage());
                               }
                           }, new Action() {
                               @Override
                               public void run() throws Exception {
                                   Log.d(TAG, "onComplete");
                                   Flowable.just(1)
                                           .compose(TransformerHelper.io2main())
                                           .subscribe(a -> mTvReceiver.setText(
                                                   mTvReceiver.getText() + "\n" + mReceivedList
                                           ));
                               }
                           },
                        //无效的。。。
                        new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                subscription.request(300);
                            }
                        });

    }
}
