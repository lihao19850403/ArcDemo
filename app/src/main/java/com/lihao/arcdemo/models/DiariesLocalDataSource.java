package com.lihao.arcdemo.models;

import com.lihao.arcdemo.db.DBManager;
import com.lihao.arcdemo.db.daos.DiaryDao;
import com.lihao.arcdemo.utils.CollectionUtils;
import com.lihao.arcdemo.utils.ThreadUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class DiariesLocalDataSource implements DataSource<Diary> {

    private static volatile DiariesLocalDataSource mInstance;

    private DiaryDao mDao;

    private final Executor mIOThread;

    private DiariesLocalDataSource() {
        mDao = DBManager.getInstance().diaryDao();
        mIOThread = Executors.newSingleThreadExecutor();
        mockData(); // 创建虚拟数据。
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
    public void getAll(DataCallback<List<Diary>> callback) {
        mIOThread.execute(() -> notifyUIAfterGetAll(mDao.getAll(), callback));
    }

    @Override
    public void get(String id, DataCallback<Diary> callback) {
        mIOThread.execute(() -> notifyUIAfterGet(mDao.get(id), callback));
    }

    @Override
    public void update(@NonNull final Diary diary) {
        mIOThread.execute(() -> {
            int result = mDao.update(diary);
            if (result == 0) {
                mDao.add(diary);
            }
        });
    }

    @Override
    public void clear() {
        mIOThread.execute(() -> mDao.deleteAll());
    }

    @Override
    public void delete(@NonNull final String id) {
        mIOThread.execute(() -> mDao.delete(id));
    }

    private void mockData() {
        mIOThread.execute(() -> {
            List<Diary> data = mDao.getAll();
            if (CollectionUtils.isEmpty(data)) {
                Map<String, Diary> mockData = MockDiaries.mock();
                Iterator<Map.Entry<String, Diary>> iterator = mockData.entrySet().iterator();
                while (iterator.hasNext()) {
                    mDao.add(iterator.next().getValue());
                }
            }
        });
    }

    private void notifyUIAfterGetAll(final List<Diary> diaries, @NonNull final DataCallback<List<Diary>> callback) {
        ThreadUtils.runOnUI(() -> {
            if (diaries.isEmpty()) {
                callback.onError();
            } else {
                callback.onSuccess(diaries);
            }
        });
    }

    private void notifyUIAfterGet(final Diary diary, @NonNull final DataCallback<Diary> callback) {
        ThreadUtils.runOnUI(() -> {
            if (diary != null) {
                callback.onSuccess(diary);
            } else {
                callback.onError();
            }
        });
    }
}
