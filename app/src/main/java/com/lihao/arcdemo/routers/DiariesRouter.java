package com.lihao.arcdemo.routers;

import android.app.Activity;
import android.content.Intent;

import com.lihao.arcdemo.DiaryEditActivity;
import com.lihao.arcdemo.presenter.DiariesContract;
import com.lihao.arcdemo.views.DiaryEditFragment;

public class DiariesRouter implements DiariesContract.Router {

    private Activity mActivity;

    public DiariesRouter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void gotoWriteDiary() {
        Intent intent = new Intent(mActivity, DiaryEditActivity.class);
        mActivity.startActivity(intent);
    }

    @Override
    public void gotoUpdateDiary(String diaryId) {
        Intent intent = new Intent(mActivity, DiaryEditActivity.class);
        intent.putExtra(DiaryEditFragment.DIARY_ID, diaryId);
        mActivity.startActivity(intent);
    }
}
