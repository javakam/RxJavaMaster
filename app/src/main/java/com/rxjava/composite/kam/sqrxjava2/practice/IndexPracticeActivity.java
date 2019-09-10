package com.rxjava.composite.kam.sqrxjava2.practice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import kam.sqrxjava2.R;
import kam.sqrxjava2.base.BaseActivity;
import kam.sqrxjava2.view.recycler.RvIndexPracticeAdapter;


/**
 * @author javakam
 * @date 2018-5-27 Sun.
 */
public class IndexPracticeActivity extends BaseActivity {
    @BindView(R.id.rv_index_practice)
    RecyclerView recyclerView;

    private RvIndexPracticeAdapter recycleAdapter;
    private List<String> dataList;

    private void initDate() {
        dataList = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            dataList.add("Advance Practice " + i);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_practice;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDate();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recycleAdapter = new RvIndexPracticeAdapter(this, dataList);
        recyclerView.setAdapter(recycleAdapter);
        //设置分隔线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
