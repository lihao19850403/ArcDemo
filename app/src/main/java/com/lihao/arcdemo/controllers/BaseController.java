package com.lihao.arcdemo.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

import androidx.annotation.NonNull;

public class BaseController extends Controller {

    private boolean mIsActive = false;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return null;
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        setActive(false);
    }

    protected void setActive(boolean isActive) {
        mIsActive = isActive;
    }

    public boolean isActive() {
        return mIsActive;
    }
}
