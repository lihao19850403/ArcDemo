package com.lihao.arcdemo;

import android.os.Bundle;

import com.lihao.arcdemo.viewmodels.DiariesViewModel;
import com.lihao.arcdemo.utils.ActivityUtils;
import com.lihao.arcdemo.views.DiariesFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);
        initFragment();
    }

    private void initFragment() {
        DiariesFragment diariesFragment = getDiariesFragment();
        if (diariesFragment == null) {
            diariesFragment = new DiariesFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
        }
        DiariesViewModel diariesViewModel = ViewModelProviders.of(this).get(DiariesViewModel.class);
        diariesViewModel.setView(diariesFragment);
        diariesFragment.setViewModel(diariesViewModel);
    }

    private DiariesFragment getDiariesFragment() {
        return (DiariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }
}