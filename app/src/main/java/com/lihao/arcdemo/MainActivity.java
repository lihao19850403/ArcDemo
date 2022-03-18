package com.lihao.arcdemo;

import android.os.Bundle;

import com.lihao.arcdemo.interactors.DiariesInteractor;
import com.lihao.arcdemo.presenter.DiariesPresenter;
import com.lihao.arcdemo.routers.DiariesRouter;
import com.lihao.arcdemo.utils.ActivityUtils;
import com.lihao.arcdemo.views.DiariesFragment;

import androidx.appcompat.app.AppCompatActivity;

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
        diariesFragment.setPresenter(new DiariesPresenter(diariesFragment, new DiariesInteractor(), new DiariesRouter(this)));
    }

    private DiariesFragment getDiariesFragment() {
        return (DiariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }
}