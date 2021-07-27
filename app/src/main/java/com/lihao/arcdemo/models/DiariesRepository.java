package com.lihao.arcdemo.models;

import com.lihao.arcdemo.utils.CollectionUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

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
    public Flowable<List<Diary>> getAll() {
        // 获取内存数据。
        if (!CollectionUtils.isEmpty(mMemoryCache)) {
            return Flowable.fromIterable(mMemoryCache.values())
                    .toList()
                    .toFlowable();
        }
        // 获取本地数据。
        return mLocalDataSource.getAll()
                .flatMap(diaries -> Flowable.fromIterable(diaries)
                        .doOnNext(diary -> mMemoryCache.put(diary.getId(), diary))
                        .toList()
                        .toFlowable());
    }

    @Override
    public Flowable<Diary> get(@NonNull final String id) {
        // 获取内存数据。
        Diary cachedDiary = getDiaryByIdFromMemory(id);
        if (cachedDiary != null) {
            return Flowable.just(cachedDiary);
        }
        // 获取本地数据。
        return mLocalDataSource.get(id)
                .doOnNext(diary -> {
                    if (diary != null) {
                        mMemoryCache.put(diary.getId(), diary);
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

    @Nullable
    private Diary getDiaryByIdFromMemory(@NonNull String id) {
        if (CollectionUtils.isEmpty(mMemoryCache)) {
            return null;
        } else {
            return mMemoryCache.get(id);
        }
    }
}
