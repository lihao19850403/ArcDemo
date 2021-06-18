package com.lihao.arcdemo.presenter;

import android.text.TextUtils;

import com.lihao.arcdemo.models.DataCallback;
import com.lihao.arcdemo.models.DataSource;
import com.lihao.arcdemo.models.DiariesRepository;
import com.lihao.arcdemo.models.Diary;

import androidx.annotation.NonNull;

public class DiaryEditPresenter implements DiaryEditContract.Presenter {

    /** 数据源。 */
    private final DataSource<Diary> mDiariesRepository;

    /** 视图。 */
    private final DiaryEditContract.View mView;

    /** 日记ID。 */
    private String mDiaryId;

    public DiaryEditPresenter(@NonNull String diaryId, @NonNull DiaryEditContract.View view) {
        mDiariesRepository = DiariesRepository.getInstance();
        mView = view;
        mDiaryId = diaryId;
    }

    @Override
    public void start() {
        requestDiary();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void saveDiary(String title, String description) {
        if (isAddDiary()) {
            createDiary(title, description);
        } else {
            updateDiary(title, description);
        }
    }

    @Override
    public void requestDiary() {
        if (isAddDiary()) {
            return;
        }
        mDiariesRepository.get(mDiaryId, new DataCallback<Diary>() {
            @Override
            public void onSuccess(Diary diary) {
                if (!mView.isActive()) {
                    return;
                }
                mView.setTitle(diary.getTitle());
                mView.setDescription(diary.getDescription());
            }

            @Override
            public void onError() {
                if (!mView.isActive()) {
                    return;
                }
                mView.showError();
            }
        });
    }

    private boolean isAddDiary() {
        return TextUtils.isEmpty(mDiaryId);
    }

    private void createDiary(String title, String description) {
        Diary newDiary = new Diary(title, description);
        mDiariesRepository.update(newDiary);
        mView.showDiariesList();
    }

    private void updateDiary(String title, String description) {
        Diary diary = new Diary(mDiaryId, title, description);
        mDiariesRepository.update(diary);
        mView.showDiariesList();
    }
}
