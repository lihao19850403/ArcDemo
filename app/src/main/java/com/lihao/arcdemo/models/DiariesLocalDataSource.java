package com.lihao.arcdemo.models;

import com.lihao.arcdemo.utils.CollectionUtils;
import com.lihao.arcdemo.utils.GsonUtils;
import com.lihao.arcdemo.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import io.reactivex.Flowable;

public class DiariesLocalDataSource implements DataSource<Diary> {

    private static final String DIARY_DATA = "diary_data";

    private static final String ALL_DIARY = "all_diary";

    private static Map<String, Diary> LOCAL_DATA = new LinkedHashMap<>();

    private static volatile DiariesLocalDataSource mInstance;

    private SharedPreferencesUtils mSpUtils;

    private DiariesLocalDataSource() {
        mSpUtils = SharedPreferencesUtils.getInstance(DIARY_DATA);
        String diaryStr = mSpUtils.get(ALL_DIARY); // 获取本地日记信息。
        LinkedHashMap<String, Diary> diariesObj = json2Obj(diaryStr);
        if (CollectionUtils.isEmpty(diariesObj)) {
            LOCAL_DATA = MockDiaries.mock();
        } else {
            LOCAL_DATA = diariesObj;
        }
    }

    public static DiariesLocalDataSource get() {
        if (mInstance == null) {
            synchronized (DiariesLocalDataSource.class) {
                if (mInstance == null) {
                    mInstance = new DiariesLocalDataSource();
                }
            }
        }
        return mInstance;
    }

    @Override
    public Flowable<List<Diary>> getAll() {
        return Flowable.fromIterable(LOCAL_DATA.values())
                .toList()
                .toFlowable();
    }

    @Override
    public Flowable<Diary> get(@NonNull String id) {
        Diary diary = LOCAL_DATA.get(id);
        if (diary != null) {
            return Flowable.just(diary);
        } else {
            return Flowable.empty();
        }
    }

    @Override
    public void update(Diary diary) {
        LOCAL_DATA.put(diary.getId(), diary);
        mSpUtils.put(ALL_DIARY, obj2Json());
    }

    @Override
    public void clear() {
        LOCAL_DATA.clear();
        mSpUtils.remove(ALL_DIARY);
    }

    @Override
    public void delete(String id) {
        LOCAL_DATA.remove(id);
        mSpUtils.put(ALL_DIARY, obj2Json());
    }

    private String obj2Json() {
        return GsonUtils.toJson(LOCAL_DATA);
    }

    private LinkedHashMap<String, Diary> json2Obj(String diaryStr) {
        LinkedHashMap<String, Diary> result = new LinkedHashMap<>();
        LinkedHashMap diaryMap = GsonUtils.fromJson(diaryStr, LinkedHashMap.class);
        Set<String> ids = diaryMap == null ? new HashSet<>() : diaryMap.keySet();
        for (String id : ids) {
            Map diaryData = (Map) diaryMap.get(id);
            String diaryId = (String) diaryData.get("mId");
            String diaryTitle = (String) diaryData.get("mTitle");
            String diaryDesc = (String) diaryData.get("mDescription");
            Diary diary = new Diary(diaryId, diaryTitle, diaryDesc);
            List diaryObservers = (ArrayList) diaryData.get("mObservers");
            if (!CollectionUtils.isEmpty(diaryObservers)) {
                for (Object observer : diaryObservers) {
                    if (observer instanceof Observer) {
                        diary.registerObserver((Observer<Diary>) observer);
                    }
                }
            }
            result.put(id, diary);
        }
        return result;
    }
}
