package com.lihao.arcdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.lihao.arcdemo.presenter.DiariesContract;
import com.lihao.arcdemo.presenter.DiariesPresenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DiariesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);
        initView();
    }

    private void initView() {
        DiariesContract.View mView = findViewById(R.id.content);
        mPresenter = new DiariesPresenter(mView);
        mView.setPresenter(mPresenter);
        ImageView addDiaries = findViewById(R.id.add_diaries);
        addDiaries.setOnClickListener(v -> mPresenter.addDiary());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onResult(requestCode, resultCode);
    }
}