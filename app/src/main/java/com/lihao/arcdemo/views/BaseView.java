package com.lihao.arcdemo.views;

import androidx.databinding.BaseObservable;

/**
 * 基础视图标记。
 */
public interface BaseView<T extends BaseObservable> {

    /**
     * 为View设置一个ViewModel。
     * @param viewModel ViewModel实例。
     */
    void setViewModel(T viewModel);
}
