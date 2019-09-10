package com.rxjava.composite.kam.sqrxjava2.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kam.sqrxjava2.R;


/**
 * @author javakam
 * @date 2018-5-27 Sun.
 */
public class Practice1Activity extends BasePracticeActivity {
    private String[] mSendSignals;

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        mSendSignals = getDefaultStringArray();

        mTvSend.setText(mTvSend.getText() + "\n" + Arrays.asList(mSendSignals));
    }

    /**
     * 练习一  Observer-Observable
     * <p>
     * 注意:
     * 这里如果getObservable()是采用Obsrvable.create方法创建的，
     * 那么onSubscribe方法中的Dispose对象是null。??? fromArray同样为空...
     *
     * @param v
     */
    @OnClick(R.id.btStart)
    void practice1(View v) {

        Observable<String> observable = getObservable();

          /*看到Observer接口多出了一个onSubscribe方法，注释说它可以用来取消
        * Observable和Observer之间的联系。也就是说Disposable相当于Rx1.x中Subscription的作用*/

        Observer<String> observer = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                // D/123: onSubscribe :CreateEmitter{null}
                Log.d(TAG, "onSubscribe :" + d);

                mTvReceiver.setText(mTvReceiver.getText() + "\n" + "onSubscribe方法执行 ");
            }

            @Override
            public void onNext(String value) {
                if ("four".equals(value)) {
                    if (disposable != null && !disposable.isDisposed()) {
                        disposable.dispose();
                    }
                }
                Log.d(TAG, "onNext = " + value);
                mTvReceiver.setText(mTvReceiver.getText() + "\n" + "onNext; " + "value = " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error = " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
                mTvReceiver.setText(mTvReceiver.getText() + "\n" + "onComplete");

            }
        };
        /*建立订阅关系，注意这里subscribe方法返回时void*/
        observable.subscribe(observer);
    }

    /**
     * 注意这里如果使用Observable.create方法传递的参数是ObservableOnSubscribe对象。
     * 该接口的subscribe方法的参数是ObservableEmitter。也就是说
     * 它是用来发送被观察者的事件
     */
    private Observable getObservable() {
        return Observable.fromArray(mSendSignals);

        //
//        return Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("two");
//                emitter.onNext("one");
//                emitter.onNext("four");
//                emitter.onNext("five");
//                emitter.onComplete();
//            }
//        });
    }
}
