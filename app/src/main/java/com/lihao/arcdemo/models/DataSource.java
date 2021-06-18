package com.lihao.arcdemo.models;

import java.util.List;

public interface DataSource<T> {

    public void getAll(DataCallback<List<T>> callback);

    public void get(String id, DataCallback<T> callback);

    public void update(T diary);

    public void clear();

    public void delete(String id);
}
