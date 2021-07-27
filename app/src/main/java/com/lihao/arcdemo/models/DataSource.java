package com.lihao.arcdemo.models;

import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public interface DataSource<T> {

    Flowable<List<Diary>> getAll();

    Flowable<Diary> get(@NonNull String id);

    public void update(@NonNull  T diary);

    public void clear();

    public void delete(@NonNull String id);
}
