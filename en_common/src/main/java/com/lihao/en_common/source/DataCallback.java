package com.lihao.en_common.source;

public interface DataCallback<T> {

    public void onSuccess(T t);

    public void onError();
}
