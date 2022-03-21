package com.lihao.en_common.usecase;

import com.lihao.en_base.utils.ThreadUtils;

/**
 * 在UI线程执行响应的包装类。
 * @param <P> 响应参数类型。
 */
public class UICallbackWrapper<P> implements UseCase.UseCaseCallback<P> {

    /** 要执行的回调。 */
    private final UseCase.UseCaseCallback<P> mCallback;

    /**
     * 对给定的参数进行了封装，在UI线程执行任务。
     * @param callback 响应参数。
     */
    public UICallbackWrapper(UseCase.UseCaseCallback<P> callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(P response) {
        ThreadUtils.runOnUI(() -> mCallback.onSuccess(response));
    }

    @Override
    public void onError() {
        ThreadUtils.runOnUI(() -> mCallback.onError());
    }
}
