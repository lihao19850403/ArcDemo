package com.lihao.diary_list;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lihao.en_base.utils.ActivityUtils;
import com.lihao.en_common.router.RouterPath;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = RouterPath.List.PAGER_LIST_DIARY)
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
        diariesFragment.setPresenter(new DiariesPresenter(diariesFragment));
    }

    private DiariesFragment getDiariesFragment() {
        return (DiariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }
}