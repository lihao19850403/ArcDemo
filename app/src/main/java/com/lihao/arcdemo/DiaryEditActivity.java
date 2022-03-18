package com.lihao.arcdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lihao.arcdemo.presenter.DiaryEditContract;
import com.lihao.arcdemo.presenter.DiaryEditPresenter;
import com.lihao.arcdemo.views.DiariesEditView;

import androidx.appcompat.app.AppCompatActivity;

public class DiaryEditActivity extends AppCompatActivity {

    private DiaryEditPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        // 获得日记的ID。
        String diaryId = getIntent().getStringExtra(DiariesEditView.DIARY_ID);
        initToolbar(diaryId);
        initView(diaryId);
    }

    private void initToolbar(String diaryId) {
        TextView titleView = findViewById(R.id.toolbar_title);
        titleView.setText(TextUtils.isEmpty(diaryId) ? R.string.add_diary : R.string.edit_diary);
    }

    private void initView(String diaryId) {
        DiaryEditContract.View mView = findViewById(R.id.content);
        mPresenter = new DiaryEditPresenter(diaryId, mView);
        mView.setPresenter(mPresenter);
        ImageView confirmBtn = findViewById(R.id.confirm_work);
        confirmBtn.setOnClickListener(v -> ((DiariesEditView) findViewById(R.id.content)).done());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }
}