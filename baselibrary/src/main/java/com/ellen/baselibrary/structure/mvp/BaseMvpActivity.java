package com.ellen.baselibrary.structure.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ellen.baselibrary.base.BaseActivity;
import com.ellen.baselibrary.structure.mvp.basemvp.BasePresenter;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    protected  P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMVP();
    }

    protected abstract void initMVP();
}
