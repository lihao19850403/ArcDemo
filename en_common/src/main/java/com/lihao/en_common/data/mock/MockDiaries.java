package com.lihao.en_common.data.mock;

import com.lihao.en_common.model.Diary;

import java.util.LinkedHashMap;
import java.util.Map;

public class MockDiaries {

    private static final String DESCRIPTION = "今天，天气晴朗，在第九巷大道上，我遇到一群年轻人，他们优雅地弹奏着手风琴，围观的人大多是少男少女，他们目不转睛。";

    public static Map<String, Diary> mock() {
        return mock(new LinkedHashMap<String, Diary>());
    }

    public static Map<String, Diary> mock(Map<String, Diary> data) {
        Diary test1 = getDiary("2018-11-02 艺术节");
        data.put(test1.getId(), test1);
        Diary test2 = getDiary("2018-11-04 参加会展");
        data.put(test2.getId(), test2);
        Diary test3 = getDiary("2018-11-05 今天的心情很糟糕");
        data.put(test3.getId(), test3);
        Diary test4 = getDiary("2018-11-07 学习了新的架构");
        data.put(test4.getId(), test4);
        Diary test5 = getDiary("2018-11-09 持续进步");
        data.put(test5.getId(), test5);
        Diary test6 = getDiary("2018-11-10 我还在成长");
        data.put(test6.getId(), test6);
        Diary test7 = getDiary("2018-11-11 该怎样合作");
        data.put(test7.getId(), test7);
        Diary test8 = getDiary("2018-11-12 进步");
        data.put(test8.getId(), test8);
        return data;
    }

    private static Diary getDiary(String title) {
        return new Diary(title, DESCRIPTION);
    }
}
