package com.lihao.arcdemo.presenter;

import com.lihao.arcdemo.views.BaseView;

/**
 * 编辑日记通用接口。
 */
public interface DiaryEditContract {

    /**
     * Presenter通用接口。
     */
    interface Presenter extends BasePresenter {

        /**
         * 保存日记。
         * @param title 标题。
         * @param description 日记内容。
         */
        void saveDiary(String title, String description);

        /**
         * 获取日记信息。
         */
        void requestDiary();
    }

    /**
     * View通用接口。
     */
    interface View extends BaseView<Presenter> {

        /**
         * 判断Controller是否已加入Activity中。
         * @return 判断结果。
         */
        boolean isActive();

        /**
         * 设置标题。
         * @param title 标题。
         */
        void setTitle(String title);

        /**
         * 设置日记内容。
         * @param description 日记内容。
         */
        void setDescription(String description);

        /**
         * 弹出错误提示信息。
         */
        void showError();

        /**
         * 显示日记列表。
         */
        void showDiariesList();
    }
}
