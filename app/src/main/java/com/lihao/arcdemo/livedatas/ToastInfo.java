package com.lihao.arcdemo.livedatas;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

public class ToastInfo extends MutableLiveData<String> {

    public interface ToastObserver {

        public void onNewMessage(String toastInfo);
    }

    public void observe(LifecycleOwner owner, final ToastObserver observer) {
        super.observe(owner, s -> {
            if (s == null) {
                return;
            }
            observer.onNewMessage(s);
        });
    }
}
