package com.lihao.arcdemo.usecases;

import androidx.annotation.CallSuper;

public abstract class UseCase<Q, P> {

    /** 请求参数。 */
    public interface RequestValues {
    }

    /** 响应参数。 */
    public interface ResponseValues {
    }

    /**
     * 针对请求的响应。
     */
    public interface UseCaseCallback<P> {

        /**
         * 若包含多个返回数据，则需要封装传入。
         * {@link ResponseValues}
         * @param response 返回数据。
         */
        void onSuccess(P response);

        /**
         * 出错。
         */
        void onError();
    }

    private Q mRequestValues;

    private UseCaseCallback<P> mUseCaseCallback;

    /**
     * 若包含多个请求数据，则需要封装传入。
     * @param requestValues 请求数据参数。{@link RequestValues}
     * @return {@link UseCase}本身，进行链式调用。
     */
    @CallSuper
    public UseCase<Q, P> setRequestValues(Q requestValues) {
        mRequestValues = requestValues;
        return this;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    /**
     * 设置数据处理后的回调。
     * @param useCaseCallback 数据处理后的回调。{@link ResponseValues}
     * @return {@link UseCase}本身，进行链式调用。
     */
    public UseCase<Q, P> setUseCaseCallback(UseCaseCallback<P> useCaseCallback) {
        mUseCaseCallback = new UICallbackWrapper<>(useCaseCallback);
        return this;
    }

    protected UseCaseCallback<P> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    /**
     * 可在任意线程执行，回调时返回主线程。
     */
    public void run() {
        executeUseCase(mRequestValues);
    }

    /**
     * 执行数据处理流程。
     * @param requestValues 请求数据。
     */
    protected abstract void executeUseCase(Q requestValues);
}
