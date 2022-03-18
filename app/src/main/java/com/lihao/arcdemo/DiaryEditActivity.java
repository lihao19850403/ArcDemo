package com.lihao.arcdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lihao.arcdemo.interactors.DiariesEditInteractor;
import com.lihao.arcdemo.presenter.DiaryEditPresenter;
import com.lihao.arcdemo.utils.ActivityUtils;
import com.lihao.arcdemo.views.DiaryEditFragment;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        // 获得日记的ID。
        String diaryId = getIntent().getStringExtra(DiaryEditFragment.DIARY_ID);
        initToolbar(diaryId);
        initFragment(diaryId);
    }

    private void initToolbar(String diaryId) {
        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(TextUtils.isEmpty(diaryId) ? R.string.add_diary : R.string.edit_diary);
    }

    private void initFragment(String diaryId) {
        DiaryEditFragment addEditDiaryFragment = getDiaryEditFragment();
        if (addEditDiaryFragment == null) {
            addEditDiaryFragment = initEditDiaryFragment(diaryId);
        }
        DiaryEditPresenter diaryEditPresenter = new DiaryEditPresenter(diaryId, addEditDiaryFragment, new DiariesEditInteractor());
        addEditDiaryFragment.setPresenter(diaryEditPresenter);
    }

    private DiaryEditFragment getDiaryEditFragment() {
        return (DiaryEditFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private DiaryEditFragment initEditDiaryFragment(String diaryId) {
        DiaryEditFragment addEditDiaryFragment = new DiaryEditFragment();
        if (getIntent().hasExtra(DiaryEditFragment.DIARY_ID)) {
            Bundle bundle = new Bundle();
            bundle.putString(DiaryEditFragment.DIARY_ID, diaryId);
            addEditDiaryFragment.setArguments(bundle);
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditDiaryFragment, R.id.content);
        return addEditDiaryFragment;
    }
}