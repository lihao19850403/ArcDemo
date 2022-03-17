package com.lihao.en_common.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */
public class RouterPath {

    /**
     * 日记列表页面。
     */
    public static class List {

        private static final String LIST = "/List";

        /* 日记列表。 */
        public static final String PAGER_LIST_DIARY = LIST + "/listDiary";
    }

    /**
     * 日记编辑页面。
     */
    public static class Edit {

        private static final String EDIT = "/Edit";

        /* 编辑日记。 */
        public static final String PAGER_EDIT_DIARY = EDIT + "/editDiary";
    }
}
