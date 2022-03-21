package com.lihao.diary_list.routers;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lihao.diary_list.DiariesContract;
import com.lihao.en_common.router.RouterPath;

public class DiariesRouter implements DiariesContract.Router {

    private Activity mActivity;

    public DiariesRouter(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void gotoWriteDiary() {
        ARouter.getInstance()
                .build(RouterPath.Edit.PAGER_EDIT_DIARY)
                .navigation(mActivity, 100);
    }

    @Override
    public void gotoUpdateDiary(String diaryId) {
        ARouter.getInstance()
                .build(RouterPath.Edit.PAGER_EDIT_DIARY)
                .withString("DIARY_ID", diaryId)
                .navigation(mActivity, 100);
    }
}
