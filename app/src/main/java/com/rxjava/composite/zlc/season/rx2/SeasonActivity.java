package com.rxjava.composite.zlc.season.rx2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.InterruptedIOException;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import kam.sqrxjava2.R;
import zlc.season.rx2.demo.ChapterNine;

/**
 * RxJava2初学者教程
 * <p>
 * 作者：Season_zlc
 * 简书：https://www.jianshu.com/u/c50b715ccaeb
 * GitHub：https://github.com/ssseasonnn/RxJava2Demo
 */
public class SeasonActivity extends AppCompatActivity {
    public static final String TAG = "TAG";

    static {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (throwable instanceof InterruptedIOException) {
                    Log.d(TAG, "Io interrupted");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.season_main);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterNine.demo4();
//                ChapterEight.request(128);
            }
        });

        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChapterNine.request(96);
//                ChapterEight.demo4();
            }
        });
    }

}
