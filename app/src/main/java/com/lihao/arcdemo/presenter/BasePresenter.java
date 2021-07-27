package com.lihao.arcdemo.presenter;

/**
 * 基本的Presenter。
 */
public interface BasePresenter {

    /**
     * 生命周期开始。
     */
    void subscribe();

    /**
     * 生命周期结束。
     */
    void unsubscribe();
}