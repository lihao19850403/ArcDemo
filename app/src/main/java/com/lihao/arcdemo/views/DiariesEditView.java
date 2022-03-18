package com.lihao.arcdemo.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lihao.arcdemo.R;
import com.lihao.arcdemo.presenter.DiaryEditContract;

import androidx.annotation.StringRes;

public class DiariesEditView extends ScrollView implements DiaryEditContract.View {

    private boolean mActive;

    public static final String DIARY_ID = "DIARY_ID";

    private DiaryEditContract.Presenter mPresenter;

    private TextView mTitle;
    private TextView mDescription;

    public DiariesEditView(Context context) {
        super(context);
        init();
    }

    public DiariesEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiariesEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_diary_edit, this);
        this.mTitle = findViewById(R.id.edit_title);
        this.mDescription = findViewById(R.id.edit_description);
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

    @Override
    public void setPresenter(DiaryEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return mActive;
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescription.setText(description);
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDiariesList() {
        Context context = getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        }
    }

    /**
     * 完成日记的修改。
     */
    public void done() {
        mPresenter.saveDiary(mTitle.getText().toString(), mDescription.getText().toString());
    }

    private String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }
}
