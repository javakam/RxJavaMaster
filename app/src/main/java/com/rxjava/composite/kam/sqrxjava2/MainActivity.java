package com.rxjava.composite.kam.sqrxjava2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.rxjava.composite.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kam.sqrxjava2.practice.IndexPracticeActivity;
import zlc.season.rx2.SeasonActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.card1)
    CardView card1;
    @BindView(R.id.card2)
    CardView card2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.card1, R.id.card2})
    public void go(View view) {
        switch (view.getId()) {
            case R.id.card1:
                startActivity(new Intent(this, IndexPracticeActivity.class));
                break;
            case R.id.card2:
                startActivity(new Intent(this, SeasonActivity.class));
                break;
        }
    }

}
