package com.rxjava.composite.kam.sqrxjava2.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import kam.sqrxjava2.R;
import kam.sqrxjava2.base.BaseActivity;

/**
 * @author javakam
 * @date 2018-5-27
 */
public abstract class BasePracticeActivity extends BaseActivity {

    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btStart)
    Button btStart;

    @BindView(R.id.btStop)
    Button btStop;

    @BindView(R.id.tv_send_msg)
    TextView mTvSend;

    @BindView(R.id.tv_receiver_msg)
    TextView mTvReceiver;

    @BindView(R.id.progress_bar)
    ProgressBar mProgress;

    @BindView(R.id.tv_progress_text)
    TextView mTvProgress;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_practice;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolBar(toolbar);
        initViews(savedInstanceState);
    }

    protected abstract void initViews(@Nullable Bundle savedInstanceState);

    public String[] getDefaultStringArray() {
        String[] strs = new String[5];
        strs[0] = "one";
        strs[1] = "two";
        strs[2] = "three";
        strs[3] = "four";
        strs[4] = "five";
        return strs;
    }

    public int[] getDefaultIntegerArray() {
        int[] nums = new int[10];
        nums[0] = 1;
        nums[1] = 2;
        nums[2] = 3;
        nums[3] = 4;
        nums[4] = 5;
        nums[5] = 6;
        nums[6] = 7;
        nums[7] = 8;
        nums[8] = 9;
        nums[9] = 10;
        return nums;
    }
}
