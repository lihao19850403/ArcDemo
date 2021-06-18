package com.lihao.arcdemo.models;

import com.lihao.arcdemo.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DiariesRepository implements DataSource<Diary> {

    private static volatile DiariesRepository mInstance;

    private final DataSource<Diary> mLocalDataSource;

    private Map<String, Diary> mMemoryCache;

    private DiariesRepository() {
        mMemoryCache = new LinkedHashMap<>();
        mLocalDataSource = DiariesLocalDataSource.get();
    }

    public static DiariesRepository getInstance() {
        if (mInstance == null) {
            synchronized (DiariesRepository.class) {
                if (mInstance == null) {
                    mInstance = new DiariesRepository();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void getAll(final DataCallback<List<Diary>> callback) {
        // 获取内存数据。
        if (!CollectionUtils.isEmpty(mMemoryCache)) {
            callback.onSuccess(new ArrayList<>(mMemoryCache.values()));
            return;
        }
        // 获取本地数据。
        mLocalDataSource.getAll(new DataCallback<List<Diary>>() {
            @Override
            public void onSuccess(List<Diary> diaries) {
                updateMemoryCache(diaries); // 更新缓存。
                callback.onSuccess(new ArrayList<>(mMemoryCache.values()));
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void get(String id, final DataCallback<Diary> callback) {
        // 获取内存数据。
        Diary cachedDiary = getDiaryByIdFromMemory(id);
        if (cachedDiary != null) {
            callback.onSuccess(cachedDiary);
            return;
        }
        // 获取本地数据。
        mLocalDataSource.get(id, new DataCallback<Diary>() {
            @Override
            public void onSuccess(Diary diary) {
                mMemoryCache.put(diary.getId(), diary); // 更新缓存。
                callback.onSuccess(diary);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });
    }

    @Override
    public void update(Diary diary) {
        mLocalDataSource.update(diary);
        mMemoryCache.put(diary.getId(), diary);
    }

    @Override
    public void clear() {
        mLocalDataSource.clear();
        mMemoryCache.clear();
    }

    @Override
    public void delete(String id) {
        mLocalDataSource.delete(id);
        mMemoryCache.remove(id);
    }

    private void updateMemoryCache(List<Diary> diaryList) {
        mMemoryCache.clear();
        for (Diary diary : diaryList) {
            mMemoryCache.put(diary.getId(), diary);
        }
    }

    private Diary getDiaryByIdFromMemory(String id) {
        if (CollectionUtils.isEmpty(mMemoryCache)) {
            return null;
        } else {
            return mMemoryCache.get(id);
        }
    }
}
