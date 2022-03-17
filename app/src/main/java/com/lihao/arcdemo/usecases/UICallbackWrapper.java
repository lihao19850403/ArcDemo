package com.lihao.arcdemo.usecases;

import com.lihao.arcdemo.utils.ThreadUtils;

public class UICallbackWrapper<P> implements UseCase.UseCaseCallback<P> {

    private final UseCase.UseCaseCallback<P> mCallback;

    UICallbackWrapper(UseCase.UseCaseCallback<P> callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(final P response) {
        ThreadUtils.runOnUI(() -> mCallback.onSuccess(response));
    }

    @Override
    public void onError() {
        ThreadUtils.runOnUI(() -> mCallback.onError());
    }
}
