package com.lihao.arcdemo.presenter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    private T mData;

    public RecyclerViewHolder(ViewGroup parent, int resource) {
        super(LayoutInflater.from(parent.getContext()).inflate(resource, parent, false));
    }

    @CallSuper
    public void onBindView(T data) {
        mData = data;
    }

    public T getData() {
        return mData;
    }
}
