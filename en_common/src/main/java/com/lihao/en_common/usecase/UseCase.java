package com.lihao.en_common.usecase;

import androidx.annotation.CallSuper;

/**
 * 基础用例类。
 * @param <Q> 请求参数类型。
 * @param <P> 响应参数类型。
 */
public abstract class UseCase<Q, P> {

    /** 请求参数。 */
    public interface RequestValues {
    }

    /** 响应参数。 */
    public interface ResponseValues {
    }

    /** 针对请求的响应。 */
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

    /** 请求参数。 */
    private Q mRequestValues;

    /** 响应回调。 */
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

    /**
     * 获取请求参数。
     * @return 请求参数。
     */
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

    /**
     * 获取响应回调。
     * @return 响应回调。
     */
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
