package com.lihao.arcdemo.models;

public interface Observer<T> {

    void update(T data);
}
