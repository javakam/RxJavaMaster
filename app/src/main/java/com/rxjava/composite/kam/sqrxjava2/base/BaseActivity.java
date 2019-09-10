package com.rxjava.composite.kam.sqrxjava2.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


/**
 * @author javakma
 * @date 2018-5-27 Sun.
 */
public abstract class BaseActivity extends AppCompatActivity {
    public static final String TAG = "123";
    protected Unbinder mUnBinder;

    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            setContentView(getLayoutId());
        }
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mUnBinder = ButterKnife.bind(this);
    }

    public void setToolBar(Toolbar toolBar) {
        if (toolBar != null) {
            setSupportActionBar(toolBar);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        dispose();
    }

    protected void dispose() {
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    @LayoutRes
    protected abstract int getLayoutId();

}
