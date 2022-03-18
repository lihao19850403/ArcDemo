package com.lihao.arcdemo.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lihao.arcdemo.DiaryEditActivity;
import com.lihao.arcdemo.R;
import com.lihao.arcdemo.presenter.DiariesAdapter;
import com.lihao.arcdemo.presenter.DiariesContract;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiariesView extends LinearLayout implements DiariesContract.View {

    private boolean mActive;

    private DiariesContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;

    public DiariesView(Context context) {
        super(context);
        init();
    }

    public DiariesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiariesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_diaries, this);
        this.mRecyclerView = findViewById(R.id.diaries_list);
        initDiariesList();
        mActive = true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mActive = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mActive = false;
    }

    private void initDiariesList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setPresenter(DiariesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void gotoWriteDiary() {
        Context context = getContext();
        if (context instanceof Activity) {
            Intent intent = new Intent(getContext(), DiaryEditActivity.class);
            ((Activity) context).startActivityForResult(intent, 100);
        }
    }

    @Override
    public void gotoUpdateDiary(final String diaryId) {
        Context context = getContext();
        if (context instanceof Activity) {
            Intent intent = new Intent(getContext(), DiaryEditActivity.class);
            intent.putExtra(DiariesEditView.DIARY_ID, diaryId);
            ((Activity) context).startActivityForResult(intent, 100);
        }
    }

    @Override
    public void showSuccess() {
        showMessage(getString(R.string.success));
    }

    @Override
    public void showError() {
        showMessage(getString(R.string.error));
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void setListAdapter(DiariesAdapter diariesAdapter) {
        mRecyclerView.setAdapter(diariesAdapter);
    }

    private String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }
}
