package com.lihao.arcdemo.models;

public interface DataCallback<T> {

    public void onSuccess(T t);

    public void onError();
}
