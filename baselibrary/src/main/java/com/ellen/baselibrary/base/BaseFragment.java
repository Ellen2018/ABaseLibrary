package com.ellen.baselibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    //懒加载相关
    private boolean isPrepare = false;
    private boolean isLoazyLoad = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepare = true;
        if(this instanceof LazyLoadInterface){
            LazyLoadInterface lazyLoadInterface = (LazyLoadInterface) this;
            lazyLoadInterface.lazyLoad();
            isLoazyLoad = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), container, false);
        if(this instanceof ButterKnifeInterface){
            ButterKnifeInterface butterKnifeInterface = (ButterKnifeInterface) this;
            butterKnifeInterface.initButterKnife(view);
        }
        initView(view);
        initData();
        return view;
    }

    protected String getTAG(){
        return getClass().getSimpleName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(this instanceof ButterKnifeInterface){
            ButterKnifeInterface butterKnifeInterface = (ButterKnifeInterface) this;
            butterKnifeInterface.unBindButterKnife();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isPrepare && !isLoazyLoad){
            if(this instanceof LazyLoadInterface){
                LazyLoadInterface lazyLoadInterface = (LazyLoadInterface) this;
                lazyLoadInterface.lazyLoad();
                isLoazyLoad = true;
            }
        }
    }

    protected abstract void initData();
    protected abstract void initView(View view);
    protected abstract int setLayout();

    //支持ButterKnife的接口
    public interface ButterKnifeInterface {
        void initButterKnife(View view);
        void unBindButterKnife();
    }

    public interface LazyLoadInterface{
        void lazyLoad();
    }
}
