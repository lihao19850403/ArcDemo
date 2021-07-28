package com.lihao.arcdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.lihao.arcdemo.viewmodels.DiaryViewModel;
import com.lihao.arcdemo.utils.ActivityUtils;
import com.lihao.arcdemo.views.DiaryEditFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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
        DiaryViewModel diaryViewModel = ViewModelProviders.of(this).get(DiaryViewModel.class);
        diaryViewModel.setView(addEditDiaryFragment);
        diaryViewModel.setDiaryId(diaryId);
        addEditDiaryFragment.setViewModel(diaryViewModel);
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