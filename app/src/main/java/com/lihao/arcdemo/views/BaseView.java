package com.lihao.arcdemo.views;

import com.lihao.arcdemo.presenter.BasePresenter;

/**
 * 基础视图标记。
 */
public interface BaseView<T extends BasePresenter> {

    /**
     * 为View设置一个Presenter。
     * @param presenter Presenter实例。
     */
    void setPresenter(T presenter);
}
