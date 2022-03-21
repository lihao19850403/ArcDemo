package com.lihao.diary_list;


import com.lihao.diary_list.list.DiariesAdapter;
import com.lihao.diary_list.usecases.GetAllDiariesUseCase;
import com.lihao.en_base.base.BaseInteractor;
import com.lihao.en_base.base.BasePresenter;
import com.lihao.en_base.base.BaseRouter;
import com.lihao.en_base.base.BaseView;
import com.lihao.en_common.model.Diary;

import androidx.annotation.NonNull;

public interface DiariesContract {

    /**
     * Presenter公共接口。
     */
    interface Presenter extends BasePresenter {

        /**
         * 加载日记。
         */
        void loadDiaries();

        /**
         * 增加日记。
         */
        void addDiary();

        /**
         * 更新日记。
         * @param diary 带更新日记数据。
         */
        void updateDiary(@NonNull Diary diary);

        /**
         * 输入框单击事件。
         * @param desc 描述信息。
         */
        void onInputDialogClick(String desc);

        /**
         * 处理上一个页面的返回结果。
         * @param requestCode 请求码。
         * @param resultCode  结果码。
         */
        void onResult(int requestCode, int resultCode);
    }

    /**
     * View公共接口。
     */
    interface View extends BaseView<Presenter> {

        /**
         * 显示成功信息。
         */
        void showSuccess();

        /**
         * 显示失败信息。
         */
        void showError();

        /**
         * 判断Fragment是否已加入Activity。
         * @return 判断结果。
         */
        boolean isActive();

        /**
         * 设置适配器。
         * @param diariesAdapter 待设置的适配器。
         */
        void setListAdapter(DiariesAdapter diariesAdapter);
    }

    interface Interactor extends BaseInteractor {

        GetAllDiariesUseCase getAll();
    }

    interface Router extends BaseRouter {

        void gotoWriteDiary();

        void gotoUpdateDiary(String diaryId);
    }

}